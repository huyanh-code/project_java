package model;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    private String orderCode;
    private String productName;
    private String unit;
    private double unitPrice;
    private int quantity;

    public OrderDetails(String orderCode, String productName, String unit, double unitPrice, int quantity) {
        this.orderCode = orderCode;
        this.productName = productName;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Order Code: %-20s | Product: %-10s | Unit: %-3s | Unit Price: %8.2f | Quantity: %2d",
                orderCode, productName, unit, unitPrice, quantity);
    }
}
