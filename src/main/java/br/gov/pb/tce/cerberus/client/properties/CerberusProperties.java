package br.gov.pb.tce.cerberus.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "cerberus")
public class CerberusProperties {

    private boolean enabled = true;

    private URI issuerUri;

    private final Client client = new Client();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public URI getIssuerUri() {
        return issuerUri;
    }

    public void setIssuerUri(URI issuerUri) {
        this.issuerUri = issuerUri;
    }

    public Client getClient() {
        return client;
    }

    public static class Client {

        private String id;

        private String secret;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
