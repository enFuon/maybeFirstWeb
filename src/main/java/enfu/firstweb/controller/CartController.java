package enfu.firstweb.controller;

import enfu.firstweb.entity.Product;
import enfu.firstweb.entity.User;
import enfu.firstweb.service.CartService;
import enfu.firstweb.service.ProductService;
import enfu.firstweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, ProductService productService, UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public String addToCart(@RequestParam Long productId) {
        // Pobierz aktualnie zalogowanego użytkownika
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User currentUser = userService.findUserByEmail(currentUserEmail);

        if (currentUser != null) {
            // Pobierz produkt jako Optional
            Optional<Product> productOptional = productService.getProductById(productId);

            if (productOptional.isPresent()) {
                // Jeśli produkt istnieje, dodaj go do koszyka
                Product product = productOptional.get();
                cartService.addProductToCart(currentUser, product);
            } else {
                // Obsłuż przypadek, gdy produkt nie istnieje
                System.out.println("Product with ID " + productId + " does not exist.");
                // Można dodać kod do wyświetlenia komunikatu o błędzie lub przekierowania na inną stronę
            }
        }

        return "redirect:/"; // Przekieruj na stronę główną lub stronę koszyka
    }
}
