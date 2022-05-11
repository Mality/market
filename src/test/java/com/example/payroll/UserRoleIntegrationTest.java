package com.example.payroll;

import com.example.payroll.persistence.dao.RoleRepository;
import com.example.payroll.persistence.dao.UserRepository;
import com.example.payroll.persistence.model.Role;
import com.example.payroll.persistence.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

//import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@DataJpaTest
//@TestPropertySource(
//        locations = "classpath:application-integration-test.properties"
//)
public class UserRoleIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testUserRoles() {
        User user1 = new User("user1", 100L);
        User user2 = new User("user2", 100L);

        Role role1 = new Role("user");
        Role role2 = new Role("admin");

        user1.addRole(role1);
        user1.addRole(role2);
        user2.addRole(role1);

        entityManager.persist(role1);
        entityManager.persist(role2);
        entityManager.persist(user1);
        entityManager.persist(user2);

        entityManager.flush();

        User foundUser1 = userRepository.getById(user1.getId());
        User foundUser2 = userRepository.getById(user2.getId());

        assertThat(foundUser1).isEqualTo(user1);
        assertThat(foundUser2).isEqualTo(user2);
    }
}
