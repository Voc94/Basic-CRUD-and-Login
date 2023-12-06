package launcher;

import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import model.User;
import repository.pizza.Cache;
import repository.pizza.PizzaRepository;
import repository.pizza.PizzaRepositoryCacheDecorator;
import repository.pizza.PizzaRepositoryMySQL;
import repository.record.SalesRecordRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.pizza.PizzaService;
import service.pizza.PizzaServiceImpl;
import service.salesrecord.SalesRecordService;
import service.salesrecord.SalesRecordServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import service.user.AuthorizationService;
import service.user.AuthorizationServiceImpl;
import view.CustomerView;
import view.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthenticationService authenticationService;
    private final PizzaServiceImpl pizzaService;
    private final SalesRecordService salesRecordService;
    private final UserRepositoryMySQL userRepository;
    private static volatile ComponentFactory instance;
    private CustomerView customerView;  // Declare CustomerView as an instance variable
    private final AuthorizationServiceImpl authorizationService;
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
        PizzaRepository pizzaRepository = new PizzaRepositoryCacheDecorator(
                new PizzaRepositoryMySQL(connection),
                new Cache<>()
        );
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.salesRecordService = new SalesRecordServiceImpl(new SalesRecordRepositoryMySQL(connection));
        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        this.authorizationService = new AuthorizationServiceImpl(userRepository,rightsRolesRepository);
        this.loginView = new LoginView(stage);
        this.pizzaService = new PizzaServiceImpl(pizzaRepository);
        this.loginController = new LoginController(loginView, authenticationService,authorizationService,stage,this);

        // nu fac aici CustomerView-u
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public AuthorizationServiceImpl getAuthorizationService() {
        return authorizationService;
    }

    public PizzaServiceImpl getPizzaService() {
        return pizzaService;
    }
    public SalesRecordService getSalesRecordService() {return salesRecordService;}
    public UserRepositoryMySQL getUserRepository(){
        return userRepository;
    }
    public LoginController getLoginController() {
        return loginController;
    }
}
