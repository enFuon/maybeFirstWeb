package enfu.firstweb.controller;

import enfu.firstweb.service.ProductService;
import enfu.firstweb.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sound.midi.SysexMessage;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        // Sprawdź, czy użytkownik jest zalogowany
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            // Sprawdź, czy użytkownik jest administratorem
            if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/admin-panel"; // Przekierowanie do panelu administracyjnego
            }
            // Sprawdź, czy użytkownik jest zwykłym użytkownikiem
            else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
                model.addAttribute("products", productService.getAllProducts());
                return "index"; // Strona główna dla zalogowanych użytkowników z listą produktów
            }
        }

        // Jeśli użytkownik nie jest zalogowany, wyświetl standardową stronę główną z listą produktów
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
}