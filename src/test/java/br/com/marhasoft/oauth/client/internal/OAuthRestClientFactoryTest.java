package br.com.marhasoft.oauth.client.internal;

import br.com.marhasoft.oauth.client.api.AccessTokenService;
import br.com.marhasoft.oauth.client.api.OAuthRestClientFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("OAuth RestClient Factory")
class OAuthRestClientFactoryTest {

    @Test
    @DisplayName("Deve criar um RestClient")
    void shouldCreateRestClient() {

        RestClient.Builder builder = RestClient.builder();

        AccessTokenService accessTokenService =
                mock(AccessTokenService.class);

        OAuthRestClientFactory factory =
                new DefaultOAuthRestClientFactory(
                        builder,
                        accessTokenService);

        RestClient restClient = factory.create();

        assertThat(restClient)
                .isNotNull();

    }

    @Test
    @DisplayName("Deve retornar sempre a mesma instância do RestClient")
    void shouldReturnSameRestClientInstance() {

        RestClient.Builder builder = RestClient.builder();

        AccessTokenService accessTokenService =
                mock(AccessTokenService.class);

        OAuthRestClientFactory factory =
                new DefaultOAuthRestClientFactory(
                        builder,
                        accessTokenService);

        RestClient first = factory.create();
        RestClient second = factory.create();

        assertThat(first)
                .isSameAs(second);

    }

}