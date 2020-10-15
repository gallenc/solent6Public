package org.opennms.tmforum.tmf650.api;

@javax.annotation.Generated(value = "org.opennms.tmforum.swagger.patch.JavaJerseyServerCodegen", date = "2020-08-08T06:53:02.962+01:00")
public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}