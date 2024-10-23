/**
 * Sample Skeleton for 'ProductManagement.fxml' Controller Class
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductManagementController {

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="enterName"
    private TextField enterName; // Value injected by FXMLLoader

    @FXML // fx:id="enterPrice"
    private TextField enterPrice; // Value injected by FXMLLoader

    @FXML // fx:id="enterQuantity"
    private TextField enterQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="name"
    private TableColumn<?, ?> name; // Value injected by FXMLLoader

    @FXML // fx:id="price"
    private TableColumn<?, ?> price; // Value injected by FXMLLoader

    @FXML // fx:id="quantity"
    private TableColumn<?, ?> quantity; // Value injected by FXMLLoader

    @FXML // fx:id="tableView"
    private TableView<?> tableView; // Value injected by FXMLLoader

    // ObservableList for storing products
    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up columns for the TableView
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Set the items for the TableView
        tableView.setItems(products);
    }

    @FXML
    private void addButtonClicked() {
        String productName = enterName.getText();
        double productPrice = Double.parseDouble(enterPrice.getText());
        int productQuantity = Integer.parseInt(enterQuantity.getText());

        Product product = new Product(productName, productPrice, productQuantity);
        products.add(product);

        enterName.clear();
        enterPrice.clear();
        enterQuantity.clear();
    }

}

