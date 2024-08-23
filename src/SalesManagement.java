import model.Product;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SalesManagement {
    private static List<Product> products = new ArrayList<>();
    private static List<String> orders = new ArrayList<>();

    private static String ORDERS_FILE = "orders.txt";
    private static String PRODUCT_FILE = "products.txt";

    public static void main(String[] args) {

        loadOrders();
        loadProducts();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
            System.out.println("Menu: ");
            System.out.println("1. Tạo đơn bán hàng");
            System.out.println("2. Quản lý đơn bán hàng");
            System.out.println("3. Xem báo cáo bán hàng");
            System.out.println("4. Thêm mới sản phẩm");
            System.out.println("5. Danh sách sản phẩm");
            System.out.println("6. Thoát ");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    createOrder();
                    break;
                case 2:
                    manageOrders();
                    break;
                case 3:
                    viewSalesReport();
                    break;
                case 4:
                    addProduct();
                    break;
                case 5:
                    listProducts();
                    break;
                case 6:
                    saveProducts();
                    System.out.println("Bye~");
                    break;
            }
        }while (choice != 6);

        scanner.close();
    }

    private static void loadProducts() {
        products.clear(); // Xóa danh sách sản phẩm cũ trước khi tải lại
        File file = new File(PRODUCT_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PRODUCT_FILE), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 5) {
                        String productCode = parts[0];
                        String productName = parts[1];
                        double priceRetail = Double.parseDouble(parts[2]);
                        double importPrice = Double.parseDouble(parts[3]);
                        int quantityInStock = Integer.parseInt(parts[4]);

                        Product product = new Product(productCode, productName, priceRetail, importPrice, quantityInStock);
                        products.add(product);
                    }
                }
                System.out.println("Products loaded successfully.");
            } catch (IOException e) {
                System.err.println("Error loading products: " + e.getMessage());
            }
        } else {
            System.out.println("Product file does not exist. Starting with an empty product list.");
        }
    }



    private static void saveProducts() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PRODUCT_FILE), StandardCharsets.UTF_8))) {
            for (Product product : products) {
                writer.write(product.getProductCode() + "," +
                        product.getProductName() + "," +
                        product.getPriceRetail() + "," +
                        product.getImportPrice() + "," +
                        product.getQuantityInStock());
                writer.newLine();
            }
            System.out.println("Products saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving products");
        }
    }

    private static void loadOrders() {
        orders.clear(); //Xóa đơn hàng cũ trước khi tải lại
        File file = new File(ORDERS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ORDERS_FILE), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    orders.add(line);
                }
                System.out.println("Orders loaded successfully.");
            } catch (IOException e) {
                System.out.println("Error loading orders");
            }
        } else {
            System.out.println("Order file does not exist. Starting with an empty order list.");
        }
    }


    private static void saveOrders() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ORDERS_FILE, true), StandardCharsets.UTF_8))) {
            for (String order : orders) {
                writer.write(order);
                writer.newLine();
            }
            System.out.println("Orders saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving orders");
        }
    }


    private static void addProduct() {
        Scanner scanner = new Scanner(System.in);

        // Nhập thông tin sản phẩm
        System.out.print("Enter product code: ");
        String productCode = scanner.nextLine();

        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter retail price: ");
        double priceRetail = scanner.nextDouble();

        System.out.print("Enter import price: ");
        double importPrice = scanner.nextDouble();

        System.out.print("Enter quantity in stock: ");
        int quantityInStock = scanner.nextInt();
        scanner.nextLine();

        Product newProduct = new Product(productCode, productName, priceRetail, importPrice, quantityInStock);

        // Thêm sản phẩm vào danh sách
        products.add(newProduct);

        // Lưu danh sách sản phẩm vào file
        saveProducts();

        System.out.println("Product added and saved successfully.");
        System.out.println("------------------------------------");
    }

    private static void createOrder() {
        Scanner scanner = new Scanner(System.in);

        // Nhập mã sản phẩm và tìm kiếm sản phẩm
        Product product = null;
        while (product == null) {
            System.out.print("Enter product code: ");
            String productCode = scanner.nextLine();
            product = findProductByCode(productCode);

            if (product == null) {
                System.out.println("Product not found. Please try again.");
            }
        }

        // Nhập số lượng sản phẩm bán
        int quantity = 0;
        while (quantity <= 0) {
            System.out.print("Enter quantity to sell: ");
            quantity = scanner.nextInt();
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0. Please try again.");
            }
        }

        // Tính thành tiền
        double totalPrice = product.getPriceRetail() * quantity;

        // Tạo chuỗi thông tin đơn hàng
        String orderInfo = product.getProductCode() + " | " +
                product.getProductName() + " | " +
                product.getPriceRetail() + " | " +
                quantity + " | " +
                totalPrice;

        // Thêm đơn hàng vào danh sách và lưu vào file
        orders.clear(); // Đảm bảo danh sách orders chỉ có 1 đơn hàng
        orders.add(orderInfo);
        saveOrders(); // Lưu tất cả đơn hàng vào file

        System.out.println("Order created and saved successfully.");
        System.out.println("-----------------------------------");
    }

    private static void manageOrders() {
        // Đọc danh sách đơn hàng từ tệp orders.txt
        loadOrders();

        // Hiển thị danh sách đơn hàng
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("List of Orders:");
            for (String order : orders) {
                System.out.println(order);
            }
        }
    }

    private static void viewSalesReport() {
        double totalSales = 0;
        double totalCost = 0;
        int totalQuantitySold = 0;

        // Tính tổng số lượng sản phẩm đã bán và tổng tiền bán hàng
        for (String order : orders) {
            String[] parts = order.split(" \\| ");
            if (parts.length == 5) {
                int quantitySold = Integer.parseInt(parts[3]);
                double priceRetail = Double.parseDouble(parts[2]);
                double totalPrice = Double.parseDouble(parts[4]);

                totalQuantitySold += quantitySold;
                totalSales += totalPrice;
            }
        }

        // Tính tổng tiền nhập hàng
        double totalImportCost = 0;
        for (Product product : products) {
            int quantityInStock = product.getQuantityInStock();
            double importPrice = product.getImportPrice();
            totalImportCost += (quantityInStock * importPrice);
        }

        // Tính tổng lãi
        double totalProfit = totalSales - totalImportCost;

        // Hiển thị báo cáo
        System.out.printf("Total Quantity Sold: %d%n", totalQuantitySold);
        System.out.printf("Total Sales Amount: %.2f%n", totalSales);
        System.out.printf("Total Profit: %.2f%n", totalProfit);
    }

    private static void listProducts() {
        // Đọc danh sách sản phẩm từ tệp products.txt
        loadProducts();

        // Hiển thị danh sách sản phẩm
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println("List of Products:");
            for (Product product : products) {
                System.out.printf("Code: %s, Name: %s, Retail Price: %.2f, Import Price: %.2f, Quantity in Stock: %d%n",
                        product.getProductCode(),
                        product.getProductName(),
                        product.getPriceRetail(),
                        product.getImportPrice(),
                        product.getQuantityInStock());
            }
        }
    }



    private static Product findProductByCode(String productCode) {
        for (Product product : products) {
            if (product.getProductCode().equalsIgnoreCase(productCode)) {
                return product;
            }
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

}
