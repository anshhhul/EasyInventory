/**
 * Sample Skeleton for 'OrderScreen.fxml' Controller Class
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class OrderScreen {

    @FXML // fx:id="btnOrder"
    private Button btnOrder; // Value injected by FXMLLoader

    @FXML // fx:id="enterName"
    private TextField enterName; // Value injected by FXMLLoader

    @FXML // fx:id="enterQuantity"
    private TextField enterQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TableColumn<?, ?> name; // Value injected by FXMLLoader

    @FXML // fx:id="quantity"
    private TableColumn<?, ?> quantity; // Value injected by FXMLLoader

    @FXML // fx:id="tableView"
    private TableView<?> tableView; // Value injected by FXMLLoader

    // ObservableList for storing orders
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up columns for the TableView
        name.setCellValueFactory(cellData -> cellData.getValue().productProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Set the items for the TableView
        tableView.setItems(orders);
    }

    @FXML
    private void orderButtonClicked() {
        String productName = enterName.getText();
        int orderQuantity = Integer.parseInt(enterQuantity.getText());

        Product product = findProductByName(productName);
        if (product != null) {
            Order order = new Order(product, orderQuantity);
            orders.add(order);

            enterName.clear();
            enterQuantity.clear();
        }
    }

    // Add a method to find a product by its name
    private Product findProductByName(String productName) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }
}
