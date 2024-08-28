package enfu.firstweb.controller;

import enfu.firstweb.entity.Store;
import enfu.firstweb.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/add")
    public String createStoreForm(Model model) {
        model.addAttribute("store", new Store());
        return "admin/stores/add";
    }

    @PostMapping("/add")
    public String saveStore(@ModelAttribute Store store) {
        storeService.saveStore(store);
        return "redirect:/admin/stores/list";
    }

    @GetMapping("/edit/{id}")
    public String editStoreForm(@PathVariable("id") Long id, Model model) {
        Store store = storeService.findStoreById(id);
        model.addAttribute("store", store);
        return "admin/stores/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateStore(@PathVariable("id") Long id, @ModelAttribute Store store) {
        store.setId(id);
        storeService.saveStore(store);
        return "redirect:/admin/stores/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable("id") Long id) {
        storeService.deleteStore(id);
        return "redirect:/admin/stores/list";
    }

    @GetMapping("/list")
    public String listStores(Model model) {
        List<Store> stores = storeService.findAllStores();
        model.addAttribute("stores", stores);
        return "admin/stores/list";
    }
}
