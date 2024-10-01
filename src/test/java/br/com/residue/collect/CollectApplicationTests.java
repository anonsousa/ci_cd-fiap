package br.com.residue.collect;

import br.com.residue.collect.infra.security.TokenService;
import br.com.residue.collect.infra.security.VerifyToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CollectApplicationTests {

	@MockBean
	private TokenService tokenService;

	@MockBean
	private VerifyToken verifyToken;

	@Test
	void contextLoads() {
	}

}
