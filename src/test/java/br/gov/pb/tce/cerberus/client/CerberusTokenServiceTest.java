package br.gov.pb.tce.cerberus.client;

import br.gov.pb.tce.cerberus.client.service.CerberusTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestConfiguration.class)
class CerberusTokenServiceTest {

    @Autowired
    private CerberusTokenService tokenService;

    @Test
    void deveObterAccessToken() {
        String token = tokenService.getAccessToken();

        assertNotNull(token);
    }

    @Test
    void deveReutilizarTokenValido() {
        String token1 = tokenService.getAccessToken();
        String token2 = tokenService.getAccessToken();

        assertNotNull(token1);
        assertNotNull(token2);
        assertEquals(token1, token2);
    }
}