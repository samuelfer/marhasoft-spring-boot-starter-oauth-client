package br.com.marhasoft.oauth.client.api;

/**
 * Service responsible for providing a valid OAuth 2.0 Access Token.
 */
public interface AccessTokenService {

    /**
     * Returns a valid OAuth 2.0 Access Token.
     *
     * @return a valid access token.
     */
    String getAccessToken();

    /**
     * Obtém o Access Token do cliente informado.
     */
    String getAccessToken(String clientRegistrationId);
}
