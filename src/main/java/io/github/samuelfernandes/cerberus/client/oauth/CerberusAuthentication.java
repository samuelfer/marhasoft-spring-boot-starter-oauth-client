package io.github.samuelfernandes.cerberus.client.oauth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Authentication utilizada internamente pelo Cerberus para obtenção
 * de Access Tokens através do fluxo Client Credentials.
 */
public final class CerberusAuthentication extends AnonymousAuthenticationToken {

    private static final String KEY = "cerberus-client";
    private static final String PRINCIPAL = "cerberus-client";

    public CerberusAuthentication() {
        super(
                KEY,
                PRINCIPAL,
                AuthorityUtils.createAuthorityList("ROLE_CERBERUS_CLIENT")
        );
    }
}