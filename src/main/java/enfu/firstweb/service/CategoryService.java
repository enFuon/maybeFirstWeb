package enfu.firstweb.service;

import enfu.firstweb.entity.Category;
import enfu.firstweb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }


    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category findById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Transactional
    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

}
