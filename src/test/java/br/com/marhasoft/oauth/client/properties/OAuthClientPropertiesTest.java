package br.com.marhasoft.oauth.client.properties;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class OAuthClientPropertiesTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(
                            AutoConfigurations.of(TestConfiguration.class)
                    );

    @Test
    void shouldBindProperties() {

        contextRunner
                .withPropertyValues(
                        "cerberus.server-url=http://localhost:9999",
                        "cerberus.client.id=test-client",
                        "cerberus.client.secret=test-secret"
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
    void shouldFailWhenServerUrlIsMissing() {

        contextRunner
                .withPropertyValues(
                        "cerberus.client.id=test-client",
                        "cerberus.client.secret=test-secret"
                )
                .run(context -> {

                    assertThat(context).hasFailed();

                });

    }

    @Test
    void shouldFailWhenClientIdIsMissing() {

        contextRunner
                .withPropertyValues(
                        "cerberus.server-url=http://localhost:9999",
                        "cerberus.client.secret=test-secret"
                )
                .run(context -> {

                    assertThat(context).hasFailed();

                    assertThat(context.getStartupFailure())
                            .hasRootCauseInstanceOf(
                                    org.springframework.boot.context.properties.bind.validation.BindValidationException.class);

                });

    }

    @Test
    void shouldFailWhenClientSecretIsMissing() {

        contextRunner
                .withPropertyValues(
                        "cerberus.server-url=http://localhost:9999",
                        "cerberus.client.id=test-client"
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