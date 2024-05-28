package com.project.project.Repositorytest;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.controller.UserController;
import com.project.project.model.User;
import com.project.project.repositories.UserRepositories;
import com.project.project.repositories.UserRepositry;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRepositoryTest {

    @Mock
    private UserRepositry userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    private UserRepositories uRepositories;

    @Test
    public void findByUsernameNotFoundTest() {
        String username = "Fares";
        User dbUser = uRepositories.findByUsername(username);
        assertNull(dbUser);
    }

    @Test
    public void testUsernameExistsInDatabase() {
        // Create a new user with an existing username
        User existingUser = new User();
        existingUser.setUsername("Fady");
        when(userRepository.findByUsername("Fady")).thenReturn(existingUser);
        existingUser.setPassword("password"); // Set a non-null password

        // Mock BindingResult and Model
        BindingResult bindingResult = mock(BindingResult.class);
        Model model = mock(Model.class);

        // Call the saveUser method of UserController
        ModelAndView modelAndView = userController.saveUser(existingUser, bindingResult, "password", model);

        // Verify that the view name is "login.html"
        assertEquals("register.html", modelAndView.getViewName());

        // Verify that the userRepository.findByUsername method is called with the
        // correct username
        verify(userRepository, times(1)).findByUsername("Fady");

        // Check if the username exists in the database
        User userFromDatabase = uRepositories.findByUsername("Fady");
        assertNotNull(userFromDatabase, "The username 'Fady' should exist in the database");
    }

    @Test
    void testValidLogin() {
        // Mock UserRepositry
        UserRepositry userRepository = mock(UserRepositry.class);

        // Create a user with valid credentials
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("$2a$12$FVZxUMthVnQDk9ulNp8.1eq6do/48oimwNpE7JdWBTHrFkGx7IjQO"); // Hashed password

        // Mock the userRepositry to return the user when findByUsername is called
        when(userRepository.findByUsername("validUser")).thenReturn(user);

        // Create a UserController instance with the mocked userRepository
        UserController userController = new UserController(userRepository);

        // Create session mock
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        // Perform login
        ModelAndView modelAndView = userController.loginProcess("validUser", "password", model, session);

        // Assert that the modelAndView redirects to the expected view
        assertEquals("index.html", modelAndView.getViewName());
    }

}