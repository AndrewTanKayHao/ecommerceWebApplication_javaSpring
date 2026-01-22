package ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ecommerce.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ecommerce.interfacemethods.CartInterface;
import ecommerce.interfacemethods.CustomerInterface;
import ecommerce.interfacemethods.OrderDetailInterface;
import ecommerce.interfacemethods.OrderInterface;
import ecommerce.interfacemethods.ProductInterface;
import ecommerce.repository.ProductRepository;

@SpringBootTest
public class CartAndOrderTestData {

	@Autowired
	private ProductRepository ProductRepo;

	@Autowired
	private ProductInterface productService;

	@Autowired
	private CustomerInterface customerService;

	@Autowired
	private OrderInterface orderService;

	@Autowired
	private OrderDetailInterface orderDtlService;

	@Autowired
	private CartInterface cartService;

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

			ProductRepo.saveAll(productList);
		}
	}

	@Test
	public void CartTest() {
		//This is Customer test data//
		ArrayList<Product> productList  = (ArrayList<Product>) productService.getAllProducts();
		System.out.println(productList);
		//productList.forEach(product -> product.setOrderedQuantity(10));
		Customer customer = new Customer("Spongebob Squarepants", "spongebob@fakemail.com", "password123", true);
		customerService.save(customer);


		//This is Cart test data//
		Cart cart = cartService.createCart(customer);
		ProductCategory Candy= new ProductCategory("Candy");
		ProductCategory Cereal= new ProductCategory("Cereal");
		Product Skittles = new Product("Skittles", Candy, "Taste the rainbow!", BigDecimal.valueOf(3.60), "URL", 30);
		Product Trix = new Product("Trix Cereal", Cereal, "Trix are for kids!", BigDecimal.valueOf(7.20), "URL", 50);
		productService.save(Skittles);
		productService.save(Trix);

		cartService.addCartProduct(cart, Skittles, 1);
		cartService.addCartProduct(cart, Trix, 1);
		cartService.updateCartQuantity(cart, Skittles, 5);
		cartService.updateCartQuantity(cart, Trix, 3);
		cartService.removeCartProduct(cart, Trix);


//		//This is Order test data//
//		Orders order = new Orders(cart);
//		orderService.save(order);
//		System.out.println(order);
//		Orders order1 = new Orders(cart);
//		orderService.save(order1);
//		System.out.println(order1);
	}
}
