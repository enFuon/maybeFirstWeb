package enfu.firstweb.config;

import enfu.firstweb.entity.Category;
import enfu.firstweb.entity.Warehouse;
import enfu.firstweb.entity.Product;
import enfu.firstweb.service.CartService;
import enfu.firstweb.service.CategoryService;
import enfu.firstweb.service.ProductService;
import enfu.firstweb.service.WarehouseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(CartService cartService, ProductService productService, CategoryService categoryService, WarehouseService warehouseService) {
        return args -> {
            // Czyścimy tabele
            cartService.deleteAllCartItems();
            cartService.deleteAllCarts();
            productService.deleteAllProducts();
            categoryService.deleteAllCategories();
            warehouseService.deleteAllWarehouses();

            // Tworzymy przykładowe kategorie
            Category electronics = new Category();
            electronics.setName("Electronics");
            categoryService.saveCategory(electronics);

            Category books = new Category();
            books.setName("Books");
            categoryService.saveCategory(books);

            // Tworzymy przykładowe magazyny
            Warehouse warehouse1 = new Warehouse();
            warehouse1.setName("Warehouse 1");
            warehouse1.setLocation("Location 1");
            warehouseService.saveWarehouse(warehouse1);

            Warehouse warehouse2 = new Warehouse();
            warehouse2.setName("Warehouse 2");
            warehouse2.setLocation("Location 2");
            warehouseService.saveWarehouse(warehouse2);

            // Tworzymy przykładowe produkty
            Product product1 = new Product();
            product1.setName("Laptop");
            product1.setDescription("A high-performance laptop.");
            product1.setPrice(999.99);
            product1.setQuantityInStock(10);
            product1.setCategory(categoryService.getCategoryByName("Electronics").get());
            product1.setWarehouse(warehouseService.getWarehouseByName("Warehouse 1").get());
            productService.saveProduct(product1);

            Product product2 = new Product();
            product2.setName("Book");
            product2.setDescription("A fascinating book.");
            product2.setPrice(19.99);
            product2.setQuantityInStock(100);
            product2.setCategory(categoryService.getCategoryByName("Books").get());
            product2.setWarehouse(warehouseService.getWarehouseByName("Warehouse 2").get());
            productService.saveProduct(product2);
        };
    }
}



/*package enfu.firstweb.config;

import enfu.firstweb.entity.Product;
import enfu.firstweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Random;

@Component
public class DataInitializer {

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void init() {
        generateProducts();
    }

    private void generateProducts() {
        String[] productNames = {
                "Smartphone", "Laptop", "Tablet", "Headphones", "Smartwatch",
                "Camera", "Monitor", "Keyboard", "Mouse", "Printer",
                "Television", "Router", "External Hard Drive", "USB Flash Drive", "Speakers",
                "Webcam", "Graphics Card", "Motherboard", "Processor", "Power Supply",
                "Memory Card", "VR Headset", "Drone", "3D Printer", "Fitness Tracker",
                "E-book Reader", "Portable Charger", "Gaming Console", "Smart Light Bulb", "Smart Plug",
                "Bluetooth Speaker", "Gaming Chair", "Projector", "Electric Scooter", "Robot Vacuum",
                "Smart Thermostat", "Smart Door Lock", "Smart Home Hub", "Wireless Earbuds", "Action Camera",
                "Security Camera", "Home Assistant Device", "Laptop Cooling Pad", "Portable Monitor", "Smart Scale",
                "Air Purifier", "Portable Blender", "Waterproof Speaker", "Noise Cancelling Headphones", "Smart Glasses",
                "Portable Projector", "Electric Toothbrush", "Digital Photo Frame", "Portable Power Station", "Bluetooth Tracker",
                "Wireless Charger", "Smart Doorbell", "Car Dash Camera", "Streaming Device", "Smartwatch Charger",
                "Tablet Case", "Smartphone Stand", "Laptop Sleeve", "Gaming Keyboard", "Gaming Mouse",
                "Wireless Adapter", "USB Hub", "Laptop Stand", "Monitor Arm", "Desk Lamp",
                "Wireless Headphones", "Bluetooth Adapter", "Smartphone Gimbal", "Phone Tripod", "Portable Speaker",
                "Gaming Headset", "Mechanical Keyboard", "Ergonomic Mouse", "Laptop Docking Station", "Smart TV Box",
                "Soundbar", "Portable SSD", "Smart Door Sensor", "Smart Smoke Detector", "Robot Lawn Mower",
                "Fitness Watch", "Digital Alarm Clock", "Portable Fan", "Portable Air Conditioner", "Smart Coffee Maker",
                "Smart Refrigerator", "Smart Oven", "Smart Washing Machine", "Smart Dishwasher", "Smart Microwave",
                "Smart Toaster", "Smart Iron", "Smart Blender", "Smart Vacuum Cleaner", "Smart Water Filter"
        };

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setName(productNames[i]);
            product.setPrice(BigDecimal.valueOf(random.nextInt(1000) + 50).doubleValue()); // Przekonwertowanie na Double
            product.setDescription("Description for " + productNames[i]);
            productService.saveProduct(product);
        }
    }
}
*/