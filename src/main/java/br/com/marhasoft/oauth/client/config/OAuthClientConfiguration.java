package br.com.marhasoft.oauth.client.config;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.internal.DefaultAccessTokenService;
import br.com.marhasoft.oauth.client.internal.OAuthClientConstants;
import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Configures the OAuth 2.0 Client infrastructure used to obtain
 * Access Tokens using the Client Credentials grant type.
 *
 * <p>This configuration supports both the single-client
 * configuration and the new multiple-client configuration.</p>
 */
@Configuration(proxyBeanMethods = false)
public class OAuthClientConfiguration {

    private final OAuthClientProperties properties;

    public OAuthClientConfiguration(OAuthClientProperties properties) {
        this.properties = properties;
    }

    /**
     * Creates the repository containing all configured OAuth clients.
     *
     * <p>If multiple clients are configured, all of them are registered.
     * Otherwise, the legacy single-client configuration is used.</p>
     */
    @Bean
    @ConditionalOnMissingBean
    public ClientRegistrationRepository clientRegistrationRepository() {

        List<ClientRegistration> registrations = new ArrayList<>();

        // Registers all configured OAuth clients.
        if (!properties.getClients().isEmpty()) {

            properties.getClients().forEach((name, client) ->
                    registrations.add(createClientRegistration(name, client)));

        } else {

            // Backward compatibility with the legacy single-client configuration.
            registrations.add(
                    createClientRegistration(
                            OAuthClientConstants.CLIENT_REGISTRATION_ID,
                            properties.getClient()));
        }

        return new InMemoryClientRegistrationRepository(registrations);
    }

    /**
     * Creates the service responsible for storing authorized clients
     * and their respective Access Tokens.
     */
    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository repository) {

        return new InMemoryOAuth2AuthorizedClientService(repository);
    }

    /**
     * Configures the OAuth2 provider to use the Client Credentials flow.
     */
    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizedClientProvider authorizedClientProvider() {

        return OAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials()
                .build();
    }

    /**
     * Creates the manager responsible for authorizing OAuth clients
     * and automatically obtaining or renewing Access Tokens when necessary.
     */
    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository repository,
            OAuth2AuthorizedClientService service,
            OAuth2AuthorizedClientProvider provider) {

        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        repository,
                        service);

        manager.setAuthorizedClientProvider(provider);

        return manager;
    }

    /**
     * Exposes the AccessTokenService used by applications to obtain
     * Access Tokens from the configured Authorization Server.
     */
    @Bean
    @ConditionalOnMissingBean
    public AccessTokenService accessTokenService(
            OAuth2AuthorizedClientManager manager) {

        return new DefaultAccessTokenService(manager, properties);
    }

    /**
     * Resolves the OAuth 2.0 Token Endpoint based on the configured
     * Authorization Server URL.
     */
    private String resolveTokenEndpoint() {

        return properties.getServerUrl()
                .resolve(OAuthClientConstants.TOKEN_ENDPOINT)
                .toString();
    }

    /**
     * Creates a ClientRegistration for a configured OAuth client.
     */
    private ClientRegistration createClientRegistration(
            String registrationId,
            OAuthClientProperties.Client client) {

        return ClientRegistration.withRegistrationId(registrationId)
                .clientId(client.getId())
                .clientSecret(client.getSecret())
                .authorizationGrantType(
                        AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(
                        ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .tokenUri(resolveTokenEndpoint())
                .build();
    }

}