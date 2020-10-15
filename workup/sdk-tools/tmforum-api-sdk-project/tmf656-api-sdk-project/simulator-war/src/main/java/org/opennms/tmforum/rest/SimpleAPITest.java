package org.opennms.tmforum.rest;

import org.opennms.tmforum.rest.paramsfilter.old.Filter;
import org.opennms.tmforum.rest.paramsfilter.old.QueryParamsParser;
import org.opennms.tmforum.swagger.tmf656.swagger.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.opennms.tmforum.swagger.tmf656.swagger.api.NotFoundException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.print.attribute.standard.Media;

@Path("/simpleApiTestService")
@io.swagger.annotations.Api(description = "SimpleAPITest")
public class SimpleAPITest {
    private final static Logger LOG = LoggerFactory.getLogger(SimpleAPITest.class);

    
    /*
     * 
     * 
     * simple query
     * http://localhost:8080/tmf656-simulator-war/tmf-api/serviceProblemManagement/v3/simpleApiTestService/1/?fields=id&offset=5&limit=7
     * 
     * http://localhost:8080/tmf656-simulator-war/tmf-api/serviceProblemManagement/v3/simpleApiTestService/1/?fields=description,status&creationDate.gt=2013-04-20&status=acknowledged
     * status=acknowledged;status=rejected
     * status=acknowledged&status=rejected
     * status=acknowledged,rejected
     * 
     * http://localhost:8080/tmf656-simulator-war/tmf-api/serviceProblemManagement/v3/simpleApiTestService/1/?fields=description,status&creationDate.gt=2013-04-20&status=acknowledged,rejected
     * http://localhost:8080/tmf656-simulator-war/tmf-api/serviceProblemManagement/v3/simpleApiTestService/1/?fields=description,status&creationDate.gt=2013-04-20&status=acknowledged&status=rejected
     * http://localhost:8080/tmf656-simulator-war/tmf-api/serviceProblemManagement/v3/simpleApiTestService/1/?fields=description,status&creationDate.gt=2013-04-20&status=acknowledged;status=rejected
     * 
     * sort
     * sort=creationDate,statusChangeDate,note.date
     * sort=,+,creationDate,statusChangeDate,note.date
     * If sorting is used then following parameters MUST be supported:
Sort-Query-Parameters : “sort”, “=”, (Sort-Direction), Sort-Field
     * Sort-Direction : “-“ | “+”
Sort-Field: The field to sort on.
     * example
     * http://localhost:8080/tmf656-simulator-war/tmf-api/serviceProblemManagement/v3/simpleApiTestService/1/?fields=id&offset=5&limit=7&sort=,+,creationDate,statusChangeDate,note.date
     * Note basic query design issue + is reserved character in http url '+' is escaped to space (or use %2B URL encoded)
     * &sort=,-,creationDate  works
     * &sort=,+,creationDate becomes &sort=, ,creationDate
     * 
     * Test String
     * http://localhost:8080/tmf656-simulator-war/tmf-api/serviceProblemManagement/v3/simpleApiTestService/?fields=description,status&creationDate.gt=2013-04-20&status=acknowledged&status=rejected&name=hello&dateTime.gt=2013-04-20&dateTime.lte=2017-04-20
     * 
     * 
     */

    @GET
    @Path("/")
    @Produces({MediaType.TEXT_PLAIN})
    public Response retrieveTest( @QueryParam("fields") String fields,
           @Context SecurityContext securityContext, @Context UriInfo uriInfo) throws NotFoundException {

        try {
           String responseString="GET simpleApiTestService/ called fields=" + fields+"\n";
          
           String requestUri=uriInfo.getRequestUri().toString();
           LOG.debug("path=" + requestUri);
           
           responseString+="path=" + requestUri+"\n";
           responseString+="parameter multi value map:"+"\n";

           
           MultivaluedMap<String, String> mvMap = uriInfo.getQueryParameters();
           for(String param: mvMap.keySet()) {
               responseString+="   param: " + param+"\n";
               List<String> values = mvMap.get(param);
               for(String value: values) {
                   responseString+="      value: " + value+"\n";
               }
           }
           
           QueryParamsParser queryParser = new QueryParamsParser();
           
           
           Filter filter = queryParser.parse(mvMap);
           String queryString = filter.getQuery();
           responseString+="queryString="+queryString;

           LOG.debug(responseString);

            return Response.status(Response.Status.OK).entity(responseString).build();

        } catch (Exception ex) {
            LOG.error("GET simpleApiTestService/ ", ex);
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR,
                    "GET simpleApiTestService/ " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponseMessage).build();
        }

    }

}
