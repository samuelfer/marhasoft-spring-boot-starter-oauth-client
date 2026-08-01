package br.com.marhasoft.oauth.client.config;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.web.client.RestClientCustomizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("Configuração do RestClient")
class RestClientConfigurationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(
                            AutoConfigurations.of(RestClientConfiguration.class))
                    .withBean(
                            AccessTokenService.class,
                            () -> mock(AccessTokenService.class));

    @Test
    @DisplayName("Deve criar o RestClientCustomizer")
    void shouldCreateRestClientCustomizer() {

        contextRunner.run(context -> {

            assertThat(context)
                    .hasSingleBean(OAuthRestClientFactory.class);

        });

    }

    @Test
    @DisplayName("Deve utilizar o RestClientCustomizer fornecido pela aplicação")
    void shouldUseCustomRestClientCustomizer() {

        contextRunner
                .withBean(
                        "oauthRestClientCustomizer",
                        RestClientCustomizer.class,
                        () -> builder -> {
                        })
                .run(context -> {

                    assertThat(context)
                            .hasSingleBean(OAuthRestClientFactory.class);

                });

    }

}