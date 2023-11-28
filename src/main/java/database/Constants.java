package database;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.BUY_PIZZA;
import static database.Constants.Rights.CREATE_PIZZA;
import static database.Constants.Rights.DELETE_PIZZA;
import static database.Constants.Rights.RETURN_PIZZA;
import static database.Constants.Rights.RIGHTS;
import static database.Constants.Rights.SELL_PIZZA;
import static database.Constants.Rights.UPDATE_PIZZA;
import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Roles.ROLES;
public class Constants {

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(CREATE_PIZZA, DELETE_PIZZA, UPDATE_PIZZA, SELL_PIZZA));

        rolesRights.get(CUSTOMER).addAll(Arrays.asList(SELL_PIZZA, BUY_PIZZA, RETURN_PIZZA));

        return rolesRights;
    }

    public static class Schemas {
        public static final String TEST = "test_library";
        public static final String PRODUCTION = "library";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String PIZZA = "pizza";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE,
                PIZZA};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";
        public static final String CUSTOMER = "customer";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CUSTOMER};
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        public static final String CREATE_PIZZA = "create_pizza";
        public static final String DELETE_PIZZA = "delete_pizza";
        public static final String UPDATE_PIZZA = "update_pizza";

        public static final String SELL_PIZZA = "sell_pizza";
        public static final String BUY_PIZZA = "buy_pizza";
        public static final String RETURN_PIZZA = "return_pizza";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_PIZZA,
                DELETE_PIZZA, UPDATE_PIZZA, SELL_PIZZA, BUY_PIZZA, RETURN_PIZZA};
    }
}
