package br.com.marhasoft.oauth.client.internal;

import br.com.marhasoft.oauth.client.exception.OAuthClientException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Serviço de Access Token")
class DefaultAccessTokenServiceTest {

    @Mock
    private OAuth2AuthorizedClientManager manager;

    @InjectMocks
    private DefaultAccessTokenService service;

    @Test
    @DisplayName("Deve retornar o Access Token")
    void shouldReturnAccessToken() {

        ClientRegistration registration =
                ClientRegistration.withRegistrationId("oauth-client")
                        .tokenUri("http://localhost")
                        .clientId("id")
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .build();

        OAuth2AccessToken token =
                new OAuth2AccessToken(
                        OAuth2AccessToken.TokenType.BEARER,
                        "abc123",
                        Instant.now(),
                        Instant.now().plusSeconds(3600));

        OAuth2AuthorizedClient client =
                new OAuth2AuthorizedClient(registration, "principal", token);

        when(manager.authorize(any())).thenReturn(client);

        String accessToken = service.getAccessToken();

        assertThat(accessToken)
                .isEqualTo("abc123");

        ArgumentCaptor<OAuth2AuthorizeRequest> captor =
                ArgumentCaptor.forClass(OAuth2AuthorizeRequest.class);

        verify(manager).authorize(captor.capture());

        assertThat(captor.getValue().getClientRegistrationId())
                .isEqualTo(OAuthClientConstants.CLIENT_REGISTRATION_ID);
    }

    @Test
    @DisplayName("Deve lançar OAuthClientException quando o Authorization Server não retornar um Access Token")
    void shouldThrowOAuthClientExceptionWhenAuthorizedClientIsNull() {

        when(manager.authorize(any()))
                .thenReturn(null);

        assertThatThrownBy(service::getAccessToken)
                .isInstanceOf(OAuthClientException.class)
                .hasMessage("O Authorization Server não retornou um Access Token.");
    }

}