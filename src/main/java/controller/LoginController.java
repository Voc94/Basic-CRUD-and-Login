package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.User;
import model.validator.Notification;
import repository.pizza.PizzaRepository;
import service.user.AuthenticationService;
import view.LoginView;

public class LoginController {
    private final Stage stage;
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final PizzaRepository pizzaRepository;
    public LoginController(LoginView loginView, AuthenticationService authenticationService, Stage stage, PizzaRepository pizzaRepository) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.stage = stage;
        this.pizzaRepository = pizzaRepository;
        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }



    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("Log In Successfull!");
                CustomerController customerController = new CustomerController(stage,pizzaRepository);
                customerController.switchToCustomerView(stage,pizzaRepository);
            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username,password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register Successful!");
            }
        }
    }
}