package it.unimol.newunimol.user_roles_management;

import it.unimol.newunimol.user_roles_management.service.AuthService;
import it.unimol.newunimol.user_roles_management.service.RoleService;
import it.unimol.newunimol.user_roles_management.service.TokenJWTService;
import it.unimol.newunimol.user_roles_management.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewunimolApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@Autowired
	private TokenJWTService tokenJWTService;

	@Autowired
	private RoleService roleService;

	@Test
	void contextLoads() {
		assertNotNull(applicationContext);
	}

	@Test
	void testUserServiceBeanExists() {
		assertNotNull(userService);
	}

	@Test
	void testAuthServiceBeanExists() {
		assertNotNull(authService);
	}

	@Test
	void testTokenJWTServiceBeanExists() {
		assertNotNull(tokenJWTService);
	}

	@Test
	void testRoleServiceBeanExists() {
		assertNotNull(roleService);
	}

	@Test
	void testApplicationContextContainsServices() {
		assertTrue(applicationContext.containsBean("userService"));
		assertTrue(applicationContext.containsBean("authService"));
		assertTrue(applicationContext.containsBean("tokenJWTService"));
		assertTrue(applicationContext.containsBean("roleService"));
	}

	@Test
	void testServicesAreNotNull() {
		assertAll(
			() -> assertNotNull(userService),
			() -> assertNotNull(authService),
			() -> assertNotNull(tokenJWTService),
			() -> assertNotNull(roleService)
		);
	}
}
