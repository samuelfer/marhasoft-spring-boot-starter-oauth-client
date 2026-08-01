package br.com.marhasoft.oauth.client.api;

import org.springframework.web.client.RestClient;

/**
 * Factory responsible for creating RestClient instances configured
 * with automatic OAuth 2.0 Bearer Token authentication.
 */
public interface OAuthRestClientFactory {

    /**
     * Creates a RestClient using the default configured OAuth client.
     *
     * @return a RestClient configured with OAuth authentication.
     */
    RestClient create();

    /**
     * Creates a RestClient using the specified OAuth client.
     *
     * @param client the configured OAuth client name.
     * @return a RestClient configured with OAuth authentication.
     */
    RestClient create(String client);

}