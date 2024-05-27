package com.project.project.Repositorytest;


import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.project.project.repositories.UserRepositories;
import com.project.project.model.User;

@SpringBootTest
public class UserRepositoryTest {

    private UserRepositories userRepository;
    @Autowired
    public UserRepositoryTest(ApplicationContext context) {
        this.userRepository = context.getBean(UserRepositories.class);
    }

    //
    @Test
    public void findByUsernameNotFoundTest() {
        String username = "aa";
        User dbUser = userRepository.findByUsername(username);
        assertNull(dbUser);
    }
}
