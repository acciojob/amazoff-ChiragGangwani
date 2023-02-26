package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

//    public Order() {
//    }

    public Order(String id, String deliveryTime) {
        this.id=id;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        int hh=Integer.parseInt(deliveryTime.charAt(0)+""+deliveryTime.charAt(1));
        int mm=Integer.parseInt((deliveryTime.charAt(3)+""+deliveryTime.charAt(4)));
        this.deliveryTime=hh*60+mm;
        System.out.println(this.deliveryTime);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
