package model;

import java.io.Serializable;

public class Product implements Serializable {
    private String productCode;
    private String productName;
    private double priceRetail;
    private double importPrice;
    private int quantityInStock;

    public Product(String productCode, String productName, double priceRetail, double importPrice, int quantityInStock) {
        this.productCode = productCode;
        this.productName = productName;
        this.priceRetail = priceRetail;
        this.importPrice = importPrice;
        this.quantityInStock = quantityInStock;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPriceRetail() {
        return priceRetail;
    }

    public void setPriceRetail(double priceRetail) {
        this.priceRetail = priceRetail;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", priceRetail=" + priceRetail +
                ", importPrice=" + importPrice +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}
