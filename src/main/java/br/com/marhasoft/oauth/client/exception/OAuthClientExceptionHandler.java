package br.com.marhasoft.oauth.client.exception;

import org.springframework.security.oauth2.client.ClientAuthorizationException;

public final class OAuthClientExceptionHandler {

    private OAuthClientExceptionHandler() {
    }

    public static OAuthClientException translate(Throwable ex) {

        if (ex instanceof OAuthClientException oauthException) {
            return oauthException;
        }

        if (ex instanceof ClientAuthorizationException) {
            return new OAuthClientException(
                    "Falha na autenticação do cliente OAuth. Verifique o Client ID e o Client Secret.",
                    ex);
        }

        // Workaround para um comportamento do Spring Security quando o
        // Authorization Server retorna uma resposta inesperada durante
        // o fluxo Client Credentials.
        if (ex instanceof NullPointerException
                && ex.getMessage() != null
                && ex.getMessage().contains("tokenResponse")) {

            return new OAuthClientException(
                    "O Authorization Server retornou uma resposta inesperada ao solicitar o Access Token. Verifique os logs do Authorization Server para mais detalhes.",
                    ex);
        }

        return new OAuthClientException(
                "Erro ao obter o Access Token do Authorization Server.",
                ex);
    }
}