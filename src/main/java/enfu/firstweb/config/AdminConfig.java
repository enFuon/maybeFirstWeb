package enfu.firstweb.config;

import enfu.firstweb.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {

    @Bean
    public CommandLineRunner initAdminAccounts(UserService userService) {
        return args -> {
            userService.deleteAllUsers();

            userService.createAdminAccount("Admin1", "admin1@example.com", "password1");
            userService.createAdminAccount("Admin2", "admin2@example.com", "password2");

            userService.createUserAccount("User1", "user1@example.com", "password1");
            userService.createUserAccount("User2", "user2@example.com", "password2");
        };
    }
}