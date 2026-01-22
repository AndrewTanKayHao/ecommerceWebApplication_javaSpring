package ecommerce;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import ecommerce.model.*;
import ecommerce.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

// Author: Angeline, Chee Wee and Sara

@SpringBootApplication
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Bean
	CommandLineRunner testData(ProductRepository productRepo, ProductCategoryRepository productCategoryRepo, CustomerRepository customerRepo, AdminRepository adminRepo,
							   OrderRepository orderRepo, OrderDetailRepository orderDetailsRepo, OrderStatusRepository orderStatusRepo, UserDetailsManager users, PasswordEncoder encoder) {
		return args -> {

			//create admin account
			final String admin = "admin@mail.com";
			if(!users.userExists(admin)){
				Admin Alice = new Admin("Alice Eng", "admin@mail.com", encoder.encode("admin123"));
				adminRepo.save(Alice);

				UserDetails adminDetails = User.withUsername(admin)
						.password(encoder.encode("admin123"))
						.roles("ADMIN")
						.build();
				users.createUser(adminDetails);
				System.out.println("Admin User created successfully");
			}

			//create customer account (John)
			final String customer = "test@mail.com";
			if (!users.userExists(customer)) {
				Customer John = new Customer("John Tan", "test@mail.com", encoder.encode("password"), true);
				John.setAddress("Corporation Drive Street 31 S6039182");
				John.setPhoneNumber("91234578");

				customerRepo.save(John);

				UserDetails customerDetails = User.withUsername(customer)
						.password(encoder.encode("password"))
						.roles("CUSTOMER")
						.build();
				users.createUser(customerDetails);
				System.out.println("Customer User created successfully");
			}

			List<Product> productList = new ArrayList<>();
			List<OrderDetail> orderDetailList1 = new ArrayList<>();
			List<OrderDetail> orderDetailList2 = new ArrayList<>();

			if (productRepo.count() == 0 && productCategoryRepo.count() == 0) {

				//initialise products
				ProductCategory luxuryPens = new ProductCategory("Luxury Pens");
				ProductCategory notebooks = new ProductCategory("Notebooks & Journals");
				productCategoryRepo.save(luxuryPens);
				productCategoryRepo.save(notebooks);

				Product Aurora1 = new Product("Aurora Night Noir", luxuryPens, "Midnight black barrel with 24k gold nib", BigDecimal.valueOf(48), "/images/AuroraStars.png", 20);
				productList.add(Aurora1);

				Product Aurora2 = new Product("Aurora Lumina", luxuryPens, "Rose Gold Barrel, illuminated with glittering Crystals", BigDecimal.valueOf(35.9), "/images/AuroraCraft.png", 18);
				productList.add(Aurora2);

				Product Obsidian = new Product("Obsidian Script Roller", luxuryPens, "Sleek matte rollerball pen with obsidian accents", BigDecimal.valueOf(42.5), "/images/ObsidianScript.png", 32);
				productList.add(Obsidian);

				Product Parker = new Product("Parker Midnight Astral", luxuryPens, "Deep blue lacquer barrel with constellation engraving", BigDecimal.valueOf(98), "/images/ParkerSonnet.jpg", 48);
				productList.add(Parker);
				
				Product Caran = new Product("Caran d’Ache 849 Classic ", luxuryPens, "Sleek aluminum body with smooth, precision rollerball tip", BigDecimal.valueOf(69), "/images/Caran.png", 88);
				productList.add(Caran);
				
				Product London = new Product("London Brown Ballpoint Pen", luxuryPens, "Rhodium-plated brass with leather barrel and silver cap", BigDecimal.valueOf(129), "/images/LondonBallpoint_brown.png", 89);
				productList.add(London);
				
				Product Regent = new Product("Regent Gold Fountain Pen", luxuryPens, "Gold-plated body with precision nib for smooth writing", BigDecimal.valueOf(189), "/images/RegentFountain.png", 86);
				productList.add(Regent);
				
				Product Scriveiner = new Product("Scriveiner Gold Fountain Pen", luxuryPens, "Brass body with black lacquer and 24k gold finish", BigDecimal.valueOf(89), "/images/ScriveinerGold.png", 87);
				productList.add(Scriveiner);

				Product Velour = new Product("Velour Luxe Signature Journal", notebooks, "Soft suede-feel hardcover with gilded edges", BigDecimal.valueOf(32), "/images/velour.png", 35);
				productList.add(Velour);

				Product EbonSky = new Product("EbonSky Dot Grid Notebook", notebooks, "Deep navy cover with silver constellation design", BigDecimal.valueOf(28.5), "/images/ebony.png", 20);
				productList.add(EbonSky);

				Product Monarch = new Product("Monarch Leather Planner", notebooks, "Full-grain leather planner with refillable inserts", BigDecimal.valueOf(68), "/images/monarch.png", 10);
				productList.add(Monarch);

				Product SilkScript = new Product("SilkScript Daily Organiser", notebooks, "Satin-touch cover with embossed floral motifs", BigDecimal.valueOf(32), "/images/silkscript.png", 26);
				productList.add(SilkScript);
				
				Product ZenithGold = new Product("Zenith Gold Executive Planner", notebooks, "Fine-grain leather with the Zenith Star", BigDecimal.valueOf(55), "/images/ZenithGold.png", 27);
				productList.add(ZenithGold);
				
				Product ZenithSilver = new Product("Zenith Matt Silver Planner", notebooks, "Fine-grain leather with the Zenith Constellation", BigDecimal.valueOf(55), "/images/ZenithSilver.png", 28);
				productList.add(ZenithSilver);
				
				Product ZenithCroc = new Product("Zenith Matt Croc Planner", notebooks, "Matte crocodile-textured cover with refined, timeless elegance", BigDecimal.valueOf(59), "/images/ZenithCroc.png", 29);
				productList.add(ZenithCroc);
				
				Product VeganLeather = new Product("OtterSkin Ethical Planner ", notebooks, "Vegan leather cover by Vegeatte with a smooth finish", BigDecimal.valueOf(69), "/images/VeganLeather.png", 31);
				productList.add(VeganLeather);

				productRepo.saveAll(productList);

				//initialise order status
				OrderStatus Pending = new OrderStatus("Pending");
				OrderStatus Delivered = new OrderStatus("Delivered");
				OrderStatus Cancelled = new OrderStatus("Cancelled");
				OrderStatus UnderReview = new OrderStatus("Under Review");

				orderStatusRepo.save(Pending);
				orderStatusRepo.save(Delivered);
				orderStatusRepo.save(Cancelled);
				orderStatusRepo.save(UnderReview);

				//initialise John Order no. 1
				Order order1 = new Order();
				order1.setCustomer(customerRepo.findByEmail("test@mail.com"));
				order1.setDateTime(ZonedDateTime.of(2025, 10, 1, 22, 45, 0, 0, ZoneId.of("Asia/Singapore")));
				order1.setStatus(Delivered);

				orderDetailList1 = List.of(
						new OrderDetail(Aurora1, 10),
						new OrderDetail(SilkScript, 2),
						new OrderDetail(Velour, 4),
						new OrderDetail(Obsidian, 2)
				);

				for (OrderDetail d : orderDetailList1) {
					d.setOrder(order1);
				}
				order1.setOrderDetailList(orderDetailList1);

				BigDecimal total = BigDecimal.ZERO;
				for (OrderDetail orderDetail : orderDetailList1) {
					Product product = productRepo.findById(orderDetail.getProduct().getId())
							.orElseThrow(() -> new RuntimeException("Product with id " + orderDetail.getProduct().getId() + " not found"));
					total=total.add(product.getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getOrderedQuantity())));
				}
				order1.setOrderTotal(total);
				order1.setPaymentMethod("Stripe");
				orderRepo.save(order1);

				//initialise John Order no. 2
				Order order2 = new Order();
				order2.setCustomer(customerRepo.findByEmail("test@mail.com"));
				order2.setDateTime(ZonedDateTime.now(ZoneId.of("Asia/Singapore")).minusDays(1));
				order2.setStatus(Pending);

				orderDetailList2 = List.of(
						new OrderDetail(EbonSky, 2),
						new OrderDetail(Parker, 2),
						new OrderDetail(Velour, 4),
						new OrderDetail(Caran, 11)
				);

				for (OrderDetail d : orderDetailList2) {
					d.setOrder(order2);
				}
				order2.setOrderDetailList(orderDetailList2);

				BigDecimal total2 = BigDecimal.ZERO;
				for (OrderDetail orderDetail : orderDetailList2) {
					Product product = productRepo.findById(orderDetail.getProduct().getId())
							.orElseThrow(() -> new RuntimeException("Product with id " + orderDetail.getProduct().getId() + " not found"));
					total2=total2.add(product.getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getOrderedQuantity())));
				}
				order2.setOrderTotal(total2);
				order2.setPaymentMethod("Stripe");
				orderRepo.save(order2);
			}
		};
	}
}