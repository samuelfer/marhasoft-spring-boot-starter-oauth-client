package br.com.marhasoft.oauth.client.auth;

import br.com.marhasoft.oauth.client.internal.OAuthClientConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Constantes OAuth2")
class OAuthClientConstantsTest {

    @Test
    @DisplayName("Deve possuir os valores esperados")
    void shouldHaveExpectedValues() {

        assertThat(OAuthClientConstants.CLIENT_REGISTRATION_ID)
                .isEqualTo("oauth-client");

        assertThat(OAuthClientConstants.TOKEN_ENDPOINT)
                .isEqualTo("oauth/token");

    }

}