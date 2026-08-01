package br.com.marhasoft.oauth.client.config;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.internal.OAuthClientConstants;
import br.com.marhasoft.oauth.client.internal.DefaultAccessTokenService;
import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * Configures the OAuth2 Client infrastructure used to obtain
 * Access Tokens through the Client Credentials grant.
 */
@Configuration(proxyBeanMethods = false)
public class OAuthClientConfiguration {

    private final OAuthClientProperties properties;

    private static final Logger log =
            LoggerFactory.getLogger(OAuthClientConfiguration.class);


    public OAuthClientConfiguration(OAuthClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientRegistration clientRegistration() {

        log.info("Client ID: {}", properties.getClient().getId());
        log.info("Client Secret: {}", properties.getClient().getSecret());

        return ClientRegistration
                .withRegistrationId(OAuthClientConstants.CLIENT_REGISTRATION_ID)
                .clientId(properties.getClient().getId())
                .clientSecret(properties.getClient().getSecret())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .tokenUri(resolveTokenEndpoint())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientRegistrationRepository clientRegistrationRepository(ClientRegistration registration) {
        return new InMemoryClientRegistrationRepository(registration);
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository repository) {

        return new InMemoryOAuth2AuthorizedClientService(repository);
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizedClientProvider authorizedClientProvider() {
        return  OAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials()
                .build();
    }

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

    @Bean
    @ConditionalOnMissingBean
    public AccessTokenService accessTokenService(
            OAuth2AuthorizedClientManager manager) {

        return new DefaultAccessTokenService(manager);
    }

    private String resolveTokenEndpoint() {
        return properties.getServerUrl()
                .resolve(OAuthClientConstants.TOKEN_ENDPOINT)
                .toString();
    }

}