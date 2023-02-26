package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String,Order> orders=new HashMap<>();
    HashMap<String, ArrayList<String>> partners=new HashMap<>();
    HashMap<String,String> orderPartnerPair=new HashMap<>();

    public ResponseEntity<String> addOrder(Order order) {
        if(orders.containsKey(order.getId())){
            return new ResponseEntity<>("Order Id already Exist", HttpStatus.ALREADY_REPORTED);
        }
        orders.put(order.getId(),order);
        return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> addPartner(String partnerId) {
        if(partners.containsKey(partnerId)){
            return new ResponseEntity<>("Partner is already Exist", HttpStatus.ALREADY_REPORTED);
        }
        partners.put(partnerId, new ArrayList<String>());
        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }


    public ResponseEntity<String> addOrderPartnerPair(String orderId, String partnerId) {
        if(partners.containsKey(partnerId) && orders.containsKey(orderId)) {
            ArrayList<String> temp= partners.get(partnerId);
            for(String i :temp){
                if(i.equals(orderId))
                    return new ResponseEntity<>("Order already assigned", HttpStatus.ALREADY_REPORTED);
            }
            temp.add(orderId);
            orderPartnerPair.put(orderId,partnerId);
            return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Partner or Order is not Exist", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Order> getOrderById(String orderId) {
        if(!orders.containsKey(orderId)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Order order=orders.get(orderId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }


    public ResponseEntity<DeliveryPartner> getPartnerById(String partnerId) {
        if(!partners.containsKey(partnerId)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        deliveryPartner.setNumberOfOrders(partners.get(partnerId).size());
        return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
    }


    public ResponseEntity<Integer> getOrderCountByPartnerId(String partnerId) {
        if(!partners.containsKey(partnerId)){
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
        int orderCount=partners.get(partnerId).size();
        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }

    public ResponseEntity<List<String>> getOrdersByPartnerId(String partnerId) {
        if(!partners.containsKey(partnerId)){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<String> orders=partners.get(partnerId);
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }


    public ResponseEntity<List<String>> getAllOrders() {
        List<String> orders=new ArrayList<>();
        for(String i : partners.keySet()){
            List<String>temp=partners.get(i);
            orders.addAll(temp);
        }
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    public ResponseEntity<Integer> getCountOfUnassignedOrders() {
        int countOfOrders=orders.size()-orderPartnerPair.size();
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(String deliveryTime, String partnerId) {
        int tim=Integer.parseInt(deliveryTime.charAt(0)+""+deliveryTime.charAt(1))*60+Integer.parseInt(deliveryTime.charAt(3)+""+deliveryTime.charAt(4));
        int countOfOrders=0;
            List<String>temp=partners.get(partnerId);
            if(temp!=null) {
                for (String t : temp) {
                    Order n = orders.get(t);
                    int timee = n.getDeliveryTime();
                    if (timee > tim)
                        countOfOrders++;
                }
            }
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(String partnerId) {

        int finalTime=0;
        List<String>temp=partners.get(partnerId);
        for(String s : temp){
            int t=orders.get(s).getDeliveryTime();
            if(t>finalTime)
                finalTime=t;
        }
        String time="";
        int count=0;
        while(finalTime!=0){
            time=finalTime%10+time;
            if(count==2)
                time=":"+time;
            count++;
            finalTime=finalTime/10;
        }
        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deletePartnerById(String partnerId) {
        partners.remove(partnerId);
        for(String i:orderPartnerPair.keySet()){
            if(orderPartnerPair.get(i)==partnerId)
                orderPartnerPair.remove(i);
        }
        return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteOrderById(String orderId) {
        orders.remove(orderId);
        String s=orderPartnerPair.get(orderId);
        orderPartnerPair.remove(orderId);
        ArrayList<String>temp=partners.get(s);
        if(temp!=null) {
            temp.remove(orderId);
            partners.put(s, temp);
        }
        return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
    }
}
