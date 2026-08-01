package br.com.marhasoft.oauth.client.config;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import br.com.marhasoft.oauth.client.internal.DefaultOAuthRestClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public OAuthRestClientFactory oauthRestClientFactory(
            RestClient.Builder builder,
            AccessTokenService accessTokenService) {

        return new DefaultOAuthRestClientFactory(
                builder,
                accessTokenService);
    }

}