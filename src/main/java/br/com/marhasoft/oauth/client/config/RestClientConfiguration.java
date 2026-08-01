package br.com.marhasoft.oauth.client.config;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import br.com.marhasoft.oauth.client.internal.DefaultOAuthRestClientFactory;
import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configures the RestClient infrastructure used by the OAuth Client.
 */
@Configuration(proxyBeanMethods = false)
public class RestClientConfiguration {

    /**
     * Creates the base RestClient.Builder used by the library.
     */
    @Bean
    @ConditionalOnMissingBean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    /**
     * Creates the factory responsible for providing RestClient
     * instances configured with automatic OAuth 2.0 authentication.
     */
    @Bean
    @ConditionalOnMissingBean
    public OAuthRestClientFactory oauthRestClientFactory(
            RestClient.Builder builder,
            AccessTokenService accessTokenService,
            OAuthClientProperties properties) {

        return new DefaultOAuthRestClientFactory(
                builder,
                accessTokenService,
                properties);
    }

}