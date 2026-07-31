package br.com.marhasoft.oauth.client.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

/**
 * Configuration properties for the MarhaSoft OAuth Client.
 */
@ConfigurationProperties(prefix = "marhasoft.oauth")
@Validated
public class OAuthClientProperties {

    private boolean enabled = true;

    @NotNull
    private URI serverUrl;

    @Valid
    private final Client client = new Client();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public URI getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(URI serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Client getClient() {
        return client;
    }

    public static class Client {

        @NotBlank
        private String id;

        @NotBlank
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