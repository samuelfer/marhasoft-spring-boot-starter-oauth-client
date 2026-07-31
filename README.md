# MarhaSoft OAuth Client Spring Boot Starter

Spring Boot Starter para integração automática com servidores de autorização OAuth 2.0 utilizando o fluxo **Client Credentials**.

## Objetivo

Simplificar a configuração de clientes OAuth em aplicações Spring Boot, fornecendo automaticamente toda a infraestrutura necessária para obtenção e gerenciamento de Access Tokens.

Com apenas algumas propriedades de configuração, a biblioteca disponibiliza um serviço para obtenção de Access Tokens e configura automaticamente o suporte ao OAuth Client.

## Funcionalidades

- Auto Configuration para Spring Boot
- Configuração automática do OAuth 2.0 Client
- Suporte ao fluxo **Client Credentials**
- Gerenciamento automático de Access Tokens
- Integração com `RestClient`
- Configuração através de `@ConfigurationProperties`
- Validação das propriedades obrigatórias (_Fail Fast_)

## Requisitos

- Java 21+
- Spring Boot 4.x

## Instalação

Enquanto a biblioteca não estiver publicada em um repositório Maven remoto, execute:

```bash
mvn clean install
```

Isso instalará a biblioteca no repositório Maven local (`~/.m2/repository`).

Depois, adicione a dependência ao projeto:

```xml
<dependency>
    <groupId>br.com.marhasoft</groupId>
    <artifactId>spring-boot-starter-oauth-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Configuração

Adicione as propriedades da biblioteca em seu `application.yml`:

```yaml
marhasoft:
  oauth:
    enabled: true
    server-url: https://oauth.example.com
    client:
      id: my-client
      secret: my-secret
```

| Propriedade                     | Obrigatória | Descrição                                                 |
| ------------------------------- | :---------: | --------------------------------------------------------- |
| `marhasoft.oauth.enabled`       |     Não     | Habilita ou desabilita a auto configuração da biblioteca. |
| `marhasoft.oauth.server-url`    |     Sim     | URL base do Authorization Server OAuth 2.0.               |
| `marhasoft.oauth.client.id`     |     Sim     | Client ID utilizado para autenticação.                    |
| `marhasoft.oauth.client.secret` |     Sim     | Client Secret utilizado para autenticação.                |

## Utilização

Após adicionar a dependência e configurar as propriedades, basta injetar o serviço `AccessTokenService`.

```java
@Service
public class ExampleService {

    private final AccessTokenService accessTokenService;

    public ExampleService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    public void execute() {

        String accessToken = accessTokenService.getAccessToken();

        // Utilize o Access Token conforme necessário.
    }

}
```

## Estrutura do Projeto

```
br.com.marhasoft.oauth.client
├── api
├── auth
├── autoconfigure
├── config
├── interceptor
├── internal
└── properties
```

## Como funciona

Durante a inicialização da aplicação, a biblioteca:

1. Lê as propriedades configuradas em `application.yml`;
2. Configura automaticamente o cliente OAuth 2.0;
3. Registra os componentes necessários para autenticação;
4. Gerencia automaticamente a obtenção e reutilização do Access Token;
5. Disponibiliza o `AccessTokenService` para utilização pela aplicação.

## Primeira Versão

A versão inicial da biblioteca contempla:

- Estrutura inicial do projeto
- Auto Configuration
- Configuration Properties
- Configuração automática do OAuth Client
- OAuth 2.0 Client Credentials
- Gerenciamento automático de Access Tokens
- Integração com `RestClient`
- Testes unitários
- Testes de Auto Configuration
- Documentação inicial

## Roadmap

Funcionalidades planejadas para as próximas versões:

- Refresh automático de Access Token
- Retry configurável
- Timeout configurável
- Observabilidade com Micrometer
- Exemplos completos de utilização
- Integração com WireMock para testes

## Licença

Este projeto é distribuído sob a licença definida neste repositório.
