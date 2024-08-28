package enfu.firstweb.controller;

import enfu.firstweb.dto.UserDto;
import enfu.firstweb.entity.User;
import enfu.firstweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping("/edit/{email}")
    public String editUserForm(@PathVariable("email") String email, Model model) {
        User user = userService.findUserByEmail(email);

        // Konwersja User na UserDto
        UserDto userDto = mapToUserDto(user);

        model.addAttribute("user", userDto);
        return "admin/users/edit";
    }

    @PostMapping("/edit/{email}")
    public String updateUser(@PathVariable("email") String email, @ModelAttribute("user") UserDto userDto) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            user.setName(userDto.getFirstName() + " " + userDto.getLastName());
            user.setEmail(userDto.getEmail());

            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }

            // Konwertowanie User na UserDto
            UserDto updatedUserDto = mapToUserDto(user);
            userService.saveUser(updatedUserDto);
        }
        return "redirect:/admin/users/list";
    }


    @GetMapping("/delete/{email}")
    public String deleteUser(@PathVariable("email") String email) {
        userService.deleteUserByEmail(email);
        return "redirect:/admin/users/list";
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        String[] nameParts = user.getName().split(" ");
        userDto.setFirstName(nameParts.length > 0 ? nameParts[0] : "");
        userDto.setLastName(nameParts.length > 1 ? nameParts[1] : "");
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}