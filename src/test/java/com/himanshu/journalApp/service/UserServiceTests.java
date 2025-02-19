package com.himanshu.journalApp.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
// import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.repository.query.Param;

import com.himanshu.journalApp.entity.User;
import com.himanshu.journalApp.repository.UserRepository;

// import net.bytebuddy.asm.MemberSubstitution.Argument;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    public void testFindByUsername() {
        assertNotNull(userRepository.findByUsername("Harsh"));
    }

    @Disabled
    @ParameterizedTest
    // @CsvSource({
    //     "Harsh",
    //     "Himanshu",
    //     "Rahul"
    // })
    @ValueSource(strings = {"Harsh", "Himanshu", "Rahul"})
    public void testFindByUsername(String username) {
        assertNotNull(userRepository.findByUsername(username));
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveUser(User user) {
        assertNotNull(userService.saveUser(user));
    }
}
