package enfu.firstweb.service;

import enfu.firstweb.entity.Category; // Dodany import
import enfu.firstweb.entity.Product;
import enfu.firstweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;



    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Metoda do przypisywania kategorii
    public Product assignCategoryToProduct(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId).orElseThrow();
        Category category = categoryService.getCategoryById(categoryId).orElseThrow();
        product.setCategory(category);
        return productRepository.save(product);

    }
    @Transactional
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }


}
