package br.com.marhasoft.oauth.client.internal;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import br.com.marhasoft.oauth.client.properties.OAuthClientProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("OAuth RestClient Factory")
class OAuthRestClientFactoryTest {

    @Test
    @DisplayName("Deve criar um RestClient utilizando o cliente padrão")
    void shouldCreateRestClient() {

        RestClient.Builder builder = RestClient.builder();

        AccessTokenService accessTokenService =
                mock(AccessTokenService.class);

        OAuthClientProperties properties =
                new OAuthClientProperties();

        OAuthRestClientFactory factory =
                new DefaultOAuthRestClientFactory(
                        builder,
                        accessTokenService,
                        properties);

        RestClient restClient = factory.create();

        assertThat(restClient)
                .isNotNull();
    }

    @Test
    @DisplayName("Deve retornar sempre a mesma instância do RestClient para o cliente padrão")
    void shouldReturnSameRestClientInstance() {

        RestClient.Builder builder = RestClient.builder();

        AccessTokenService accessTokenService =
                mock(AccessTokenService.class);

        OAuthClientProperties properties =
                new OAuthClientProperties();

        OAuthRestClientFactory factory =
                new DefaultOAuthRestClientFactory(
                        builder,
                        accessTokenService,
                        properties);

        RestClient first = factory.create();
        RestClient second = factory.create();

        assertThat(first)
                .isSameAs(second);
    }

    @Test
    @DisplayName("Deve retornar a mesma instância para o mesmo cliente")
    void shouldReturnSameRestClientForClient() {

        RestClient.Builder builder = RestClient.builder();

        AccessTokenService accessTokenService =
                mock(AccessTokenService.class);

        OAuthClientProperties properties =
                new OAuthClientProperties();

        OAuthClientProperties.Client client =
                new OAuthClientProperties.Client();
        client.setId("id");
        client.setSecret("secret");

        properties.setDefaultClient("diligencia");
        properties.getClients().put("diligencia", client);

        OAuthRestClientFactory factory =
                new DefaultOAuthRestClientFactory(
                        builder,
                        accessTokenService,
                        properties);

        RestClient first = factory.create("diligencia");
        RestClient second = factory.create("diligencia");

        assertThat(first)
                .isSameAs(second);
    }

    @Test
    @DisplayName("Deve criar instâncias diferentes para clientes diferentes")
    void shouldCreateDifferentRestClientsForDifferentClients() {

        RestClient.Builder builder = RestClient.builder();

        AccessTokenService accessTokenService =
                mock(AccessTokenService.class);

        OAuthClientProperties properties =
                new OAuthClientProperties();

        OAuthClientProperties.Client diligencia =
                new OAuthClientProperties.Client();
        diligencia.setId("id1");
        diligencia.setSecret("secret1");

        OAuthClientProperties.Client financeiro =
                new OAuthClientProperties.Client();
        financeiro.setId("id2");
        financeiro.setSecret("secret2");

        properties.getClients().put("diligencia", diligencia);
        properties.getClients().put("financeiro", financeiro);

        OAuthRestClientFactory factory =
                new DefaultOAuthRestClientFactory(
                        builder,
                        accessTokenService,
                        properties);

        RestClient first = factory.create("diligencia");
        RestClient second = factory.create("financeiro");

        assertThat(first)
                .isNotSameAs(second);
    }

}