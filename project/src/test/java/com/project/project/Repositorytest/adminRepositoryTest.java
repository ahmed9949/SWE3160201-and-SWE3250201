package com.project.project.Repositorytest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.controller.UserController;
import com.project.project.controller.adminController;
import com.project.project.model.User;
import com.project.project.repositories.UserRepositories;
import com.project.project.repositories.UserRepositry;

import jakarta.servlet.http.HttpSession;

public class adminRepositoryTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private UserRepositry userRepository;

    @InjectMocks
    private UserController userController;

    @Autowired
    private UserRepositories uRepositories;

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
        adminController adminController = new adminController(userRepository);
        // Call the saveUserByAdmin method of adminController
        ModelAndView modelAndView = adminController.saveUserByAdmin(existingUser, bindingResult, "password", "role",
                model);

        // Verify that the view name is "addUser"
        assertEquals("addUser", modelAndView.getViewName());

        // Verify that the userRepository.findByUsername method is called with the
        // correct username
        verify(userRepository, times(1)).findByUsername("Fady");

        // Check if the username exists in the database
        User userFromDatabase = userRepository.findByUsername("Fady");
        assertNotNull(userFromDatabase, "The username 'Fady' should exist in the database");
    }

    @Test
    public void testLogout() {
        // Mock HttpSession
        HttpSession session = mock(HttpSession.class);

        // Create an instance of UserController
        adminController aController = new adminController(userRepository);

        // Call the logout method
        ModelAndView modelAndView = aController.logout(session);

        // Verify that session.invalidate() is called
        verify(session).invalidate();

        // Verify that ModelAndView redirects to the login page ("/")
        assert modelAndView.getViewName().equals("redirect:/");
    }

    @Test
    public void testDeleteUser() {
        // Mock HttpSession
        HttpSession session = mock(HttpSession.class);

        // Create a sample user ID
        int userId = 1;

        // Mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);

        // Mock behavior for findById method to return a non-null user when called with
        // userId
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        // Create an instance of adminController with userRepository
        adminController aController = new adminController(userRepository);

        // Call the deleteUser method
        ModelAndView modelAndView = aController.deleteUser(userId, session);

        // Verify that userRepository.findById(id) is called with the correct user ID
        verify(userRepository).findById(userId);

        // Verify that userRepository.delete(user) is called if the user is present
        verify(userRepository).delete(any());

        // Verify that ModelAndView redirects to the viewUsers page ("/admin/viewUsers")
        assertEquals("redirect:/admin/viewUsers", modelAndView.getViewName());
    }

}
