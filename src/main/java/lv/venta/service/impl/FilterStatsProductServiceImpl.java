package lv.venta.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;


import lv.venta.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Product;
import lv.venta.repo.IProductRepo;
import lv.venta.service.IProductFilterAndStatsService;

@Service
public class FilterStatsProductServiceImpl implements IProductFilterAndStatsService {

    private final CRUDProductServiceImpl CRUDProductServiceImpl;

    @Autowired
    private IProductRepo prodRepo;

    FilterStatsProductServiceImpl(CRUDProductServiceImpl CRUDProductServiceImpl) {
        this.CRUDProductServiceImpl = CRUDProductServiceImpl;
    }

    @Override
    public ArrayList<Product> filterByPriceLessThan(float threshold) throws Exception {
        if (threshold <= 0){
            throw new Exception("Threshold cannot be less than or equal to 0...");
        }

        if(prodRepo.count() == 0) {
            throw new Exception("Database has no products...");
        }

        ArrayList<Product> filtered_products = prodRepo.findByPriceLessThan(threshold);

        if(filtered_products.isEmpty()){
            throw new Exception("No products found with the input threshold...");
        }

        return filtered_products;
    }

    @Override
    public ArrayList<Product> filterByCategory(Category category) throws Exception {
        if(category == null) {
            throw new Exception("Ievades dati nav korekti");
        }

        if(prodRepo.count() == 0) {
            throw new Exception("DB nav produktu, tāpēc neko nevar filtrēt");
        }

        ArrayList<Product> filteredProducts = prodRepo.findByCategory(category);

        if(filteredProducts.isEmpty()) {
            throw new Exception("Nav neviens produkts " + category + " kategorijā");
        }
        return filteredProducts;
    }

    @Override
    public ArrayList<Product> filterByKeyword(String keyword) throws Exception {
        if (keyword == null){
            throw new Exception("Cannot filter by null keyword...");
        }

        if(prodRepo.count() == 0) {
            throw new Exception("DB nav produktu, tāpēc neko nevar filtrēt");
        }

        ArrayList<Product> filtered_products = prodRepo.findByTitleContainingOrDescriptionContaining(keyword, keyword);

        if(filtered_products.isEmpty()){
            throw new Exception("No rows were found with given keyword...");
        }

        return filtered_products;
    }

    @Override
    public float calculateAVGPrice() throws Exception {
        if(prodRepo.count() == 0) {
            throw new Exception("DB nav produktu, tāpēc neko nevar aprēķināt");
        }
        float avg_price = prodRepo.count_average_database();

        return avg_price;
    }

}