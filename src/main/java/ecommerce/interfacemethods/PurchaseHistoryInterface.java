package ecommerce.interfacemethods;

import ecommerce.model.Customer;
import ecommerce.model.OrderDetail;
import ecommerce.model.Order;
import ecommerce.model.OrderStatus;

import java.time.ZonedDateTime;
import java.util.List;

//Author: Nyunt
public interface PurchaseHistoryInterface {
    List<Order> getAllOrders();
    List<Order> findOrderByOrderDate(ZonedDateTime orderDate, long customerId);
    List<Order> findOrderById(long orderId, long customerId);
    List<Order> findOrderByCustomerId(long customer);
    List<Order> findOrderByStatus(String status, long customerId);
    // We fetch details through Orders (no OrderDetails repo)
    List<OrderDetail> findOrderDetailsByOrderId(long orderId);
    boolean cancelOrder(long orderId, long customerId);
}
