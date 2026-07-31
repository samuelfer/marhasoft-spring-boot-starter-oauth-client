package br.com.marhasoft.oauth.client.internal;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.auth.ClientAuthentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class DefaultAccessTokenService implements AccessTokenService {

    private static final ClientAuthentication AUTHENTICATION =
            new ClientAuthentication();

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public DefaultAccessTokenService(
            OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    public String getAccessToken() {

        OAuth2AuthorizedClient authorizedClient =
                authorizedClientManager.authorize(
                        OAuth2AuthorizeRequest
                                .withClientRegistrationId(OAuthClientConstants.CLIENT_REGISTRATION_ID)
                                .principal(AUTHENTICATION)
                                .build());

        if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
            throw new IllegalStateException("Não foi possível obter o Access Token.");
        }

        return authorizedClient.getAccessToken().getTokenValue();
    }
}