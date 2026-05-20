package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.model.Product;
import lv.venta.service.ICRUDProductService;

@Controller
@RequestMapping("/product/crud")
public class ProductCRUDController {

    @Autowired
    private ICRUDProductService prodService;

    @GetMapping("/all")//localhost:8080/product/crud/all
    public String getControllerRetrieveAllProducts(Model model) {
        try
        {
            ArrayList<Product> productsFromDB = prodService.retrieveAllProducts();
            model.addAttribute("package", productsFromDB);
            return "show-all-products";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/all/{id}")//localhost:8080/product/crud/all/2
    public String getControllerRetrieveProductById(
            @PathVariable(name = "id") int id, Model model) {
        try
        {
            Product productFromDB = prodService.retrieveProductById(id);
            model.addAttribute("package", productFromDB);
            return "show-one-product";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }


    @GetMapping("/add")//localhost:8080/product/crud/add
    public String getControllerForProductAdding(Model model) {
        model.addAttribute("product", new Product());
        return "add-one-product";
    }


    @PostMapping("/add")///product/crud/add
    public String postControllerForProductAdding(Product product, Model model) {
        try
        {
            prodService.createNewProduct(product);
            return "redirect:/product/crud/all";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/update/{id}")//localhost:8080/product/crud/update/2
    public String getControllerForUpdateById(@PathVariable(name = "id") int id,Model model) {

        try
        {
            Product productFromDB = prodService.retrieveProductById(id);
            model.addAttribute("product", productFromDB);
            return "update-one-product";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }

    }



    @PostMapping("/update/{id}")
    public String postControllerForUpdateById(@PathVariable(name = "id") int id, Product product,
                                              Model model) {
        try
        {
            prodService.updateProductById(id, product.getPrice(), product.getCategory(),
                    product.getDescription(), product.getQuantity());
            return "redirect:/product/crud/all/" + id; //vai arī uz /product/crud/all
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/delete/{id}")
    public String getControllerForDeletion(@PathVariable(name = "id") int id, Model model) {
        try
        {
            prodService.deleteProductById(id);
            ArrayList<Product> productsFromDB = prodService.retrieveAllProducts();
            model.addAttribute("package", productsFromDB);
            return "show-all-products";
        }
        catch (Exception e) {
            model.addAttribute("package", e.getMessage());
            return "error-page";
        }
    }


}