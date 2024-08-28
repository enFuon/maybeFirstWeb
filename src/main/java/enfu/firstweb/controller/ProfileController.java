package enfu.firstweb.controller;

import enfu.firstweb.entity.User;
import enfu.firstweb.entity.Cart;
import enfu.firstweb.service.UserService;
import enfu.firstweb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/profile")
    public String getUserProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Pobieranie informacji o użytkowniku na podstawie szczegółów logowania
        User user = userService.findUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);

        // Pobieranie koszyka użytkownika
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);

        return "user/profile/profile"; // Zaktualizowana ścieżka do pliku profile.html w katalogu templates/profile
    }
}
