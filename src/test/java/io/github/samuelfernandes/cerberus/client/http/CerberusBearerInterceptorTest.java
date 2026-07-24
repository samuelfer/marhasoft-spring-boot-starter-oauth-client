package io.github.samuelfernandes.cerberus.client.http;

import io.github.samuelfernandes.cerberus.client.service.CerberusTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CerberusBearerInterceptorTest {

    @Test
    void deveAdicionarBearerToken() throws Exception {

        CerberusTokenService tokenService = mock(CerberusTokenService.class);

        when(tokenService.getAccessToken())
                .thenReturn("abc123");

        CerberusBearerInterceptor interceptor =
                new CerberusBearerInterceptor(tokenService);

        MockClientHttpRequest request =
                new MockClientHttpRequest();

        ClientHttpRequestExecution execution =
                mock(ClientHttpRequestExecution.class);

        when(execution.execute(any(), any()))
                .thenReturn(new MockClientHttpResponse(new byte[0], 200));

        interceptor.intercept(request, new byte[0], execution);

        assertEquals(
                "Bearer abc123",
                request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        verify(tokenService).getAccessToken();
        verify(execution).execute(any(), any());
    }
}