package br.com.wiser.business.app.servidor;

/**
 * Created by Jefferson on 31/07/2016.
 */
public class Response {
    private String messageResponse;
    private int codeResponse;

    public String getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(String messageResponse) {
        this.messageResponse = messageResponse;
    }

    public int getCodeResponse() {
        return codeResponse;
    }

    public void setCodeResponse(int codeResponse) {
        this.codeResponse = codeResponse;
    }
}
