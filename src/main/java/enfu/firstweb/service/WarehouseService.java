package enfu.firstweb.service;

import enfu.firstweb.entity.Product;
import enfu.firstweb.entity.Store;
import enfu.firstweb.entity.Warehouse;
import enfu.firstweb.repository.ProductRepository;
import enfu.firstweb.repository.StoreRepository;
import enfu.firstweb.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> getWarehouseById(Long id) {
        return warehouseRepository.findById(id);
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    public void assignProductsToStore(Long warehouseId, Long storeId, List<Long> productIds) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        List<Product> products = productRepository.findAllById(productIds);

        for (Product product : products) {
            if (product.getWarehouse().equals(warehouse)) {
                store.getProducts().add(product);
            } else {
                throw new RuntimeException("Product does not belong to the warehouse");
            }
        }

        storeRepository.save(store);
    }

    public Optional<Warehouse> getWarehouseByName(String name) {
        return warehouseRepository.findByName(name);
    }

    @Transactional
    public void deleteAllWarehouses() {
        warehouseRepository.deleteAll();
    }
}
