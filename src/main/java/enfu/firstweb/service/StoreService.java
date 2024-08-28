package enfu.firstweb.service;

import enfu.firstweb.entity.Store;
import java.util.List;

public interface StoreService {
    Store saveStore(Store store);
    Store findStoreById(Long id);
    List<Store> findAllStores();
    void deleteStore(Long id);
}
