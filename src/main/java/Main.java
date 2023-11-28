import controller.LoginController;
import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Pizza;
import model.builder.PizzaBuilder;
import model.validator.UserValidator;
import repository.pizza.PizzaRepository;
import repository.pizza.PizzaRepositoryCacheDecorator;
import repository.pizza.PizzaRepositoryMySQL;
import repository.pizza.Cache;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.pizza.PizzaService;
import service.pizza.PizzaServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.LoginView;

import java.sql.Connection;
import java.time.LocalDate;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository,
                rightsRolesRepository);

        final LoginView loginView = new LoginView(primaryStage);

        final UserValidator userValidator = new UserValidator(userRepository);

        new LoginController(loginView, authenticationService, userValidator);
    }
}