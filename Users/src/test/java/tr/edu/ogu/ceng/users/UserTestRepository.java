package tr.edu.ogu.ceng.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import tr.edu.ogu.ceng.Users.entity.Users;
import tr.edu.ogu.ceng.Users.repository.UsersRepository;

@SpringBootTest
public class UserTestRepository {

	@Container
	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			DockerImageName.parse("postgres:16-alpine"));

	static {

		postgres.start();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.datasource.url", postgres::getJdbcUrl);

		registry.add("spring.datasource.username", postgres::getUsername);

		registry.add("spring.datasource.password", postgres::getPassword);

	}

	@Autowired
	private UsersRepository repository;

	@Test
	public void test() {
		Users user = new Users();
		repository.save(user);

	}

}
