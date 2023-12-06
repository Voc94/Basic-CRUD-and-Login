package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Pizza;
import model.User;

import java.util.List;

public class AdminView {
    private TableView<User> employeeTable;
    private List<User> employee;
    private GridPane gridPane;
    private TextField idField;
    private TextField nameField;
    private TextField chefField;
    private TextField deliveryDateTimeField;

    private Button createButton;
    private Button retrieveButton;
    private Button updateButton;
    private Button deleteButton;
    private Button generateReportButton;
    private Button sellButton;

    public AdminView(Stage primaryStage, List<User> employee) {
        primaryStage.setTitle("Employee View");

        gridPane = new GridPane();
        initializeGridPane(gridPane);
        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);
        this.employee = employee;
        initializeTableView(gridPane);
        initializeFields(gridPane);
        initializeButtons(gridPane);

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

        this.setEmployeeTable(employee);

        // Search box
        TextField searchBox = new TextField();
        searchBox.setPromptText("Search...");
        searchBox.textProperty().addListener((observable, oldValue, newValue) ->
                filterTable(newValue.trim()));

        borderPane.setLeft(employeeTable);
        borderPane.setTop(searchBox);

        gridPane.add(borderPane, 0, 1);
    }

    private void initializeFields(GridPane gridPane) {
        idField = new TextField();
        idField.setPromptText("ID");

        nameField = new TextField();
        nameField.setPromptText("Name");

        chefField = new TextField();
        chefField.setPromptText("Chef");

        deliveryDateTimeField = new TextField();
        deliveryDateTimeField.setPromptText("Delivery DateTime");

        gridPane.add(new Label("ID:"), 0, 2);
        gridPane.add(idField, 1, 2);
        gridPane.add(new Label("Name:"), 0, 3);
        gridPane.add(nameField, 1, 3);
        gridPane.add(new Label("Chef:"), 0, 4);
        gridPane.add(chefField, 1, 4);
        gridPane.add(new Label("Delivery DateTime:"), 0, 5);
        gridPane.add(deliveryDateTimeField, 1, 5);
    }

    private void initializeButtons(GridPane gridPane) {
        createButton = new Button("Create");

        retrieveButton = new Button("Retrieve");

        updateButton = new Button("Update");

        deleteButton = new Button("Delete");

        generateReportButton = new Button("Generate Report");

        sellButton = new Button(("Sell"));
        HBox buttonBox = new HBox(10, createButton, retrieveButton, updateButton, deleteButton, generateReportButton,sellButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 6, 2, 1);
    }

    private void filterTable(String keyword) {
        // Add logic to filter the table based on the search keyword
        // You can use a FilteredList or manipulate the items directly
    }

    private void fillTable(List<User> employee) {
        employeeTable.getItems().setAll(employee);
    }


    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonListener) {
        createButton.setOnAction(createButtonListener);
    }

    public void addRetrieveButtonListener(EventHandler<ActionEvent> retrieveButtonListener) {
        retrieveButton.setOnAction(retrieveButtonListener);
    }

    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonListener) {
        updateButton.setOnAction(updateButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addGenerateReportButtonListener(EventHandler<ActionEvent> generateReportButtonListener) {
        generateReportButton.setOnAction(generateReportButtonListener);
    }
    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonListener) {
        sellButton.setOnAction(sellButtonListener);
    }
    public Scene getScene() {
        return gridPane.getScene();
    }
    public TextField getIdField() {
        return idField;
    }

    public TextField getNameField() {
        return nameField;
    }
    public User getSelectedPizza() {
        return employeeTable.getSelectionModel().getSelectedItem();
    }
    public TextField getChefField() {
        return chefField;
    }

    public void setEmployeeTable(List<User> userList) {
            // Create table
            employeeTable = new TableView<>();
            employeeTable.setEditable(true);
            employeeTable.setMinWidth(480);

            TableColumn<User, Long> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<User, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

            TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
            passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

            employeeTable.getColumns().addAll(idColumn, nameColumn, passwordColumn);
            employeeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

            // Set table items
            fillTable(userList);
        }
}
