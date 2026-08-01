package br.com.marhasoft.oauth.client.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.ClientAuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tratamento de exceções do OAuth Client")
class OAuthClientExceptionHandlerTest {

    @Test
    @DisplayName("Deve retornar a mesma exceção quando já for OAuthClientException")
    void shouldReturnSameOAuthClientException() {

        OAuthClientException exception =
                new OAuthClientException("Erro");

        OAuthClientException translated =
                OAuthClientExceptionHandler.translate(exception);

        assertThat(translated)
                .isSameAs(exception);

    }

    @Test
    @DisplayName("Deve traduzir exceção de autenticação do cliente OAuth")
    void shouldTranslateClientAuthorizationException() {

        ClientAuthorizationException exception =
                new ClientAuthorizationException(
                        new OAuth2Error("invalid_client"),
                        "oauth-client");

        OAuthClientException translated =
                OAuthClientExceptionHandler.translate(exception);

        assertThat(translated.getMessage())
                .startsWith("Falha na autenticação do cliente OAuth");

        assertThat(translated.getMessage())
                .contains("Client ID");

        assertThat(translated.getMessage())
                .contains("Client Secret");

        assertThat(translated.getCause())
                .isSameAs(exception);

    }

    @Test
    @DisplayName("Deve traduzir resposta inesperada do Authorization Server")
    void shouldTranslateUnexpectedAuthorizationServerResponse() {

        NullPointerException exception =
                new NullPointerException(
                        "Cannot invoke \"org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse.getAccessToken()\" because \"tokenResponse\" is null");

        OAuthClientException translated =
                OAuthClientExceptionHandler.translate(exception);

        assertThat(translated.getMessage())
                .contains("Authorization Server");

        assertThat(translated.getMessage())
                .contains("resposta inesperada");

        assertThat(translated.getMessage())
                .contains("Access Token");

        assertThat(translated.getCause())
                .isSameAs(exception);

    }

    @Test
    @DisplayName("Deve traduzir exceções inesperadas")
    void shouldTranslateUnexpectedException() {

        RuntimeException exception =
                new RuntimeException("Erro qualquer");

        OAuthClientException translated =
                OAuthClientExceptionHandler.translate(exception);

        assertThat(translated.getMessage())
                .isEqualTo("Erro ao obter o Access Token do Authorization Server.");

        assertThat(translated.getCause())
                .isSameAs(exception);

    }

}