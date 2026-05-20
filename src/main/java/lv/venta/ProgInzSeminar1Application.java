package lv.venta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import lv.venta.controller.SimpleController;
import lv.venta.model.Category;
import lv.venta.model.Product;
import lv.venta.repo.IProductRepo;

@SpringBootApplication
public class 	ProgInzSeminar1Application {

    private final SimpleController simpleController;

    private final IProductRepo IProductRepo;

    ProgInzSeminar1Application(IProductRepo IProductRepo, SimpleController simpleController) {
        this.IProductRepo = IProductRepo;
        this.simpleController = simpleController;
    }

	public static void main(String[] args) {
		SpringApplication.run(ProgInzSeminar1Application.class, args);
	}
	
	@Bean
	public CommandLineRunner saveDataInDB(IProductRepo prodRepo) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				
				//CrudRepositoriju funkcijas: //https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html

				
				Product p1 = new Product("Apple", 0.99f, "Sweet and Tasty", Category.fruit, 10);
				Product p2 = new Product("Carrot", 2.84f, "Orange", Category.vegetable, 40);
				Product p3 = new Product("Grape", 4.99f, "Violet", Category.fruit, 3);
				
				prodRepo.save(p1);
				prodRepo.save(p2);
				prodRepo.save(p3);
				
				
				System.out.println("Ierakstu skaits: " +prodRepo.count());
				System.out.println("Produkts ar id=1: " + prodRepo.findById(1).get());
				System.out.println("Visi produkti: " + prodRepo.findAll());
				prodRepo.deleteById(1);
				System.out.println("Visi produkti pec 1.dzešanas" + prodRepo.findAll());
				
				Product productForUpdate = prodRepo.findById(2).get();
				productForUpdate.setPrice(9.99f);
				prodRepo.save(productForUpdate);
				System.out.println("Visi produkti pec 1.dzešanas un 2.rediģēšanas" + prodRepo.findAll());
				
				
			}
		};
	}
	

}
