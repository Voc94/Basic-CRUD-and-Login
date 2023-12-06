package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;  // Import this

import model.Pizza;
import repository.pizza.PizzaRepository;
import service.pizza.PizzaService;
import service.pizza.PizzaServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerView {
    private TableView pizzaTable;

    private List<Pizza> pizzaList;
    private GridPane gridPane;

    public CustomerView(Stage primaryStage,List<Pizza> pizzaList) {
        primaryStage.setTitle("Customer View");

        gridPane = new GridPane();
        initializeGridPane(gridPane);
        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);
        this.pizzaList = pizzaList;
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
        pizzaTable = new TableView<Pizza>();
        pizzaTable.setEditable(false);
        pizzaTable.setMinWidth(480);

        TableColumn<Pizza, String> nameColumn = new TableColumn<>("Pizza Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pizza, String> chefColumn = new TableColumn<>("Pizza Chef");
        chefColumn.setCellValueFactory(new PropertyValueFactory<>("chef"));

        TableColumn<Pizza, String> deliveryTimeColumn = new TableColumn<>("Pizza Delivery Time");
        deliveryTimeColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDateTime"));

        pizzaTable.getColumns().addAll(
                (TableColumn<Pizza, String>) nameColumn,
                (TableColumn<Pizza, String>) chefColumn,
                (TableColumn<Pizza, String>) deliveryTimeColumn
        );
        pizzaTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Search box
        TextField searchBox = new TextField();
        searchBox.setPromptText("Search...");
        searchBox.textProperty().addListener((observable, oldValue, newValue) ->
                filterTable(newValue.trim()));

        // Buy button
        Button buyButton = createBuyButton();

        HBox buttonBox = new HBox(buyButton);
        borderPane.setLeft(pizzaTable);
        borderPane.setTop(searchBox);
        borderPane.setRight(buttonBox);

        gridPane.add(borderPane, 0, 1);

        // Fill the table with data
        fillTable(pizzaList);
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
        // Add logic to filter the table based on the search keyword
        // You can use a FilteredList or manipulate the items directly
    }

    private void fillTable(List<Pizza> pizzas) {
        pizzaTable.getItems().setAll(pizzaList);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Scene getScene() {
        return gridPane.getScene();
    }
}
