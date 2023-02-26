package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    public ResponseEntity<String> addOrder(Order order) {
        return orderRepository.addOrder(order);
    }

    public ResponseEntity<String> addPartner(String partnerId) {
        return orderRepository.addPartner(partnerId);
    }

    public ResponseEntity<String> addOrderPartnerPair(String orderId, String partnerId) {
        return orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public ResponseEntity<Order> getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public ResponseEntity<DeliveryPartner> getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public ResponseEntity<Integer> getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public ResponseEntity<List<String>> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public ResponseEntity<List<String>> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public ResponseEntity<Integer> getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public ResponseEntity<String> deletePartnerById(String partnerId) {
        return orderRepository.deletePartnerById(partnerId);
    }

    public ResponseEntity<String> deleteOrderById(String orderId) {
        return orderRepository.deleteOrderById(orderId);
    }
}
