package br.com.marhasoft.oauth.client.exception;

public class OAuthClientException extends RuntimeException {

    public OAuthClientException(String message) {
        super(message);
    }

    public OAuthClientException(String message, Throwable cause) {
        super(message, cause);
    }

}