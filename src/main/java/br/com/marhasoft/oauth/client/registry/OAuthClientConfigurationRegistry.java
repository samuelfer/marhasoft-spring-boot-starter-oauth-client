package br.com.marhasoft.oauth.client.registry;

import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;

import java.util.Map;

public class OAuthClientConfigurationRegistry {

    private final OAuthClientProperties properties;

    public OAuthClientConfigurationRegistry(OAuthClientProperties properties) {
        this.properties = properties;
    }

    public OAuthClientProperties.Client getDefaultClient() {

        if (!properties.getClients().isEmpty()) {

            String defaultClient = properties.getDefaultClient();

            if (defaultClient == null) {
                throw new IllegalStateException(
                        "Nenhum cliente padrão foi configurado.");
            }

            OAuthClientProperties.Client client =
                    properties.getClients().get(defaultClient);

            if (client == null) {
                throw new IllegalStateException(
                        "Cliente padrão '" + defaultClient + "' não encontrado.");
            }

            return client;
        }

        return properties.getClient();
    }

    public OAuthClientProperties.Client getClient(String name) {

        if (properties.getClients().isEmpty()) {
            return properties.getClient();
        }

        OAuthClientProperties.Client client =
                properties.getClients().get(name);

        if (client == null) {
            throw new IllegalArgumentException(
                    "Cliente OAuth '" + name + "' não encontrado.");
        }

        return client;
    }

    public Map<String, OAuthClientProperties.Client> getClients() {
        return properties.getClients();
    }

}