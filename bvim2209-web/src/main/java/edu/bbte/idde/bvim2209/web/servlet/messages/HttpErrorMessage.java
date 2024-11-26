package edu.bbte.idde.bvim2209.web.servlet.messages;

public class HttpErrorMessage {
    private String error;

    public HttpErrorMessage(String error) {
        this.error = error;
    }

    public String getMessage() {
        return error;
    }

    public void setMessage(String message) {
        this.error = message;
    }
}
