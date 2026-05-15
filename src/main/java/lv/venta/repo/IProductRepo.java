package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Product;

public interface IProductRepo extends CrudRepository<Product, Integer>{

	//te automatiski izveidosies vaicājums: SELECT * FROM PRODUCT_TABLE WHERE TITLE=title, bet atgriezīs true, ja būs kādi dati
	public abstract boolean existsByTitle(String title);

}
