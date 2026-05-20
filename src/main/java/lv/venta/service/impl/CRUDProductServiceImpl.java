package lv.venta.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.model.Category;
import lv.venta.model.Product;
import lv.venta.repo.IProductRepo;
import lv.venta.service.ICRUDProductService;

@Service
public class CRUDProductServiceImpl implements ICRUDProductService {

	@Autowired
	private IProductRepo prodRepo;

	@Override
	public Product createNewProduct(Product newProduct) throws Exception {
		if(newProduct == null) {
			throw new Exception("Produkta dati nav piejami, jo nav reference");
		}

		if(prodRepo.existsByTitle(newProduct.getTitle())) {
			throw new Exception("Produkts ar tādu nosaukumu jau eksistē");
		}

		if(newProduct.getTitle() == null || newProduct.getTitle().isEmpty()
				|| newProduct.getCategory() == null || newProduct.getPrice() < 0
				|| newProduct.getPrice() > 100000 || newProduct.getDescription() == null
				|| newProduct.getQuantity() < 0 || newProduct.getQuantity() > 100000) {
			throw new Exception("Nav korekti ievades dati");
		}


		return prodRepo.save(newProduct);
	}

	@Override
	public ArrayList<Product> retrieveAllProducts() throws Exception {
		if(prodRepo.count() == 0) {
			throw new Exception("Produktu tabula ir tukša");
		}

		return (ArrayList<Product>) prodRepo.findAll();

	}

	@Override
	public Product retrieveProductById(int id) throws Exception {
		if(id < 1) {
			throw new Exception("Id nevar būt negatīvs");
		}

		if(!prodRepo.existsById(id)) {//pārbaudam, vai neeksistē
			throw new Exception("Produkts ar id " + id + " neeksistē");
		}

		return prodRepo.findById(id).get();
	}

	@Override
	public Product updateProductById(int id, float price, Category category, String description, int quantity)
			throws Exception {
		Product productForUpdating = retrieveProductById(id);

		if(category == null || price < 0
				|| price > 100000 || description == null
				|| quantity < 0 || quantity > 100000) {
			throw new Exception("Nav korekti ievades dati");
		}


		if(productForUpdating.getPrice() != price) {
			productForUpdating.setPrice(price);
		}

		if(!productForUpdating.getCategory().equals(category))
		{
			productForUpdating.setCategory(category);
		}
		if(!productForUpdating.getDescription().equals(description))
		{
			productForUpdating.setDescription(description);
		}
		if(productForUpdating.getQuantity() != quantity)
		{
			productForUpdating.setQuantity(quantity);
		}

		return prodRepo.save(productForUpdating);
	}

	@Override
	public void deleteProductById(int id) throws Exception {
		Product productForDeleting = retrieveProductById(id);
		prodRepo.delete(productForDeleting);
	}

}