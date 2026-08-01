package br.com.marhasoft.oauth.client.exception;

import org.springframework.security.oauth2.client.ClientAuthorizationException;
import org.springframework.web.client.ResourceAccessException;

public final class OAuthClientExceptionHandler {

    private OAuthClientExceptionHandler() {
    }

    public static OAuthClientException translate(Throwable ex) {

        if (ex instanceof OAuthClientException oauthException) {
            return oauthException;
        }

        if (ex instanceof ClientAuthorizationException clientException) {

            String errorCode = clientException.getError().getErrorCode();

            if ("invalid_client".equals(errorCode)) {
                return new OAuthClientException(
                        "Falha na autenticação do cliente OAuth. Verifique o Client ID e o Client Secret.",
                        ex);
            }

            if ("invalid_token_response".equals(errorCode)
                    && containsCause(clientException, ResourceAccessException.class)) {

                return new OAuthClientException(
                        "Não foi possível conectar ao Authorization Server. Verifique se o servidor está disponível.",
                        ex);
            }
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

    private static boolean containsCause(Throwable throwable,
                                         Class<? extends Throwable> type) {

        while (throwable != null) {
            if (type.isInstance(throwable)) {
                return true;
            }
            throwable = throwable.getCause();
        }

        return false;
    }

}