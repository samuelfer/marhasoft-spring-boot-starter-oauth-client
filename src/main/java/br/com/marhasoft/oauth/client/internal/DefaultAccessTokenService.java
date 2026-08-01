package br.com.marhasoft.oauth.client.internal;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.auth.ClientAuthentication;
import br.com.marhasoft.oauth.client.config.OAuthClientConfiguration;
import br.com.marhasoft.oauth.client.exception.OAuthClientException;
import br.com.marhasoft.oauth.client.exception.OAuthClientExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class DefaultAccessTokenService implements AccessTokenService {

    private static final ClientAuthentication AUTHENTICATION =
            new ClientAuthentication();

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    private static final Logger log =
            LoggerFactory.getLogger(OAuthClientConfiguration.class);

    public DefaultAccessTokenService(
            OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public String getAccessToken() {

        try {
            OAuth2AuthorizedClient authorizedClient =
                    authorizedClientManager.authorize(
                            OAuth2AuthorizeRequest
                                    .withClientRegistrationId(OAuthClientConstants.CLIENT_REGISTRATION_ID)
                                    .principal(AUTHENTICATION)
                                    .build());

            if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
                throw new OAuthClientException(
                        "O Authorization Server não retornou um Access Token.");
            }

            return authorizedClient.getAccessToken().getTokenValue();
        } catch (Throwable ex) {
            throw OAuthClientExceptionHandler.translate(ex);

        }

    }
}