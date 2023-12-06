package service.user;

import database.Constants;
import model.Role;

import java.util.List;

public interface AuthorizationService {
     boolean isUserCustomer(List<Role> userRoles);
     boolean isUserEmployee(List<Role> userRoles);

     boolean isUserAdmin(List<Role> userRoles);
}
