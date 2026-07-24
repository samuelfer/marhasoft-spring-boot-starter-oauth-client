package io.github.samuelfernandes.cerberus.client.service;

import io.github.samuelfernandes.cerberus.client.oauth.CerberusAuthentication;
import io.github.samuelfernandes.cerberus.client.oauth.CerberusOAuth2Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@RequiredArgsConstructor
public class DefaultCerberusTokenService implements CerberusTokenService {

    private static final CerberusAuthentication AUTHENTICATION =
            new CerberusAuthentication();

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Override
    public String getAccessToken() {

        OAuth2AuthorizedClient authorizedClient =
                authorizedClientManager.authorize(
                        OAuth2AuthorizeRequest
                                .withClientRegistrationId(CerberusOAuth2Constants.REGISTRATION_ID)
                                .principal(AUTHENTICATION)
                                .build());

        if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
            throw new IllegalStateException("Não foi possível obter o Access Token.");
        }

        return authorizedClient.getAccessToken().getTokenValue();
    }
}