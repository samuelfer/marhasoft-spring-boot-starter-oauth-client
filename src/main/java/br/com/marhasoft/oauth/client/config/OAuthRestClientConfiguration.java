package br.com.marhasoft.oauth.client.config;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.interceptor.BearerTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OAuthRestClientConfiguration {

    @Bean("oauthRestClient")
    public RestClient oauthRestClient(
            RestClient.Builder builder,
            AccessTokenService accessTokenService) {

        return builder
                .requestInterceptor(
                        new BearerTokenInterceptor(accessTokenService))
                .build();
    }

}