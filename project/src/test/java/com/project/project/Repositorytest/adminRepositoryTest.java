package com.project.project.Repositorytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void testLogout() {
        // Mock HttpSession
        HttpSession session = mock(HttpSession.class);
        
        // Create an instance of UserController
        UserController userController = new UserController(userRepository);
        
        // Call the logout method
        ModelAndView modelAndView = userController.logout(session);
        
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
    
    // Mock behavior for findById method to return a non-null user when called with userId
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
