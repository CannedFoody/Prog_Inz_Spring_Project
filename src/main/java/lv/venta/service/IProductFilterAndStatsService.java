package lv.venta.service;

import java.util.ArrayList;

import lv.venta.model.Category;
import lv.venta.model.Product;

public interface IProductFilterAndStatsService {

    //filtret produktus pec cenas, kas mazaka par ienākošo mainīgā vērtību
    public abstract ArrayList<Product> filterByPriceLessThan(float threshold) throws Exception;

    //filtrēt produktus pēc tipa
    public abstract ArrayList<Product> filterByCategory(Category category) throws Exception;

    //filtrēt produktus pēc atslēgas vārda, kas varētu būt title vai arī description
    public abstract ArrayList<Product> filterByKeyword(String keyword) throws Exception;

    //aprēķinat videjo vērtību produktu cenām
    public abstract float calculateAVGPrice() throws Exception;
}