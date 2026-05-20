package lv.venta.repo;

import java.util.ArrayList;

import lv.venta.model.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Product;

public interface IProductRepo extends CrudRepository<Product, Integer>{

	//te automatiski izveidosies vaicājums: SELECT * FROM PRODUCT_TABLE WHERE TITLE=title, bet atgriezīs true, ja būs kādi dati
	public abstract boolean existsByTitle(String title);

	public abstract ArrayList<Product> findByCategory(Category category);

	ArrayList<Product> findByPriceLessThan(float threshold);


	ArrayList<Product> findByTitleContainingOrDescriptionContaining(String keyword, String keyword1);

	@Query(nativeQuery = true, value="SELECT AVG(price) FROM product_table")
	public abstract float count_average_database();
}
