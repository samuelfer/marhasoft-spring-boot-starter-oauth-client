package io.github.samuelfernandes.cerberus.client.http;

import io.github.samuelfernandes.cerberus.client.service.CerberusTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@RequiredArgsConstructor
public class CerberusBearerInterceptor implements ClientHttpRequestInterceptor {

    private final CerberusTokenService tokenService;

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