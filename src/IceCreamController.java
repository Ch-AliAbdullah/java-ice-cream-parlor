import javafx.fxml.FXML;
import javafx.scene.control.*;

public class IceCreamController {

    @FXML
    private ComboBox<String> baseSelection;

    @FXML
    private ComboBox<String> sizeSelection;

    @FXML
    private ComboBox<String> coneTypeSelection;

    @FXML
    private CheckBox chocolateCB;

    @FXML
    private CheckBox strawberryCB;

    @FXML
    private CheckBox vanillaCB;

    @FXML
    private CheckBox cookiesCB;

    @FXML
    private TextArea billDisplay;

    @FXML
    private Button orderBtn;

    @FXML
    public void initialize() {

        // Fill combo boxes
        baseSelection.getItems().addAll("Cone", "Cup");

        sizeSelection.getItems().addAll(
                "Small",
                "Medium",
                "Large"
        );

        coneTypeSelection.getItems().addAll(
                "Plain",
                "Waffle"
        );

        sizeSelection.setValue("Medium");
        coneTypeSelection.setValue("Plain");

        // Disable cone type for cup
        baseSelection.setOnAction(e -> {

            String selected = baseSelection.getValue();

            coneTypeSelection.setDisable(
                    selected != null &&
                            selected.equals("Cup")
            );
        });


        orderBtn.setOnAction(e -> placeOrder());
    }

    private void placeOrder() {

        Order myOrder = new Order();

        String base = baseSelection.getValue();
        String selectedSize = sizeSelection.getValue();
        String coneType = coneTypeSelection.getValue();

        if (base == null) {
            billDisplay.setText("Please select a base!");
            return;
        }

        // Base item
        if (base.equals("Cone")) {

            String typeCode =
                    coneType.equalsIgnoreCase("Waffle")
                            ? "w" : "p";

            myOrder.addIceCream(
                    new Cone(
                            "Base (" + base + ")",
                            selectedSize,
                            typeCode
                    )
            );

        } else {

            myOrder.addIceCream(
                    new Cup(
                            "Base (" + base + ")",
                            selectedSize
                    )
            );
        }

        // Flavors
        if (chocolateCB.isSelected()) {
            myOrder.addIceCream(
                    new IceCream("Chocolate", selectedSize) {
                        public double calculatePrice() {
                            return 2.0;
                        }
                    });
        }

        if (strawberryCB.isSelected()) {
            myOrder.addIceCream(
                    new IceCream("Strawberry", selectedSize) {
                        public double calculatePrice() {
                            return 2.5;
                        }
                    });
        }

        if (vanillaCB.isSelected()) {
            myOrder.addIceCream(
                    new IceCream("Vanilla", selectedSize) {
                        public double calculatePrice() {
                            return 1.5;
                        }
                    });
        }

        if (cookiesCB.isSelected()) {
            myOrder.addIceCream(
                    new IceCream("Cookies", selectedSize) {
                        public double calculatePrice() {
                            return 3.0;
                        }
                    });
        }

        // Displaying bill
        billDisplay.setText(
                myOrder.getOrderSummary()
        );

        // Saving order
        myOrder.saveOrderToFile("orders.txt");
    }
}