package br.com.marhasoft.oauth.client.autoconfigure;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.config.OAuthClientConfiguration;
import br.com.marhasoft.oauth.client.config.RestClientConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.web.client.RestClientCustomizer;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Configuração automática do OAuth Client")
class OAuthClientAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(
                            AutoConfigurations.of(OAuthClientAutoConfiguration.class));

    @Test
    @DisplayName("Deve carregar automaticamente os componentes da biblioteca")
    void shouldLoadAutoConfiguration() {

        contextRunner
                .withPropertyValues(
                        "marhasoft.oauth.server-url=http://localhost:9999",
                        "marhasoft.oauth.client.id=test-client",
                        "marhasoft.oauth.client.secret=test-secret"
                )
                .run(context -> {

                    assertThat(context).hasNotFailed();

                    assertThat(context)
                            .hasSingleBean(OAuthClientConfiguration.class);

                    assertThat(context)
                            .hasSingleBean(RestClientConfiguration.class);

                    assertThat(context)
                            .hasSingleBean(AccessTokenService.class);

                    assertThat(context)
                            .hasSingleBean(RestClientCustomizer.class);

                });

    }

    @Test
    @DisplayName("Deve falhar ao iniciar o contexto quando as propriedades obrigatórias não forem informadas")
    void shouldFailWhenRequiredPropertiesAreMissing() {

        contextRunner.run(context -> {

            assertThat(context).hasFailed();

            assertThat(context.getStartupFailure())
                    .hasMessageContaining("Could not bind properties");

        });

    }

    @Test
    @DisplayName("Não deve carregar a biblioteca quando estiver desabilitada")
    void shouldNotLoadAutoConfigurationWhenDisabled() {

        contextRunner
                .withPropertyValues(
                        "marhasoft.oauth.enabled=false"
                )
                .run(context -> {

                    assertThat(context).hasNotFailed();

                    assertThat(context)
                            .doesNotHaveBean(AccessTokenService.class);

                    assertThat(context)
                            .doesNotHaveBean(RestClientCustomizer.class);

                });

    }

}