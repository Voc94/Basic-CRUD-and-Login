package controller;

import database.Constants;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import launcher.ComponentFactory;
import model.Role;
import model.User;
import model.validator.Notification;
import repository.pizza.PizzaRepository;
import service.pizza.PizzaServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthorizationService;
import service.user.AuthorizationServiceImpl;
import view.LoginView;

import java.util.List;

public class LoginController {
    private final Stage stage;
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final ComponentFactory componentFactory;
    private final AuthorizationServiceImpl authorizationService;
    public LoginController(LoginView loginView, AuthenticationService authenticationService, AuthorizationServiceImpl authorizationService, Stage stage, ComponentFactory componentFactory) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
        this.stage = stage;
        this.componentFactory = componentFactory;
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
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event1 -> {
                    List<Role> userRoles = loginNotification.getResult().getRoles();
                    if (authorizationService.isUserCustomer(userRoles)) {
                        CustomerController customerController = new CustomerController(stage, componentFactory.getPizzaService());
                    }
                    else if (authorizationService.isUserEmployee(userRoles)) {
                        EmployeeController employeeController = new EmployeeController(stage, componentFactory.getPizzaService(),componentFactory.getSalesRecordService(),authenticationService.findByUsername(username));
                    }
                    else if (authorizationService.isUserAdmin(userRoles)) {
                        AdminController adminController = new AdminController(stage,componentFactory.getUserRepository());
                    }
                });
                delay.play();
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