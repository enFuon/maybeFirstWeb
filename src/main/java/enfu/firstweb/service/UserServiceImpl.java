package enfu.firstweb.service;

import enfu.firstweb.dto.UserDto;
import enfu.firstweb.entity.Role;
import enfu.firstweb.entity.User;
import enfu.firstweb.repository.RoleRepository;
import enfu.firstweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        if (user != null) {
            // Aktualizuj istniejącego użytkownika
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            userRepository.save(user);
        } else {
            // Dodaj nowego użytkownika
            user = new User();
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            // Przypisz rolę "USER"
            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
            }

            List<Role> roles = new ArrayList<>();
            roles.add(userRole);
            user.setRoles(roles);

            userRepository.save(user);
        }
    }



    @Override
    public User findUserByEmail(String email) {
        System.out.println("Searching for user with email: " + email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str.length > 1 ? str[1] : "");
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public void createAdminAccount(String username, String email, String password) {
        // Sprawdź, czy rola "ROLE_ADMIN" już istnieje w bazie danych
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        User admin = new User();
        admin.setName(username);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRoles(Collections.singletonList(adminRole)); // Użyj istniejącej lub nowo zapisanej roli
        userRepository.save(admin);
    }

    public void createUserAccount(String username, String email, String password) {
        // Sprawdź, czy rola "ROLE_USER" już istnieje w bazie danych
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singletonList(userRole)); // Użyj istniejącej lub nowo zapisanej roli
        userRepository.save(user);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }


}
