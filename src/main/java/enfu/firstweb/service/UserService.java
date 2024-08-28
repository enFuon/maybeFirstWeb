package enfu.firstweb.service;
import enfu.firstweb.dto.UserDto;
import enfu.firstweb.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

    void deleteUserByEmail(String email);


    void createAdminAccount(String name, String email, String password);  // Dodaj tę metodę

    void createUserAccount(String name, String email, String password);

    void deleteAllUsers();

}