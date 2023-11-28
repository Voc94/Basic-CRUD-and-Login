package controller;

import javafx.stage.Stage;
import repository.pizza.PizzaRepository;
import view.CustomerView;

public class CustomerController {
    private final CustomerView customerView;
    public CustomerController(Stage stage,PizzaRepository pizzaRepository) {
        this.customerView = new CustomerView(stage, pizzaRepository);
    }
    public void switchToCustomerView(Stage stage, PizzaRepository pizzaRepository) {
        CustomerView customerView = new CustomerView(stage,pizzaRepository);  // Use the same stage
        stage.setScene(customerView.getScene());
    }
    public CustomerView getCustomerView() {
        return customerView;
    }

}