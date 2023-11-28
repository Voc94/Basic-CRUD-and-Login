package launcher;

import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import repository.pizza.PizzaRepository;
import repository.pizza.PizzaRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.CustomerView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final PizzaRepository pizzaRepository;
    private static volatile ComponentFactory instance;
    private CustomerView customerView;  // Declare CustomerView as an instance variable

    public static ComponentFactory getInstance(Boolean componentsForTests, Stage stage) {
        if (instance == null) {
            synchronized (ComponentFactory.class) {
                if (instance == null) {
                    instance = new ComponentFactory(componentsForTests, stage);
                }
            }
        }
        return instance;
    }

    public ComponentFactory(Boolean componentsForTests, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        this.loginView = new LoginView(stage);
        this.pizzaRepository = new PizzaRepositoryMySQL(connection);
        this.loginController = new LoginController(loginView, authenticationService,stage,pizzaRepository); // TO DO TOMMOROW MAKE A VIEWSWITCHER&redo it using the layered architecture...:/ mi somn

        // Don't create CustomerView here; create it only after successful authentication
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public PizzaRepository getPizzaRepository() {
        return pizzaRepository;
    }

    public LoginController getLoginController() {
        return loginController;
    }
}
