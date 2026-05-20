package lv.venta.controller;

import java.lang.reflect.Array;

import lv.venta.model.Category;
import lv.venta.model.Product;
import lv.venta.repo.IProductRepo;
import lv.venta.service.IProductFilterAndStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/product/filter")
public class ProductFilterAndStatsController {
    private final IProductRepo iProductRepo;

    public ProductFilterAndStatsController(IProductRepo iProductRepo) {
        this.iProductRepo = iProductRepo;
    }

    @Autowired
    private IProductFilterAndStatsService service;

    @GetMapping("/price/{threshold}")
    public String get_controller_filter_by_price(@PathVariable(name = "threshold") float threshold, Model model){
        try{
            ArrayList<Product> filtered_products = service.filterByPriceLessThan(threshold);

            model.addAttribute("package", filtered_products);
            return "show-all-products";

        }
        catch(Exception e){
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/category/{category}")
    public String get_controller_filter_by_category(@PathVariable(name = "category") Category category, Model model){
        try{
            ArrayList<Product> filtered_product = service.filterByCategory(category);
            model.addAttribute("package", filtered_product);
            return "show-all-products";
        }
        catch(Exception e){
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/keyword/{keyword}")
    public String get_controller_filter_by_keyword(@PathVariable(name = "keyword") String keyword, Model model){
        try{
            ArrayList<Product> filtered_product = service.filterByKeyword(keyword);
            model.addAttribute("package", filtered_product);
            return "show-all-products";
        }
        catch(Exception e){
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/price/avg")
    public String get_controller_average_price(Model model){
        try{
            float average_price = service.calculateAVGPrice();
            model.addAttribute("package", average_price + "€");
            return "data";
        }
        catch(Exception e){
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }
}
