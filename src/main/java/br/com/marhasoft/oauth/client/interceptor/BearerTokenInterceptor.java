package br.com.marhasoft.oauth.client.interceptor;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

//@RequiredArgsConstructor
public class BearerTokenInterceptor implements ClientHttpRequestInterceptor {

    private final AccessTokenService tokenService;

    public BearerTokenInterceptor(AccessTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution)
            throws IOException {

        request.getHeaders()
                .setBearerAuth(tokenService.getAccessToken());

        return execution.execute(request, body);
    }
}