import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
            System.out.println("3 Thêm mới sản phẩm");
            System.out.println("4. Danh sách sản phẩm");
            System.out.println("5. Thoát ");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();

            switch (choice){
                case 1:
                    createOrder();
                    break;
                case 2:
                    displayOrders();
                    break;
                case 3:
                    addProduct();
                    break;
                case 4:
                    listProducts();
                    break;
                case 5:
                    saveProducts();
                    System.out.println("Bye~");
                    break;
            }
        }while (choice != 5);

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
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("orders.txt"), StandardCharsets.UTF_8))) {
            for (String order : orders) {
                writer.write(order.toString());
                writer.newLine();// Thêm dòng mới sau mỗi đơn hàng
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    private static void createOrder() {
        Scanner scanner = new Scanner(System.in);

        // Nhập thông tin khách hàng.
        System.out.print("Nhập tên khách hàng: ");
        String name = scanner.nextLine();

        // Nhập địa chỉ khách hàng.
        System.out.print("Nhập địa chỉ khách hàng: ");
        String address = scanner.nextLine();

        // Nhập số điện thoại khách hàng.
        System.out.print("Nhập số điện thoại khách hàng: ");
        String phoneNumber = scanner.nextLine();

        // Nhập email khách hàng.
        System.out.print("Nhập email khách hàng: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(name, address, phoneNumber, email);

        // Tạo danh sách chi tiết đơn hàng.
        List<OrderDetails> orderDetails = new ArrayList<>();
        double totalPrice = 0.0;

        while (true) {
            // Hiển thị danh sách sản phẩm
            System.out.println("\nDanh sách sản phẩm:");
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i));
            }

            // Chọn sản phẩm
            System.out.print("Chọn sản phẩm bằng số thứ tự (hoặc nhập 0 để hoàn thành): ");
            int productIndex = scanner.nextInt() - 1;
            if (productIndex < 0) break;

            Product selectedProduct = products.get(productIndex);

            // Nhập số lượng mua
            System.out.print("Nhập số lượng mua: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            if (quantity > selectedProduct.getQuantityInStock()) {
                System.out.println("Không đủ số lượng sản phẩm trong kho.");
                continue;
            }

            // Tính tổng tiền cho sản phẩm đó
            double productTotal = selectedProduct.getPriceRetail() * quantity;
            totalPrice += productTotal;

            // Tạo chi tiết đơn hàng
            OrderDetails details = new OrderDetails(
                    UUID.randomUUID().toString(),
                    selectedProduct.getProductName(),
                    "cái", // Đơn vị tính
                    selectedProduct.getPriceRetail(),
                    quantity
            );
            orderDetails.add(details);

            // Giảm số lượng sản phẩm trong kho
            selectedProduct.setQuantityInStock(selectedProduct.getQuantityInStock() - quantity);
        }

        // Tạo đơn hàng
        String orderId = UUID.randomUUID().toString();
        Date orderDate = new Date(); // Ngày hiện tại

        Order order = new Order(orderId, orderDate, customer, totalPrice, orderDetails);
        orders.add(String.valueOf(order)); // Lưu đối tượng Order

        System.out.println("Đơn hàng đã được tạo thành công!");

        // Hiển thị chi tiết đơn hàng
        System.out.println(order.toString());

        // Lưu đơn hàng vào tệp
        saveOrders();
        // Lưu danh sách sản phẩm đã cập nhật vào tệp
        saveProducts();
    }

    //Hiển thị danh sách đơn đã lưu
    private static void displayOrders() {
        System.out.println("List of Orders:");
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            for (String order : orders) {
                System.out.println(order);
            }
        }
    }
}
