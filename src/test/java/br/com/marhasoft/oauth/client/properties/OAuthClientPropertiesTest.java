package br.com.marhasoft.oauth.client.properties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Propriedades do cliente OAuth")
class OAuthClientPropertiesTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(
                            AutoConfigurations.of(TestConfiguration.class)
                    );

    @Test
    @DisplayName("Deve carregar as propriedades corretamente")
    void shouldBindProperties() {

        contextRunner
                .withPropertyValues(
                        "marhasoft.oauth.server-url=http://localhost:9999",
                        "marhasoft.oauth.client.id=test-client",
                        "marhasoft.oauth.client.secret=test-secret"
                )
                .run(context -> {

                    assertThat(context).hasNotFailed();

                    OAuthClientProperties properties =
                            context.getBean(OAuthClientProperties.class);

                    assertThat(properties.getServerUrl())
                            .isEqualTo(URI.create("http://localhost:9999"));

                    assertThat(properties.getClient().getId())
                            .isEqualTo("test-client");

                    assertThat(properties.getClient().getSecret())
                            .isEqualTo("test-secret");

                });

    }

    @Test
    @DisplayName("Deve falhar quando a URL do servidor não for informada")
    void shouldFailWhenServerUrlIsMissing() {

        contextRunner
                .withPropertyValues(
                        "marhasoft.oauth.client.id=test-client",
                        "marhasoft.oauth.client.secret=test-secret"
                )
                .run(context -> {

                    assertThat(context).hasFailed();

                });

    }

    @Test
    @DisplayName("Deve falhar quando o Client ID não for informado")
    void shouldFailWhenClientIdIsMissing() {

        contextRunner
                .withPropertyValues(
                        "marhasoft.oauth.server-url=http://localhost:9999",
                        "marhasoft.oauth.client.secret=test-secret"
                )
                .run(context -> {

                    assertThat(context).hasFailed();

                    assertThat(context.getStartupFailure())
                            .hasRootCauseInstanceOf(
                                    org.springframework.boot.context.properties.bind.validation.BindValidationException.class);

                });

    }

    @Test
    @DisplayName("Deve falhar quando o Client Secret não for informado")
    void shouldFailWhenClientSecretIsMissing() {

        contextRunner
                .withPropertyValues(
                        "marhasoft.oauth.server-url=http://localhost:9999",
                        "marhasoft.oauth.client.id=test-client"
                )
                .run(context -> {

                    assertThat(context).hasFailed();

                    assertThat(context.getStartupFailure())
                            .hasRootCauseInstanceOf(
                                    org.springframework.boot.context.properties.bind.validation.BindValidationException.class);

                });

    }

    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(OAuthClientProperties.class)
    static class TestConfiguration {
    }

}