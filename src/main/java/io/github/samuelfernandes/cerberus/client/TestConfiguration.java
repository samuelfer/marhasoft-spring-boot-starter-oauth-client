package io.github.samuelfernandes.cerberus.client;

import io.github.samuelfernandes.cerberus.client.configuration.OAuth2ClientConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@Import(OAuth2ClientConfiguration.class)
public class TestConfiguration {
}