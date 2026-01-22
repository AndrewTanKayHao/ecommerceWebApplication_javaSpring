package ecommerce.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ecommerce.interfacemethods.OrderInterface;
import ecommerce.interfacemethods.PaymentInterface;
import ecommerce.model.Order;
import jakarta.transaction.Transactional;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;


//Author: Dang Lam

@Service("stripePaymentService")
@Transactional
public class PaymentStripeImplementation implements PaymentInterface {

	private String name = "Stripe";
	
	@Autowired
	OrderInterface orderService;

	@Value("${stripe.api.key}")
	private String stripeApiKey;

	@Value("${stripe.domain}")
	private String domain;

	@Override
	public String getPaymentMethodName() {
		return name;
	}

	@Override
	public String processPayment(Order order) throws Exception {

		Stripe.apiKey = stripeApiKey;

		orderService.setOrderPaymentMethod(order, this.name);
		
		BigDecimal total = order.getOrderTotal();
		long amount = total.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP).longValue();
		
		SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl(domain + "/success?orderId=" + order.getId())
				.setCancelUrl(domain + "/cancel?orderId=" + order.getId())
				.addLineItem(
						SessionCreateParams.LineItem.builder().setQuantity(1L)
								.setPriceData(
										SessionCreateParams.LineItem.PriceData.builder().setCurrency("sgd")
												.setUnitAmount(amount)
												.setProductData(SessionCreateParams.LineItem.PriceData.ProductData
														.builder().setName("Your Order").build())
												.build())
								.build())
				.build();

		Session session = Session.create(params);
		return session.getUrl();
	}
}
