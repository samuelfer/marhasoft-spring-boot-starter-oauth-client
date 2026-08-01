package br.com.marhasoft.oauth.client.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Configuration properties for the MarhaSoft OAuth Client.
 */
@ConfigurationProperties(prefix = "marhasoft.oauth")
@Validated
public class OAuthClientProperties {

    private boolean enabled = true;

    @NotNull
    private URI serverUrl;

    /**
     * Configuração legada (mantida por compatibilidade).
     */
    @Valid
    private final Client client = new Client();

    /**
     * Nome do cliente padrão.
     */
    private String defaultClient;

    /**
     * Configuração para múltiplos clientes.
     */
    @Valid
    private final Map<String, Client> clients = new LinkedHashMap<>();

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

    public String getDefaultClient() {
        return defaultClient;
    }

    public void setDefaultClient(String defaultClient) {
        this.defaultClient = defaultClient;
    }

    public Map<String, Client> getClients() {
        return clients;
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

    @AssertTrue(message = "Configure 'client' ou 'clients'.")
    public boolean isValidConfiguration() {

        boolean legacy =
                client.getId() != null
                        && !client.getId().isBlank()
                        && client.getSecret() != null
                        && !client.getSecret().isBlank();

        boolean multiple =
                !clients.isEmpty();

        return legacy || multiple;
    }
}