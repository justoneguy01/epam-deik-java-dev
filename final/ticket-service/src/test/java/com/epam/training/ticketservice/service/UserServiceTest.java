package com.epam.training.ticketservice.service;
import com.epam.training.ticketservice.model.dto.UserDto;
import com.epam.training.ticketservice.model.entity.User;
import com.epam.training.ticketservice.model.repository.UserRepository;
import com.epam.training.ticketservice.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceImpl underTest = new UserServiceImpl(userRepository);
    User ADMIN = new User("admin","admin", User.Role.ADMIN);
    UserDto AdminDto = new UserDto(ADMIN);
    @Test
    void testSignInPrivilegedWithCorrectCredentialsShouldReturnSuccessMessage() {

        //Given
        given(userRepository.findByUsername(ADMIN.getUsername())).willReturn(Optional.of(ADMIN));

        //When
        when(userRepository.findByUsername(ADMIN.getUsername())).thenReturn(Optional.of(ADMIN));
        String actual = underTest.signInPrivileged(ADMIN.getUsername(), ADMIN.getPassword());

        //Then
        assertEquals("Signed in", actual);
        assertEquals(AdminDto.getUsername(),underTest.getLoggedUser().getUsername());

    }

    @Test
    void testSignInPrivilegedWithIncorrectCredentialsShouldReturnFailureMessage() {

        //Given
        given(userRepository.findByUsername(ADMIN.getUsername())).willReturn(Optional.of(ADMIN));

        //When
        when(userRepository.findByUsername(ADMIN.getUsername())).thenReturn(Optional.of(ADMIN));
        String actual = underTest.signInPrivileged("Bob", "WrongPassword");

        //Then
        assertEquals("Login failed due to incorrect credentials", actual);
        assertNull(underTest.getLoggedUser());

    }

    @Test
    void testSignOutWhenLoggedIn() {

        //Given
        given(userRepository.findByUsername(ADMIN.getUsername())).willReturn(Optional.of(ADMIN));

        //When
        when(userRepository.findByUsername(ADMIN.getUsername())).thenReturn(Optional.of(ADMIN));
        underTest.signInPrivileged(ADMIN.getUsername(), ADMIN.getPassword());
        String actual = underTest.signOut();

        //Then
        assertEquals("Signed out", actual);
        assertNull(underTest.getLoggedUser());

    }

    @Test
    void testSignOutWhenLoggedOut() {

        //Given
        given(userRepository.findByUsername(ADMIN.getUsername())).willReturn(Optional.of(ADMIN));

        //When
        when(userRepository.findByUsername(ADMIN.getUsername())).thenReturn(Optional.of(ADMIN));
        String actual = underTest.signOut();

        //Then
        assertEquals("You are not signed in", actual);
        assertNull(underTest.getLoggedUser());

    }
    @Test
    void testDescribeWhenLoggedInShouldReturnSignedInMessage() {

        //Given
        given(userRepository.findByUsername(ADMIN.getUsername())).willReturn(Optional.of(ADMIN));

        //When
        when(userRepository.findByUsername(ADMIN.getUsername())).thenReturn(Optional.of(ADMIN));
        underTest.signInPrivileged(ADMIN.getUsername(), ADMIN.getPassword());
        String result = underTest.describe();

        //Then
        assertEquals("Signed in with privileged account 'admin'", result);
    }

    @Test
    void testDescribeWhenNotLoggedInShouldReturnNotSignedInMessage() {

        //Given

        //When

        String result = underTest.describe();

        //Then
        assertEquals("You are not signed in", result);

    }

}