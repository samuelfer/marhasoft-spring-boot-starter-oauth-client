package br.com.marhasoft.oauth.client.internal;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import br.com.marhasoft.oauth.client.interceptor.BearerTokenInterceptor;
import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default implementation of {@link OAuthRestClientFactory}.
 *
 * <p>Creates and caches {@link RestClient} instances configured with
 * automatic OAuth 2.0 Bearer Token authentication.</p>
 *
 * <p>A single {@link RestClient} instance is created for each configured
 * OAuth client and reused for subsequent requests.</p>
 */
public class DefaultOAuthRestClientFactory
        implements OAuthRestClientFactory {

    private final RestClient.Builder builder;
    private final AccessTokenService accessTokenService;
    private final OAuthClientProperties properties;

    /**
     * Cache of RestClient instances indexed by OAuth client name.
     */
    private final Map<String, RestClient> clients =
            new ConcurrentHashMap<>();

    public DefaultOAuthRestClientFactory(
            RestClient.Builder builder,
            AccessTokenService accessTokenService,
            OAuthClientProperties properties) {

        this.builder = builder;
        this.accessTokenService = accessTokenService;
        this.properties = properties;
    }

    /**
     * Creates or returns a cached RestClient using the default
     * configured OAuth client.
     */
    @Override
    public RestClient create() {

        if (!properties.getClients().isEmpty()) {
            return create(properties.getDefaultClient());
        }

        // Backward compatibility with the legacy single-client configuration.
        return create(OAuthClientConstants.CLIENT_REGISTRATION_ID);
    }

    /**
     * Creates or returns a cached RestClient for the specified OAuth client.
     *
     * @param client the configured OAuth client name.
     * @return a RestClient configured with automatic Bearer Token authentication.
     */
    @Override
    public RestClient create(String client) {

        return clients.computeIfAbsent(
                client,
                this::buildRestClient);
    }

    /**
     * Builds a RestClient configured with a BearerTokenInterceptor
     * for the specified OAuth client.
     */
    private RestClient buildRestClient(String client) {

        return builder
                .requestInterceptor(
                        new BearerTokenInterceptor(
                                accessTokenService,
                                client))
                .build();
    }

}