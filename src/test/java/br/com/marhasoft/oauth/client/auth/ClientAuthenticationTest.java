package br.com.marhasoft.oauth.client.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Autenticação do cliente OAuth")
class ClientAuthenticationTest {

    @Test
    @DisplayName("Deve criar a autenticação corretamente")
    void shouldCreateAuthentication() {

        ClientAuthentication authentication =
                new ClientAuthentication();

        assertThat(authentication.getPrincipal())
                .isEqualTo("oauth-client");

        assertThat(authentication.getName())
                .isEqualTo("oauth-client");

        assertThat(authentication.isAuthenticated())
                .isTrue();

        assertThat(authentication.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_OAUTH_CLIENT");

    }

}