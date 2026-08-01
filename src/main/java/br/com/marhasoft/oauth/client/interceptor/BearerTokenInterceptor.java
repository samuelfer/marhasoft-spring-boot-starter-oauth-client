package br.com.marhasoft.oauth.client.interceptor;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

    private final AccessTokenService accessTokenService;
    private final String client;


    public BearerTokenInterceptor(AccessTokenService accessTokenService,
                                  String client) {
        this.accessTokenService = accessTokenService;
        this.client = client;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution)
            throws IOException {

        request.getHeaders()
                .setBearerAuth(
                        accessTokenService.getAccessToken(client));

        return execution.execute(request, body);
    }
}