package utility;

public enum HttpStatusCode {
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204,  "No Content"),

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable")
    ;

    private int code;
    private String desc;
    private String text;

    HttpStatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
        this.text = Integer.toString(code);
    }

    /**
     * Gets the HTTP status code
     * @return the status code number
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the HTTP status code as a text string
     * @return the status code as a text string
     */
    public String asText() {
        return text;
    }

    /**
     * Get the description
     * @return the description of the status code
     */
    public String getDesc() {
        return desc;
    }

}
