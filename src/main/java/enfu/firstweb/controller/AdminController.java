package enfu.firstweb.controller;

import enfu.firstweb.entity.Store;
import enfu.firstweb.entity.Product;
import enfu.firstweb.service.StoreService;
import enfu.firstweb.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final StoreService storeService;
    private final ProductService productService;

    public AdminController(StoreService storeService, ProductService productService) {
        this.storeService = storeService;
        this.productService = productService;
    }

    @GetMapping("/admin/admin-panel")
    public String adminPanel(Model model) {
        // Pobieranie aktualnie zalogowanego użytkownika i sprawdzanie jego roli
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            // Jeżeli użytkownik ma rolę ADMIN, pobieramy dane sklepów i produktów
            List<Store> stores = storeService.findAllStores();
            List<Product> products = productService.getAllProducts();

            model.addAttribute("stores", stores);
            model.addAttribute("products", products);

            return "admin/admin-panel"; // Wyświetlanie strony admin-panel.html
        }
        return "redirect:/access-denied"; // Przekierowanie na stronę z informacją o braku uprawnień
    }
}
