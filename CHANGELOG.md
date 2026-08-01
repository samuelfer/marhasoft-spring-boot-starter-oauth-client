# Changelog

Todas as mudanças importantes deste projeto serão documentadas neste arquivo.

O formato segue o padrão Keep a Changelog.

---

## [1.1.0] - 2026-08-01

### ✨ Novidades

- Adicionado suporte à configuração de múltiplos clientes OAuth.
- Adicionado suporte ao cliente padrão (`default-client`).
- Adicionado `AccessTokenService#getAccessToken(String client)`.
- Adicionado `OAuthRestClientFactory#create(String client)`.
- Adicionado cache de instâncias do `RestClient` por cliente.

### 🔄 Melhorias

- Mantida compatibilidade com a configuração de cliente único.
- Refatoração da infraestrutura de criação de `ClientRegistration`.
- Atualização dos testes unitários.
- Reestruturação completa do README.
- Inclusão de exemplos de utilização.
- Documentação da arquitetura da biblioteca.
- Inclusão do diagrama de funcionamento.

---

## [1.0.0] - 2026-07-XX

### 🎉 Primeira versão

- Spring Boot Starter para OAuth 2.0 Client Credentials.
- Configuração automática do Spring Security OAuth Client.
- Gerenciamento automático de Access Tokens.
- Renovação automática de tokens.
- RestClient autenticado.
- Tratamento de exceções.
- Auto Configuration.