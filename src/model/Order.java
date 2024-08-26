package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private Date orderDate;
    private Customer customer;  // Changed to Customer object
    private double totalPrice;
    private List<OrderDetails> orderDetails;

    public Order(String id, Date orderDate, Customer customer, double totalPrice, List<OrderDetails> orderDetails) {
        this.id = id;
        this.orderDate = orderDate;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.orderDetails = orderDetails;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(id).append("\n");
        sb.append("Order Date: ").append(orderDate).append("\n");
        sb.append("Customer Information:\n");
        sb.append("Name: ").append(customer.getName()).append("\n");
        sb.append("Address: ").append(customer.getAddress()).append("\n");
        sb.append("Phone Number: ").append(customer.getPhoneNumber()).append("\n");
        sb.append("Email: ").append(customer.getEmail()).append("\n");
        sb.append("Total Price: ").append(String.format("%.2f", totalPrice)).append("\n");
        sb.append("Order Details:\n");

        for (OrderDetails details : orderDetails) {
            sb.append("  Order Code: ").append(details.getOrderCode())
                    .append(" | Product: ").append(details.getProductName())
                    .append(" | Unit: ").append(details.getUnit())
                    .append(" | Unit Price: ").append(String.format("%.2f", details.getUnitPrice()))
                    .append(" | Quantity: ").append(details.getQuantity())
                    .append("\n");
        }

        sb.append("--------------------------\n");
        return sb.toString();
    }
}

