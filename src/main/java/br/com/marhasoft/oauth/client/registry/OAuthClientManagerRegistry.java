package br.com.marhasoft.oauth.client.registry;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public interface OAuthClientManagerRegistry {

    OAuth2AuthorizedClientManager getDefaultManager();

    OAuth2AuthorizedClientManager getManager(String clientName);

}