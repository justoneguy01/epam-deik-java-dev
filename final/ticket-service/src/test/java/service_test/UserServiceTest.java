package service_test;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceImpl underTest = new UserServiceImpl(userRepository);
    User ADMIN = new User("admin","admin", User.Role.ADMIN);
    @Test
    void signInPrivilegedWithCorrectCredentialsShouldReturnSuccessMessage() {
        //Given
        when(userRepository.findByUsernameAndPassword(ADMIN.getUsername(), ADMIN.getPassword())).thenReturn(Optional.of(ADMIN));
        //When
        String result = underTest.signInPrivileged(ADMIN.getUsername(), ADMIN.getPassword());
        //Then
        assertEquals("Login success", result);
        assertEquals(ADMIN, underTest.getLoggedInUser());
    }

    @Test
    void signInPrivilegedWithIncorrectCredentialsShouldReturnFailureMessage() {
        //Given
        when(userRepository.findByUsernameAndPassword(ADMIN.getUsername(), ADMIN.getPassword())).thenReturn(Optional.of(ADMIN));
        //When
        String result = underTest.signInPrivileged(ADMIN.getUsername(), "WrongPassword");
        //Then
        assertEquals("Login failed due to incorrect credentials", result);
        assertNull(underTest.getLoggedInUser());
    }

    @Test
    void signOutWhenLoggedInShouldReturnSignedOutMessage() {
        //Given
        when(userRepository.findByUsernameAndPassword(ADMIN.getUsername(), ADMIN.getPassword())).thenReturn(Optional.of(ADMIN));
        //When
        underTest.signInPrivileged(ADMIN.getUsername(), ADMIN.getPassword());
        //Then
        String result = underTest.signOut();
        assertEquals("Signed out", result);
        assertNull(underTest.getLoggedInUser());
    }

    @Test
    void signOutWhenNotLoggedInShouldReturnNotSignedInMessage() {
        //Given
        //When
        String result = underTest.signOut();
        //Then
        assertEquals("You are not signed in", result);
        assertNull(underTest.getLoggedInUser());
    }

    @Test
    void describeWhenLoggedInShouldReturnSignedInMessage() {
        //Given
        when(userRepository.findByUsernameAndPassword(ADMIN.getUsername(), ADMIN.getPassword())).thenReturn(Optional.of(ADMIN));
        //When
        underTest.signInPrivileged(ADMIN.getUsername(), ADMIN.getPassword());
        String result = underTest.describe();
        //Then
        assertEquals("Signed in with privileged account 'admin'", result);
    }

    @Test
    void describeWhenNotLoggedInShouldReturnNotSignedInMessage() {
        //Given
        //When
        String result = underTest.describe();
        //Then
        assertEquals("You are not signed in", result);
    }
}