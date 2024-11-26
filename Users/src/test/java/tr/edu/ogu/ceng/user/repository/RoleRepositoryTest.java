package tr.edu.ogu.ceng.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import tr.edu.ogu.ceng.User.entity.Role;
import tr.edu.ogu.ceng.User.repository.RoleRepository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

@SpringBootTest
public class RoleRepositoryTest {

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
    private RoleRepository repository;

    @Test
    public void saveRoleTest() {
        Role role = new Role();
        role.setRoleName("ADMIN");
        Role savedRole = repository.save(role);

        assertNotNull(savedRole.getId()); // Role ID null olmamalı
        assertEquals("ADMIN", savedRole.getRoleName()); // Role adı doğru kaydedilmiş mi kontrolü
    }

    @Test
    public void findByNameTest() {
        Role role = new Role();
        role.setRoleName("USER");
        repository.save(role);

        Optional<Role> foundRole = repository.findByRoleName("USER");

        assertTrue(foundRole.isPresent());
        assertEquals("USER", foundRole.get().getRoleName());
    }
}