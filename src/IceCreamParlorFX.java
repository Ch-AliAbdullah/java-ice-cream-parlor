import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

// Interface for the price calculation
interface Icecreamable {
    double calculatePrice();
}

// Abstract base class
abstract class IceCream implements Icecreamable {
    String flavor;
    String size;
    public IceCream(String flavor, String size) {
        this.flavor = flavor;
        this.size = size;
    }
    @Override
    public String toString() {
        return flavor + " (" + size + ")";
    }
}

// Concrete Cup class
class Cup extends IceCream {
    public Cup(String flavor, String size) { super(flavor, size); }
    @Override
    public double calculatePrice() {
        return switch (size) {
            case "Small" -> 2.00;
            case "Large" -> 5.00;
            default -> 3.50;
        };
    }
}

// Concrete Cone class
class Cone extends IceCream {
    String type;

    public Cone(String flavor, String size, String type) {
        super(flavor, size);
        this.type = type;
    }
    public String getTypeLabel() {
        return type.equalsIgnoreCase("w") ? "Waffle" : "Plain";
    }
    @Override
    public double calculatePrice() {
        double price = switch (size) {
            case "Small" -> 2.50;
            case "Large" -> 5.50;
            default -> 4.00;
        };
        if (type.equalsIgnoreCase("w")) price += 10.00;
        else if (type.equalsIgnoreCase("p")) price += 5.00;
        return price;
    }

    @Override
    public String toString() {
        return flavor + " (" + size + ", " + getTypeLabel() + ")";
    }
}
// for managing and storing multiple orders
class Order {
    private ArrayList<IceCream> iceCreams = new ArrayList<>();

    public void addIceCream(IceCream iceCream) {
        iceCreams.add(iceCream);
    }

    public double calculateTotalPrice() {
        return iceCreams.stream().mapToDouble(IceCream::calculatePrice).sum();
    }

    public void saveOrderToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (IceCream i : iceCreams) {
                writer.write(i.toString());
                writer.newLine();
            }
            writer.write("Total: $" + calculateTotalPrice());
            writer.newLine();
            writer.write("---------");
            writer.newLine();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public String getOrderSummary() {
        StringBuilder sb = new StringBuilder();
        for (IceCream i : iceCreams) {
            sb.append(i.toString()).append("\n");
        }
        sb.append("Total: $").append(String.format("%.2f", calculateTotalPrice()));
        return sb.toString();
    }
}
// Main JavaFX Application

public class IceCreamParlorFX extends Application {

            @Override
            public void start(Stage stage) throws Exception {

                FXMLLoader loader =
                        new FXMLLoader(
                                getClass().getResource("icecream-view.fxml")
                        );

                Scene scene =
                        new Scene(loader.load());

                stage.setTitle("Ice Cream Parlor FX");

                stage.setScene(scene);

                stage.show();
            }

            public static void main(String[] args) {
                launch();
            }
        }