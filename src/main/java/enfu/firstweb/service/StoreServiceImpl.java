package enfu.firstweb.service;

import enfu.firstweb.entity.Store;
import enfu.firstweb.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store findStoreById(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
