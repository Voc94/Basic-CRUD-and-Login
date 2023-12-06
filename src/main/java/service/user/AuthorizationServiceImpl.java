package service.user;

import database.Constants;
import model.Role;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.util.List;

public class AuthorizationServiceImpl implements AuthorizationService {
    UserRepository userRepository;
    RightsRolesRepository rightsRolesRepository;
    public AuthorizationServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository){
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }
    public boolean isUserCustomer(List<Role> userRoles) {
        return userRoles.stream().anyMatch(role -> role.getRole().equals(Constants.Roles.CUSTOMER));
    }
    public boolean isUserEmployee(List<Role> userRoles) {
        return userRoles.stream().anyMatch(role -> role.getRole().equals(Constants.Roles.EMPLOYEE));
    }
    public boolean isUserAdmin(List<Role> userRoles) {
        return userRoles.stream().anyMatch(role -> role.getRole().equals(Constants.Roles.ADMINISTRATOR));
    }
}
