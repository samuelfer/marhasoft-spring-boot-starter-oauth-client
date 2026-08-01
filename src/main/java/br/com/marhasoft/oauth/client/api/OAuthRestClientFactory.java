package br.com.marhasoft.oauth.client.api;

import org.springframework.web.client.RestClient;

public interface OAuthRestClientFactory {

    RestClient create();

}