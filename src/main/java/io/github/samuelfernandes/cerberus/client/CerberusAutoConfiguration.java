package io.github.samuelfernandes.cerberus.client;

import io.github.samuelfernandes.cerberus.client.configuration.OAuth2ClientConfiguration;
import io.github.samuelfernandes.cerberus.client.properties.CerberusProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties(CerberusProperties.class)
@ConditionalOnProperty(prefix = "cerberus", name = "enabled", matchIfMissing = true)
@Import({
        OAuth2ClientConfiguration.class
})
public class CerberusAutoConfiguration {
}
