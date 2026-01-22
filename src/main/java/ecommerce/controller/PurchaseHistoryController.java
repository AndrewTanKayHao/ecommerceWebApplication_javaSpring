package ecommerce.controller;
import ecommerce.model.Customer;
import ecommerce.model.Order;
import ecommerce.model.OrderDetail;

import ecommerce.interfacemethods.PurchaseHistoryInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


// Author: Nyunt

    @Controller
    public class PurchaseHistoryController {

        @Autowired
        private PurchaseHistoryInterface purchaseHistoryService;

        @GetMapping("/purchasehistory")
        public String findOrder(HttpSession session, Model model) {
            Customer customer = (Customer) session.getAttribute("sessionCustomer");
            if (customer == null) {
                return "redirect:/login";
            } else {
                System.out.print("Searching orders for customer ID: " + customer.getId());
            }
            List<Order> orders = purchaseHistoryService.findOrderByCustomerId(customer.getId());
            System.out.println("orders: " + orders.size() + "customer: " + customer.getId());
            model.addAttribute("orders", orders);
            model.addAttribute("customer", customer);
            return "purchase-history";
        }

        @GetMapping("/purchasehistory/searching")
        public String findOrderBy(
                @RequestParam(required = false) String searchtype,
                @RequestParam(required = false) String keyword,
                Model model, HttpSession session) {
            Customer customer = (Customer) session.getAttribute("sessionCustomer");
            if (customer == null) {
                return "redirect:/login";
            }
            List<Order> orders = new ArrayList<>();
            System.out.println("🧍 Session customer ID: " + customer.getId());
            System.out.println(" Searching order ID: " + keyword);
            if ("Date".equalsIgnoreCase(searchtype) && keyword != null) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(keyword, formatter);
                    ZonedDateTime date = localDate.atStartOfDay(ZoneId.of("Asia/Singapore"));
                    orders = purchaseHistoryService.findOrderByOrderDate(date, customer.getId());
                } catch (Exception e) {
                    orders = new ArrayList<>();
                }
            } else if ("Id".equalsIgnoreCase(searchtype) && keyword != null) {
                try {
                    long orderId = Long.parseLong(keyword.trim());
                    orders = purchaseHistoryService.findOrderById(orderId, customer.getId());
                } catch (NumberFormatException e) {
                    orders = new ArrayList<>();
                }
            } else if ("All".equalsIgnoreCase(searchtype) || keyword == null || keyword.isBlank()) {
                try {
                    orders = purchaseHistoryService.findOrderByCustomerId(customer.getId());
                } catch (NumberFormatException e) {
                    orders = new ArrayList<>();
                }
            }else if ("Status".equalsIgnoreCase(searchtype) && keyword != null) {
                try {
                    orders = purchaseHistoryService.findOrderByStatus(keyword,customer.getId());
                }catch (Exception e) {
                    orders = new ArrayList<>();
                }
            }

            model.addAttribute("orders", orders);
            model.addAttribute("customer", customer);
            model.addAttribute("searchtype", searchtype);
            model.addAttribute("keyword", keyword);
            return "purchase-history";
        }

        @GetMapping("/purchasehistory/searching/details")
        public String showDetails(HttpSession session, @RequestParam (required = false) long orderId, Model model, @RequestParam(required = false) String searchtype, @RequestParam(required = false) String keyword) {
            Customer customer = (Customer) session.getAttribute("sessionCustomer");
            if (customer == null) {
                return "redirect:/login";
            }
            List<Order> orders = new ArrayList<>();
            if ("Date".equalsIgnoreCase(searchtype) && keyword != null) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(keyword, formatter);
                    ZonedDateTime date = localDate.atStartOfDay(ZoneId.of("Asia/Singapore"));
                    orders = purchaseHistoryService.findOrderByOrderDate(date, customer.getId());
                } catch (Exception e) {
                    orders = new ArrayList<>();
                }
            } else if ("Id".equalsIgnoreCase(searchtype) && keyword != null) {
                try {
                    long searchOrderId = Long.parseLong(keyword.trim());
                    orders = purchaseHistoryService.findOrderById(searchOrderId, customer.getId());
                } catch (NumberFormatException e) {
                    orders = new ArrayList<>();
                }
            } else if ("All".equalsIgnoreCase(searchtype) || keyword == null || keyword.isBlank()) {
                try {
                    orders = purchaseHistoryService.findOrderByCustomerId(customer.getId());
                } catch (NumberFormatException e) {
                    orders = new ArrayList<>();
                }
            }
            else if ("Status".equalsIgnoreCase(searchtype) && keyword != null) {
                try {
                    orders = purchaseHistoryService.findOrderByStatus(keyword,customer.getId());
                }catch (Exception e) {
                    orders = new ArrayList<>();
                }
            }
                List<OrderDetail> details = purchaseHistoryService.findOrderDetailsByOrderId(orderId);
                model.addAttribute("orders", orders);
                model.addAttribute("details", details);
                model.addAttribute("selectedOrderId", orderId);
                model.addAttribute("customer", customer);
                model.addAttribute("searchtype", searchtype);
                model.addAttribute("keyword", keyword);
                return "purchase-history";
            }
        @PostMapping("/purchasehistory/cancel")
        public String deleteOrder(@RequestParam("orderId") long orderId, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
            Customer customer = (Customer) session.getAttribute("sessionCustomer");
            if (customer == null) {
                return "redirect:/login";
            }
            boolean cancelled = purchaseHistoryService.cancelOrder(customer.getId(), orderId);
            if (cancelled) {
                redirectAttributes.addFlashAttribute("message", "Order has been cancelled");
            } else {
                redirectAttributes.addFlashAttribute("error", "Unable to Cancel, Order has been processed");
            }
            return "redirect:/purchasehistory";
        }
        }
        





