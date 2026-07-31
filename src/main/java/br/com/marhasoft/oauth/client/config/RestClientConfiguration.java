package br.com.marhasoft.oauth.client.config;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.interceptor.BearerTokenInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RestClientConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "oauthRestClientCustomizer")
    RestClientCustomizer oauthRestClientCustomizer(
            AccessTokenService tokenService) {

        return builder ->
                builder.requestInterceptor(
                        new BearerTokenInterceptor(tokenService));
    }

}