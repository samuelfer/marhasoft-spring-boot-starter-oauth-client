package io.github.samuelfernandes.cerberus.client.configuration;

import io.github.samuelfernandes.cerberus.client.oauth.CerberusOAuth2Constants;
import io.github.samuelfernandes.cerberus.client.properties.CerberusProperties;
import io.github.samuelfernandes.cerberus.client.service.CerberusTokenService;
import io.github.samuelfernandes.cerberus.client.service.DefaultCerberusTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CerberusProperties.class)
@ConditionalOnProperty(
        prefix = "cerberus",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class OAuth2ClientConfiguration {

    private final CerberusProperties properties;

    @Bean
    ClientRegistration clientRegistration() {

        return ClientRegistration
                .withRegistrationId(CerberusOAuth2Constants.REGISTRATION_ID)
                .clientId(properties.getClient().getId())
                .clientSecret(properties.getClient().getSecret())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .tokenUri(properties.getServerUrl()
                        .resolve(CerberusOAuth2Constants.TOKEN_URI)
                        .toString()
                )
                .build();
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository(ClientRegistration registration) {
        return new InMemoryClientRegistrationRepository(registration);
    }

    @Bean
    OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository repository) {

        return new InMemoryOAuth2AuthorizedClientService(repository);
    }

    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository repository,
            OAuth2AuthorizedClientService service) {

        OAuth2AuthorizedClientProvider provider =
                OAuth2AuthorizedClientProviderBuilder
                        .builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        repository,
                        service);

        manager.setAuthorizedClientProvider(provider);

        return manager;
    }

    @Bean
    CerberusTokenService cerberusTokenService(
            OAuth2AuthorizedClientManager manager) {

        return new DefaultCerberusTokenService(manager);
    }

}