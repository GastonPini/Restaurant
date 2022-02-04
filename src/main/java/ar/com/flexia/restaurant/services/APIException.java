package ar.com.flexia.restaurant.services;

public class APIException extends RuntimeException {

   private static final long serialVersionUID = 1L;
   
   private final String code;

    public APIException(String code) {
        this.code = code;
    }
    
    public APIException(String code, String message) {
        super(message);
        this.code = code;
    }

    public APIException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}