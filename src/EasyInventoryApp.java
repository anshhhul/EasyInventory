
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EasyInventoryApp extends Application {

    private ObservableList<Product> inventory;
    private List<Sale> sales;
    private List<Order> orders;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("EasyInventory");

        // Initialize inventory, sales, and orders lists
        inventory = FXCollections.observableArrayList();
        sales = new ArrayList<>();
        orders = new ArrayList<>();

        // Create UI components
        TableView<Product> table = new TableView<>();
        table.setItems(inventory);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        table.getColumns().addAll(nameColumn, priceColumn, quantityColumn);

        // Product management screen
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField quantityField = new TextField();

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            Product product = new Product(name, price, quantity);
            inventory.add(product);

            nameField.clear();
            priceField.clear();
            quantityField.clear();
        });

        VBox productManagementLayout = new VBox();
        productManagementLayout.setSpacing(10);
        productManagementLayout.setPadding(new Insets(10));
        productManagementLayout.getChildren().addAll(table, nameField, priceField, quantityField, addButton);

        // Sales screen
        TableView<Sale> salesTable = new TableView<>();
        salesTable.setItems(FXCollections.observableArrayList(sales));

        TableColumn<Sale, String> saleProductColumn = new TableColumn<>("Product");
        saleProductColumn.setCellValueFactory(cellData -> cellData.getValue().productProperty());

        TableColumn<Sale, Integer> saleQuantityColumn = new TableColumn<>("Quantity");
        saleQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        salesTable.getColumns().addAll(saleProductColumn, saleQuantityColumn);

        TextField saleProductField = new TextField();
        TextField saleQuantityField = new TextField();

        Button sellButton = new Button("Sell");
        sellButton.setOnAction(e -> {
            String productName = saleProductField.getText();
            int quantity = Integer.parseInt(saleQuantityField.getText());

            Product product = findProductByName(productName);
            if (product != null && product.getQuantity() >= quantity) {
                Sale sale = new Sale(product, quantity);
                sales.add(sale);
                product.setQuantity(product.getQuantity() - quantity);

                saleProductField.clear();
                saleQuantityField.clear();
            }
        });

        VBox salesLayout = new VBox();
        salesLayout.setSpacing(10);
        salesLayout.setPadding(new Insets(10));
        salesLayout.getChildren().addAll(salesTable, saleProductField, saleQuantityField, sellButton);

        // Orders screen
        TableView<Order> ordersTable = new TableView<>();
        ordersTable.setItems(FXCollections.observableArrayList(orders));

        TableColumn<Order, String> orderProductColumn = new TableColumn<>("Product");
        orderProductColumn.setCellValueFactory(cellData -> cellData.getValue().productProperty());

        TableColumn<Order, Integer> orderQuantityColumn = new TableColumn<>("Quantity");
        orderQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        ordersTable.getColumns().addAll(orderProductColumn, orderQuantityColumn);

        TextField orderProductField = new TextField();
        TextField orderQuantityField = new TextField();

        Button orderButton = new Button("Place Order");
        orderButton.setOnAction(e -> {
            String productName = orderProductField.getText();
            int quantity = Integer.parseInt(orderQuantityField.getText());

            Product product = findProductByName(productName);
            if (product != null) {
                Order order = new Order(product, quantity);
                orders.add(order);

                orderProductField.clear();
                orderQuantityField.clear();
            }
        });

        VBox ordersLayout = new VBox();
        ordersLayout.setSpacing(10);
        ordersLayout.setPadding(new Insets(10));
        ordersLayout.getChildren().addAll(ordersTable, orderProductField, orderQuantityField, orderButton);

        // Reports screen
        TextArea reportTextArea = new TextArea();
        reportTextArea.setEditable(false);

        Button generateReportButton = new Button("Generate Report");
        generateReportButton.setOnAction(e -> {
            StringBuilder reportBuilder = new StringBuilder();
            reportBuilder.append("Inventory Report:\n");

            for (Product product : inventory) {
                reportBuilder.append(product.getName()).append(": ").append(product.getQuantity()).append("\n");
            }

            reportBuilder.append("\nSales Report:\n");

            for (Sale sale : sales) {
                reportBuilder.append(sale.getProduct()).append(": ").append(sale.getQuantity()).append("\n");
            }

            reportBuilder.append("\nOrders Report:\n");

            for (Order order : orders) {
                reportBuilder.append(order.getProduct()).append(": ").append(order.getQuantity()).append("\n");
            }

            reportTextArea.setText(reportBuilder.toString());
        });

        VBox reportsLayout = new VBox();
        reportsLayout.setSpacing(10);
        reportsLayout.setPadding(new Insets(10));
        reportsLayout.getChildren().addAll(reportTextArea, generateReportButton);

        // Create TabPane for multiple screens
        TabPane tabPane = new TabPane();

        Tab productManagementTab = new Tab("Product Management", productManagementLayout);
        Tab salesTab = new Tab("Sales", salesLayout);
        Tab ordersTab = new Tab("Orders", ordersLayout);
        Tab reportsTab = new Tab("Reports", reportsLayout);

        tabPane.getTabs().addAll(productManagementTab, salesTab, ordersTab, reportsTab);

        // Create layout
        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(tabPane);

        // Create scene
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws IOException {
        // Save inventory to file before closing the application
        saveInventoryToFile();
    }

    private void saveInventoryToFile() throws IOException {
        File file = new File("inventory.txt");
        FileWriter writer = new FileWriter(file);

        for (Product product : inventory) {
            writer.write(product.getName() + "," + product.getPrice() + "," + product.getQuantity() + "\n");
        }

        writer.close();
    }

    private Product findProductByName(String name) {
        for (Product product : inventory) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
}