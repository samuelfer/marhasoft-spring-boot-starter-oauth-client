package br.com.marhasoft.oauth.client.config;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.internal.OAuthClientConstants;
import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Configuração OAuth2")
class OAuthClientConfigurationTest {

    private OAuthClientConfiguration configuration;
    private OAuthClientProperties properties;

    @BeforeEach
    void setUp() {

        properties = new OAuthClientProperties();
        properties.setServerUrl(URI.create("http://localhost:8080"));

        properties.getClient().setId("client-id");
        properties.getClient().setSecret("client-secret");

        configuration = new OAuthClientConfiguration(properties);
    }

    @Test
    @DisplayName("Deve criar o ClientRegistrationRepository")
    void shouldCreateClientRegistrationRepository() {

        ClientRegistrationRepository repository =
                configuration.clientRegistrationRepository();

        assertThat(repository).isNotNull();

        ClientRegistration registration =
                repository.findByRegistrationId(
                        OAuthClientConstants.CLIENT_REGISTRATION_ID);

        assertThat(registration).isNotNull();
        assertThat(registration.getRegistrationId())
                .isEqualTo(OAuthClientConstants.CLIENT_REGISTRATION_ID);
        assertThat(registration.getClientId())
                .isEqualTo("client-id");
        assertThat(registration.getClientSecret())
                .isEqualTo("client-secret");
        assertThat(registration.getAuthorizationGrantType())
                .isEqualTo(AuthorizationGrantType.CLIENT_CREDENTIALS);
        assertThat(registration.getClientAuthenticationMethod())
                .isEqualTo(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
        assertThat(registration.getProviderDetails().getTokenUri())
                .isEqualTo("http://localhost:8080/oauth/token");
    }

    @Test
    @DisplayName("Deve criar o OAuth2AuthorizedClientService")
    void shouldCreateAuthorizedClientService() {

        ClientRegistrationRepository repository =
                configuration.clientRegistrationRepository();

        OAuth2AuthorizedClientService service =
                configuration.authorizedClientService(repository);

        assertThat(service).isNotNull();
    }

    @Test
    @DisplayName("Deve criar o OAuth2AuthorizedClientProvider")
    void shouldCreateAuthorizedClientProvider() {

        OAuth2AuthorizedClientProvider provider =
                configuration.authorizedClientProvider();

        assertThat(provider).isNotNull();
    }

    @Test
    @DisplayName("Deve criar o OAuth2AuthorizedClientManager")
    void shouldCreateAuthorizedClientManager() {

        ClientRegistrationRepository repository =
                configuration.clientRegistrationRepository();

        OAuth2AuthorizedClientService service =
                configuration.authorizedClientService(repository);

        OAuth2AuthorizedClientProvider provider =
                configuration.authorizedClientProvider();

        OAuth2AuthorizedClientManager manager =
                configuration.authorizedClientManager(
                        repository,
                        service,
                        provider);

        assertThat(manager).isNotNull();
    }

    @Test
    @DisplayName("Deve criar o AccessTokenService")
    void shouldCreateAccessTokenService() {

        ClientRegistrationRepository repository =
                configuration.clientRegistrationRepository();

        OAuth2AuthorizedClientService service =
                configuration.authorizedClientService(repository);

        OAuth2AuthorizedClientProvider provider =
                configuration.authorizedClientProvider();

        OAuth2AuthorizedClientManager manager =
                configuration.authorizedClientManager(
                        repository,
                        service,
                        provider);

        AccessTokenService tokenService =
                configuration.accessTokenService(manager);

        assertThat(tokenService).isNotNull();
    }

}