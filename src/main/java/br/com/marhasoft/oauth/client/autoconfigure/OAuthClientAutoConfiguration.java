package br.com.marhasoft.oauth.client.autoconfigure;

import br.com.marhasoft.oauth.client.config.OAuthClientConfiguration;
import br.com.marhasoft.oauth.client.config.RestClientConfiguration;
import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties(OAuthClientProperties.class)
@ConditionalOnProperty(prefix = "marhasoft.oauth", name = "enabled", matchIfMissing = true)
@Import({
        OAuthClientConfiguration.class,
        RestClientConfiguration.class
})
public class OAuthClientAutoConfiguration {
}
