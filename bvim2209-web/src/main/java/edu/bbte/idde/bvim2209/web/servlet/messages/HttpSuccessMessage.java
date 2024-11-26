package edu.bbte.idde.bvim2209.web.servlet.messages;

public class HttpSuccessMessage {
    private String message;

    public HttpSuccessMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
