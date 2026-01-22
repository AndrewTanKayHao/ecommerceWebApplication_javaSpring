package ecommerce;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ecommerce.model.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ecommerce.model.Product;
import ecommerce.repository.ProductRepository;

//Do not delete test data

@SpringBootTest
public class ProductTestData {

	@Autowired
	private ProductRepository ProductRepo;

	private List<Product> productList = new ArrayList<>();

	@Test
	public void saveProduct() {
		if(ProductRepo.count()==0) {

			ProductCategory luxuryPens = new ProductCategory("Luxury Pens");
			ProductCategory notebooks = new ProductCategory("Notebooks & Journals");

			Product Aurora = new Product("Aurora Noir Fountain Pen", luxuryPens, "Midnight black barrel with 24k gold nib", BigDecimal.valueOf(48), "/images/auroraPen.jpg", 20);
			productList.add(Aurora);

			Product Celestine = new Product("Celestine Gold Gel Set", luxuryPens, "Set of 5 shimmer-infused gel pens in luxe tones", BigDecimal.valueOf(35.9), "/images/celestine.jpg", 18);
			productList.add(Celestine);

			Product Obsidian = new Product("Obsidian Script Roller", luxuryPens, "Sleek matte rollerball pen with obsidian accents", BigDecimal.valueOf(42.5), "/images/obsidian.jpg", 32);
			productList.add(Obsidian);

			Product VellumCraft = new Product("VellumCraft Artisan Pen", luxuryPens, "Handmade body with polished resin swirl pattern", BigDecimal.valueOf(55), "/images/vellum.jpg", 41);
			productList.add(VellumCraft);

			Product Velour = new Product("Velour Luxe Journal", notebooks, "Soft suede-feel hardcover with gilded edges", BigDecimal.valueOf(32), "/images/velour.jpg", 35);
			productList.add(Velour);

			Product EbonSky = new Product("EbonSky Dot Grid Notebook", notebooks, "Deep navy cover with silver constellation design", BigDecimal.valueOf(28.5), "/images/ebony.jpg", 20);
			productList.add(EbonSky);

			Product Monarch = new Product("Monarch Leather Planner", notebooks, "Full-grain leather planner with refillable inserts", BigDecimal.valueOf(68), "/images/monarch.jpg", 10);
			productList.add(Monarch);

			Product SilkScript = new Product("SilkScript Daily Organiser", notebooks, "Satin-touch cover with embossed floral motifs", BigDecimal.valueOf(32), "/images/silkscript.jpg", 26);
			productList.add(SilkScript);
			
			Product ZenithGold = new Product("Zenith Gold Planner", notebooks, "Fine-grain leather with the Zenith Star", BigDecimal.valueOf(49), "/images/ZenithGold.jpg", 40);
			productList.add(ZenithGold);
			
			Product ZenithSilver = new Product("Zenith Matt Silver Planner", notebooks, "Fine-grain leather with the Zenith Constellation", BigDecimal.valueOf(49), "/images/ZenithSilver.jpg", 40);
			productList.add(ZenithSilver);


			ProductRepo.saveAll(productList);
		}
	}
}
