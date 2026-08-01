package br.com.marhasoft.oauth.client.internal;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import br.com.marhasoft.oauth.client.interceptor.BearerTokenInterceptor;
import org.springframework.web.client.RestClient;

public class DefaultOAuthRestClientFactory
        implements OAuthRestClientFactory {

    private final RestClient restClient;

    public DefaultOAuthRestClientFactory(
            RestClient.Builder builder,
            AccessTokenService accessTokenService) {

        this.restClient = builder
                .requestInterceptor(
                        new BearerTokenInterceptor(accessTokenService))
                .build();
    }

    @Override
    public RestClient create() {
        return restClient;
    }
}