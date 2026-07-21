package br.gov.pb.tce.cerberus.client;

import br.gov.pb.tce.cerberus.client.properties.CerberusProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(CerberusProperties.class)
@ConditionalOnProperty(prefix = "cerberus", name = "enabled", matchIfMissing = true)
public class CerberusAutoConfiguration {
}
