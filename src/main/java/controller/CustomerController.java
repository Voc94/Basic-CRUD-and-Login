package controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Pizza;
import repository.pizza.PizzaRepository;
import service.pizza.PizzaService;
import service.pizza.PizzaServiceImpl;
import view.CustomerView;

import java.util.List;

public class CustomerController {
    private final CustomerView customerView;
    public CustomerController(Stage stage, PizzaServiceImpl pizzaService) {
        this.customerView = new CustomerView(stage,this.findPizzaList(pizzaService));
        stage.setScene(customerView.getScene());
    }
    List<Pizza> findPizzaList(PizzaService pizzaService){
        return pizzaService.findAll();
    }
    public CustomerView getCustomerView() {
        return customerView;
    }

}