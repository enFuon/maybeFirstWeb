package enfu.firstweb.controller;

import enfu.firstweb.entity.Category;
import enfu.firstweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/add";
    }

    @PostMapping("/add")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/categories/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/categories/edit";  // Zwraca ścieżkę do pliku edit.html w templates/admin/categories/
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Long id, @ModelAttribute Category category) {
        category.setId(id);  // Ustaw id kategorii, aby upewnić się, że aktualizujesz istniejącą kategorię
        categoryService.saveCategory(category); // Zapisuje zmiany
        return "redirect:/admin/categories/list"; // Przekierowanie do listy kategorii
    }

    @GetMapping("/list")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories/list"; // Przekierowanie do listy kategorii po usunięciu
    }
}
