package br.com.marhasoft.oauth.client.auth;

import br.com.marhasoft.oauth.client.internal.OAuthClientConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Authentication utilizada internamente pelo OAuth Client para obtenção
 * de Access Tokens através do fluxo OAuth 2.0 Client Credentials.
 */
public final class ClientAuthentication extends AnonymousAuthenticationToken {

    private static final String KEY = OAuthClientConstants.CLIENT_REGISTRATION_ID;
    private static final String PRINCIPAL = OAuthClientConstants.CLIENT_REGISTRATION_ID;

    public ClientAuthentication() {
        super(
                KEY,
                PRINCIPAL,
                AuthorityUtils.createAuthorityList("ROLE_OAUTH_CLIENT")
        );
    }
}