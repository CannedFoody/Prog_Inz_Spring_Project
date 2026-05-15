package lv.venta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "ProductTable")
@Entity
public class Product {
	
	@Column(name = "Id")
	@Id //šī kolonna būs kā PK
	@GeneratedValue(strategy = GenerationType.AUTO)//auto increment datubāzē izveidos nunmuru pec kartas un unikalu id
	private int id;
	
	@Column(name = "Price")
	private float price;
	
	@Column(name = "Title", unique = true)
	private String title;
	
	@Column(name = "Category")
	private Category category;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Quantity")
	private int quantity;
	

	public int getId() {
		return id;
	}

	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	
	//TODO Izmantot validāciju anotācijas
	public void setTitle(String title) {
		this.title = title;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Product()
	{
		/*setId();
		setTitle("Galds");
		setPrice(78.99f);
		setDescription("Brūns un liels");
		setCategory(Category.other);
		setQuantity(4);*/
	}
	
	public Product(String inputTitle, float inputPrice, String inputDescription, Category inputCategory,
			int inputQuantity) {
		setTitle(inputTitle);
		setPrice(inputPrice);
		setDescription(inputDescription);
		setCategory(inputCategory);
		setQuantity(inputQuantity);
	}
	
	public String toString() {
		return id + ": " + title + ", " + price + " EUR, " + description + ", " + category + ", " + quantity; 
	}
	
	
	
	
	
	
	
	
	
	
}
