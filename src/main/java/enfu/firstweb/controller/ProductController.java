package enfu.firstweb.controller;

import enfu.firstweb.entity.Product;
import enfu.firstweb.service.ProductService;
import enfu.firstweb.service.CategoryService;
import enfu.firstweb.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());  // Lista kategorii
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());  // Lista magazynów
        return "admin/products/add";
    }

    @PostMapping("/add")
    public String saveProduct(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("price") Double price,
                              @RequestParam("quantityInStock") Integer quantityInStock,
                              @RequestParam("categoryId") Long categoryId,
                              @RequestParam("warehouseId") Long warehouseId) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQuantityInStock(quantityInStock);
        product.setCategory(categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + categoryId)));
        product.setWarehouse(warehouseService.getWarehouseById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid warehouse id: " + warehouseId)));

        // Zapis produktu
        productService.saveProduct(product);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());  // Dodajemy listę kategorii
        model.addAttribute("warehouses", warehouseService.getAllWarehouses()); // Dodajemy listę magazynów

        return "admin/products/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @ModelAttribute Product product,
                                @RequestParam("category.id") Long categoryId,
                                @RequestParam("warehouse.id") Long warehouseId) {
        // Ustawienie ID produktu
        product.setId(id);

        // Ustawienie kategorii
        product.setCategory(categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + categoryId)));

        // Ustawienie magazynu
        product.setWarehouse(warehouseService.getWarehouseById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid warehouse id: " + warehouseId)));

        // Zapis produktu
        productService.saveProduct(product);

        return "redirect:/admin/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products/list";  // Ścieżka do listy produktów
    }


}
