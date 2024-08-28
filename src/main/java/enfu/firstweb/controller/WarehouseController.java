package enfu.firstweb.controller;

import enfu.firstweb.entity.Warehouse;
import enfu.firstweb.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    // Wyświetlanie formularza dodawania nowego magazynu
    @GetMapping("/add")
    public String addWarehouseForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        return "admin/warehouses/add";
    }

    // Zapis nowego magazynu
    @PostMapping("/add")
    public String saveWarehouse(@ModelAttribute Warehouse warehouse) {
        warehouseService.saveWarehouse(warehouse);
        return "redirect:/admin/warehouses/list";
    }

    // Wyświetlanie listy magazynów
    @GetMapping("/list")
    public String listWarehouses(Model model) {
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());
        return "admin/warehouses/list";
    }

    @GetMapping("/edit/{id}")
    public String editWarehouseForm(@PathVariable("id") Long id, Model model) {
        Warehouse warehouse = warehouseService.getWarehouseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid warehouse id: " + id));
        model.addAttribute("warehouse", warehouse);
        return "admin/warehouses/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateWarehouse(@PathVariable("id") Long id, @ModelAttribute Warehouse warehouse) {
        warehouse.setId(id);
        warehouseService.saveWarehouse(warehouse);
        return "redirect:/admin/warehouses/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteWarehouse(@PathVariable("id") Long id) {
        warehouseService.deleteWarehouse(id);
        return "redirect:/admin/warehouses/list";
    }

}
