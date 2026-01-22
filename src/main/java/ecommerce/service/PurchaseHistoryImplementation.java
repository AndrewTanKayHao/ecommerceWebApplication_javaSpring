package ecommerce.service;

import ecommerce.interfacemethods.PurchaseHistoryInterface;
import ecommerce.model.OrderDetail;
import ecommerce.model.Order;
import ecommerce.model.OrderStatus;
import ecommerce.model.Product;
import ecommerce.repository.ProductRepository;
import ecommerce.repository.PurchaseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

//Author: Nyunt

@Service
@Transactional
public class PurchaseHistoryImplementation implements PurchaseHistoryInterface {

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepo;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Order> getAllOrders() {
        return purchaseHistoryRepo.findAll();
    }

    @Override
    public List<Order> findOrderByOrderDate(ZonedDateTime orderDate, long customerId) {
        ZonedDateTime startOfDay = orderDate.toLocalDate().atStartOfDay(orderDate.getOffset());
        ZonedDateTime endOfDay = startOfDay.plusDays(1);
        return purchaseHistoryRepo.findOrderByOrderDate(startOfDay,endOfDay, customerId);
    }

    @Override
    public List<Order> findOrderById(long orderId, long customerId) {
        return purchaseHistoryRepo.findOrderById(orderId, customerId);
    }
    @Override
    public List<Order> findOrderByStatus(String status, long customerId) {
        return purchaseHistoryRepo.findOrderByStatus(status, customerId);
    }

    @Override
    public List<Order> findOrderByCustomerId(long customerId) {
        System.out.println("Searching orders for customer ID: " + customerId);
        return purchaseHistoryRepo.findOrderByCustomerId(customerId);
    }


    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(long orderId) {
        // EAGER on Orders.orderDetails ensures this returns the list ready for Thymeleaf
        return purchaseHistoryRepo.findById(orderId)
                .map(Order::getOrderDetailList)
                .orElse(List.of());
    }
    @Override
    @Transactional
    public boolean cancelOrder(long customerId, long orderId) {
        Optional<Order> optionalOrder = purchaseHistoryRepo.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();

            if (order.getCustomer().getId() == customerId && order.getOrderStatus().getStatus().equalsIgnoreCase("Pending")) {
                for(OrderDetail orderDetail : order.getOrderDetailList()) {
                    Product product = orderDetail.getProduct();
                    int cancelledQty=orderDetail.getOrderedQuantity();
                    product.setStockQuantity(cancelledQty+product.getStockQuantity());
                    productRepository.save(product);
                }
                OrderStatus cancelOrderStatus = purchaseHistoryRepo.findOrderStatusByName("Cancelled");
                order.setStatus(cancelOrderStatus);
                purchaseHistoryRepo.save(order);
                return true;
            }
        }
        return false;
    }
}
