package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Pizza;
import repository.pizza.PizzaRepository;

public class CustomerView{

    private TableView<Pizza> pizzaTable;
    private GridPane gridPane;
    public CustomerView(Stage primaryStage, PizzaRepository pizzaRepository) {
        primaryStage.setTitle("Customer View");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);
        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        initializeTableView(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initializeTableView(GridPane gridPane) {
        BorderPane borderPane = new BorderPane();

        // Create table
        pizzaTable = new TableView<>();
        pizzaTable.setEditable(false);
        pizzaTable.setMinWidth(480);
        TableColumn<Pizza, String> nameColumn = new TableColumn<>("Pizza Name");

        TableColumn<Pizza, String> chefColumn = new TableColumn<>("Pizza Chef");

        TableColumn<Pizza, String> deliveryTimeColumn = new TableColumn<>("Pizza Delivery Time");

        pizzaTable.getColumns().addAll(nameColumn, chefColumn, deliveryTimeColumn);
        pizzaTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        //search box
        TextField searchBox = new TextField();
        searchBox.setPromptText("Search...");
        searchBox.textProperty().addListener((observable, oldValue, newValue) ->
                filterTable(newValue.trim()));

        //Buy button
        Button buyButton = createBuyButton();

        HBox buttonBox = new HBox(buyButton);
        borderPane.setLeft(pizzaTable);
        borderPane.setTop(searchBox);
        borderPane.setRight(buttonBox);

        gridPane.add(borderPane, 0, 1);
    }


    private Button createBuyButton() {
        Button buyButton = new Button("Buy");
        buyButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        buyButton.setOnAction(event -> {
            buyButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        });
        return buyButton;
    }

    private void filterTable(String keyword) {
    }
    public GridPane getGridPane() {
        return gridPane;
    }
    public Scene getScene() {
        return gridPane.getScene();
    }

}
