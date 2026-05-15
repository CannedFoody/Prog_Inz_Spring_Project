package lv.venta.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lv.venta.model.Category;
import lv.venta.model.Product;
//kontrolieru klase ar endpointiem
@Controller
public class SimpleController {
	
	
	ArrayList<Product> allProducts = new ArrayList<>(Arrays.asList(
			new Product("Ābols", 0.99f, "Salds un garšīgs", Category.fruit, 10),
			new Product("Burkāns", 2.84f, "Oranžš", Category.vegetable, 40),
			new Product("Vīnogas", 4.99f, "Violetas", Category.fruit, 3)
			)
			);
	
	
	@GetMapping("/showmessage") //localhost:8080/showmessage
	public String getShowMessage() {
		System.out.println("Mans pirmais kontrolieris ir izsaukts");
		return "msg";//šī koda rinda parādāsi msg.html lapu
		
	}
	
	@GetMapping("/showdata")//localhost:8080/showdata
	public String getShowData(Model model) {
		System.out.println("Otrais kontrolieris ir izsaukts");
		Random rand = new Random();
		String text = "Karina " + rand.nextInt();
		model.addAttribute("package", text);
		return "data";//sī koda rinda parādīs data.html lapu
	}
	
	
	@GetMapping("/getproduct")//localhost:8080/getproduct
	private String getGetProduct(Model model) {
		Product prod = new Product("Ābols", 0.99f, "Salds un garšīgs", Category.fruit, 10);
		model.addAttribute("package", prod);
		return "show-one-product";//sī koda rinda parādīs show-one-product.html lapu
		
	}
	
	
	@GetMapping("/getallproducts")//localhost:8080/getallproducts
	public String getGetAllProducts(Model model) {
		model.addAttribute("package",allProducts);
		return "show-all-products";//sī koda rinda parādīs show-all-products.html lapu
	}
	
	@GetMapping("/getallproducts/{id}")//localhost:8080/getallproducts/1
	public String getGetAllProductsById(@PathVariable(name = "id") int id, Model model) {
		if(id < 0) {
			model.addAttribute("package", "Produkta id nevar būt negatīvs");
			return "error-page";//šī koda rinda parādīs error-page.html lapu ar ziņū, ka id nevar būt negatīvs
		}
		
		for(Product tempP : allProducts) {
			if(tempP.getId()==id) {
				model.addAttribute("package", tempP);
				return "show-one-product";//sī koda rinda parādīs show-one-product.html lapu
			}
		}
		
		model.addAttribute("package", "Produkts ar tādu id neeksistē");
		return "error-page";
		
		
	}
	
	@GetMapping("/allproducts")//localhost:8080/allproducts?id=1
	public String getGetAllProducts2ById(@RequestParam(name = "id") int id, Model model)
	{
		if(id < 0) {
			model.addAttribute("package", "Produkta id nevar būt negatīvs");
			return "error-page";//šī koda rinda parādīs error-page.html lapu ar ziņū, ka id nevar būt negatīvs
		}
		
		for(Product tempP : allProducts) {
			if(tempP.getId()==id) {
				model.addAttribute("package", tempP);
				return "show-one-product";//sī koda rinda parādīs show-one-product.html lapu
			}
		}
		
		model.addAttribute("package", "Produkts ar tādu id neeksistē");
		return "error-page";
		
		
	}
	
	//iesākumā parāda lapu, kurā varēs ievadīt jaunu produktu. Līdzi lapai padodam tukšu produktu
	@GetMapping("/add") //localhost:8080/add
	public String getAddProduct(Model model) {
		model.addAttribute("product", new Product());
		return "add-one-product";
	}
	
	
	
	//pēc submit pogas nospiešanas html pusē, saņemam jau aizpildītu produktu
	@PostMapping("/add")
	public String postAddProduct(Product product) {
		//TODO veikt validācijas un uzstādīt id
		System.out.println(product);
		
		Product newProduct = new Product(product.getTitle(), product.getPrice(), product.getDescription(),
				product.getCategory(), product.getQuantity());
		
		allProducts.add(newProduct);
		return "redirect:/getallproducts";
	}
	
	
	
	//izveidot getmapping funkciju uz /update/id
	@GetMapping("/update/{id}")//localhost:8080/update/1
	// funkcijas deklarācija ar @PathVariable un Model
	public String getUpdateProductById(@PathVariable(name = "id") int id, Model model)
	{
		// parliecinaties, ka id ir pozitīvs, ja nav, tad uz error lapu parmest
		if(id < 0) {
			model.addAttribute("package", "Produkta id nevar būt negatīvs");
			return "error-page";//šī koda rinda parādīs error-page.html lapu ar ziņū, ka id nevar būt negatīvs
		}
		
		for(Product tempP : allProducts) {
			if(tempP.getId() == id) {
				model.addAttribute("product", tempP);
				return "update-one-product";
			}
		}
		
		model.addAttribute("package", "Produkts ar tādu id neeksistē");
		return "error-page";
		
	}
	
	//dabūsu jau redigēto produktu šajā funkcijā kā argumentu
	@PostMapping("/update/{id}")
	public String postUpdateProductById(@PathVariable(name = "id") int id, Product product) {
		
		for(Product tempP : allProducts) {
			if(tempP.getId() == id) {
				//TODO pārbaudīt, vai vispār ir jāmaina
				tempP.setTitle(product.getTitle());
				tempP.setDescription(product.getDescription());
				tempP.setPrice(product.getPrice());
				tempP.setCategory(product.getCategory());
				tempP.setQuantity(product.getQuantity());
			}
		}
		return "redirect:/getallproducts";
	}
	

	@GetMapping("/remove/{id}")//localhost:8080/remove/1
	public String getRemoveProductById(@PathVariable(name = "id") int id, Model model) {
		// parliecinaties, ka id ir pozitīvs, ja nav, tad uz error lapu parmest
		if(id < 0) {
			model.addAttribute("package", "Produkta id nevar būt negatīvs");
			return "error-page";//šī koda rinda parādīs error-page.html lapu ar ziņū, ka id nevar būt negatīvs
		}
		
		for(Product tempP : allProducts) {
			if(tempP.getId() == id) {
				allProducts.remove(tempP);
				model.addAttribute("package", allProducts);
				return "show-all-products";//parada lapu show-all-products.html ar visiem produktiem un izdzesatajam produktam tur neveajadzetu but
			}
		}
		
		model.addAttribute("package", "Produkts ar tādu id neeksistē");
		return "error-page";
		
	}
	
	//TODO
	//izveidot get mapping funkcijas deklarāciju ar padotu id
	//parbaudit, ja id ir negatvis, tad paradit error lapu
	//atrodam koknreto produktu saraksta un to izdzesam un redirektejam uz all products
	//ja tomer produkt pec ide netiek atrasts, tad paradam error lapu
	
	
	
	
	
	
	
	
	

}
