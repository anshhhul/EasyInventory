/**
 * Sample Skeleton for 'SalesScreen.fxml' Controller Class
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SalesScreen {

    @FXML // fx:id="btnSell"
    private Button btnSell; // Value injected by FXMLLoader

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

    // ObservableList for storing sales
    private ObservableList<Sale> sales = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up columns for the TableView
        name.setCellValueFactory(cellData -> cellData.getValue().productProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Set the items for the TableView
        tableView.setItems(sales);
    }

    @FXML
    private void sellButtonClicked() {
        String productName = enterName.getText();
        int saleQuantity = Integer.parseInt(enterQuantity.getText());

        Product product = findProductByName(productName);
        if (product != null && product.getQuantity() >= saleQuantity) {
            Sale sale = new Sale(product, saleQuantity);
            sales.add(sale);
            product.setQuantity(product.getQuantity() - saleQuantity);

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

