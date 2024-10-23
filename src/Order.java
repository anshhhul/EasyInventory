import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Order {
    private final ObjectProperty<Product> product;
    private final IntegerProperty quantity;

    public Order(Product product, int quantity) {
        this.product = new SimpleObjectProperty<>(product);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public Product getProduct() {
        return product.get();
    }

    public void setProduct(Product product) {
        this.product.set(product);
    }

    public ObjectProperty<Product> productProperty() {
        return product;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}
