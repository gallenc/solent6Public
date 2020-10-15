package org.opennms.tmforum.tmf656.web;


import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ServletContextListeneer executes code on web app startup and shutdown to shut down database correctly
 *
 * @author gallenc
 */
@WebListener
public class HsqlDatabaseInitialiser implements ServletContextListener {

    final static Logger LOG = LogManager.getLogger(HsqlDatabaseInitialiser.class);

    final static String TMP_DIR_KEY = "javax.servlet.context.tempdir";
    
    private String tempDirPath = null;
    
    private String jpaJdbcUrl=null;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        File tempDir = (File) sce.getServletContext().getAttribute(TMP_DIR_KEY);
        tempDirPath = tempDir.getAbsolutePath();
        LOG.info("servlet javax.servlet.context.tempdir=" + tempDirPath);
        init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("destroying servlet context");
        destroy();
    }

    public void init() {
        // this is needed to allow HSQLDB to work as in embedded server
        String dhsqlDbHome = tempDirPath + File.separator + "hsqldb";
        LOG.info("setting database temp directory=" + dhsqlDbHome);
        
        jpaJdbcUrl="jdbc:hsqldb:file:"+dhsqlDbHome + File.separator +"productDb";
        
        LOG.info("setting jpa.jdbc.url=" + jpaJdbcUrl);

        System.setProperty("jpa.jdbc.url", jpaJdbcUrl);
    }

    public void destroy() {
        shutdownHsql();
    }

    // code to shutdown hsql 
    private void shutdownHsql() {
        Connection cn = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            cn = DriverManager.getConnection(jpaJdbcUrl, "sa", "");
            Statement stmt = cn.createStatement();
            stmt.executeUpdate("SHUTDOWN;");
            LOG.info("HSQL shutdown succeeded");
            
            // unregister any jdbc drivers
            LOG.info("Unregistering any JDBC drivers ");
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                java.sql.Driver driver = drivers.nextElement();
                LOG.info("Unregistering JDBC driver " + driver);
                try {
                    java.sql.DriverManager.deregisterDriver(driver);
                } catch (Throwable t) {
                }
            }
            LOG.info("Unregistering JDBC drivers complete");

        } catch (Exception e) {
           LOG.warn("HSQL shutdown failed" , e.getMessage());
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    LOG.warn("Database closing error", e);
                }
            }
        }

    }

}
