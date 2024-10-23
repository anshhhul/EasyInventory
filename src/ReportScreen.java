/**
 * Sample Skeleton for 'ReportScreen.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ReportScreen {

    @FXML // fx:id="generateReport"
    private Button generateReport; // Value injected by FXMLLoader

    @FXML // fx:id="reportInfo"
    private TextArea reportInfo; // Value injected by FXMLLoader

    @FXML
    private void initialize() {
        // Nothing specific needs to be done here for initialization
    }

    @FXML
    private void generateReportButtonClicked() {
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

        reportInfo.setText(reportBuilder.toString());
    }
}
