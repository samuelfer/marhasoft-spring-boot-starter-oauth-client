package br.com.marhasoft.oauth.client.internal;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.auth.ClientAuthentication;
import br.com.marhasoft.oauth.client.exception.OAuthClientException;
import br.com.marhasoft.oauth.client.exception.OAuthClientExceptionHandler;
import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

/**
 * Default implementation responsible for obtaining OAuth 2.0
 * Access Tokens using the Client Credentials grant.
 */
public class DefaultAccessTokenService implements AccessTokenService {

    /**
     * Authentication representing the OAuth client.
     */
    private static final ClientAuthentication AUTHENTICATION =
            new ClientAuthentication();

    /**
     * OAuth client configuration properties.
     */
    private final OAuthClientProperties properties;

    /**
     * Spring Security component responsible for obtaining and
     * managing OAuth authorized clients.
     */
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public DefaultAccessTokenService(
            OAuth2AuthorizedClientManager authorizedClientManager,
            OAuthClientProperties properties) {

        this.authorizedClientManager = authorizedClientManager;
        this.properties = properties;
    }

    /**
     * Obtains an Access Token using the default configured client.
     *
     * <p>If the application is configured with multiple clients,
     * the client defined by {@code marhasoft.oauth.default-client}
     * will be used. Otherwise, the single-client configuration
     * is used.</p>
     */
    @Override
    public String getAccessToken() {

        if (!properties.getClients().isEmpty()) {

            String defaultClient = properties.getDefaultClient();

            if (defaultClient == null || defaultClient.isBlank()) {
                throw new OAuthClientException(
                        "Nenhum cliente OAuth padrão foi configurado.");
            }

            return getAccessToken(defaultClient);
        }

        // Backward compatibility with the legacy single-client configuration.
        return getAccessToken(OAuthClientConstants.CLIENT_REGISTRATION_ID);
    }

    /**
     * Obtains an Access Token for the specified OAuth client.
     *
     * @param client the configured client name.
     * @return the Access Token.
     */
    @Override
    public String getAccessToken(String client) {

        try {

            OAuth2AuthorizedClient authorizedClient =
                    authorizedClientManager.authorize(
                            OAuth2AuthorizeRequest
                                    .withClientRegistrationId(client)
                                    .principal(AUTHENTICATION)
                                    .build());

            if (authorizedClient == null
                    || authorizedClient.getAccessToken() == null) {

                throw new OAuthClientException(
                        "O Authorization Server não retornou um Access Token.");
            }

            return authorizedClient
                    .getAccessToken()
                    .getTokenValue();

        } catch (Throwable ex) {

            throw OAuthClientExceptionHandler.translate(ex);
        }
    }
}