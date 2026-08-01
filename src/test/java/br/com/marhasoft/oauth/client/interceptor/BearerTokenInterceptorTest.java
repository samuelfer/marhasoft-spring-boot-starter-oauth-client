package br.com.marhasoft.oauth.client.interceptor;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Interceptor Bearer")
class BearerTokenInterceptorTest {

    @Test
    @DisplayName("Deve adicionar o header Authorization")
    void shouldAddBearerHeader() throws Exception {

        AccessTokenService tokenService =
                mock(AccessTokenService.class);

        when(tokenService.getAccessToken("diligencia"))
                .thenReturn("abc123");

        HttpRequest request = mock(HttpRequest.class);
        HttpHeaders headers = new HttpHeaders();

        when(request.getHeaders()).thenReturn(headers);
        when(request.getURI()).thenReturn(URI.create("http://localhost"));

        ClientHttpRequestExecution execution =
                mock(ClientHttpRequestExecution.class);

        ClientHttpResponse response =
                mock(ClientHttpResponse.class);

        when(execution.execute(any(), any()))
                .thenReturn(response);

        BearerTokenInterceptor interceptor =
                new BearerTokenInterceptor(
                        tokenService,
                        "diligencia");

        interceptor.intercept(request, new byte[0], execution);

        assertThat(headers.getFirst(HttpHeaders.AUTHORIZATION))
                .isEqualTo("Bearer abc123");

        verify(tokenService)
                .getAccessToken("diligencia");
    }

}