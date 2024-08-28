package enfu.firstweb.controller;

import enfu.firstweb.entity.Product;
import enfu.firstweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String productList(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; // Nazwa szablonu Thymeleaf do wyświetlenia listy produktów
    }
    
}
