package repository.user;

import model.User;
import model.validator.Notification;

import java.util.*;

public interface UserRepository {

    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);
    Long findByUsername(String username);
    void removeAll();
    public User findById(Long id);

    boolean existsByUsername(String username);

    boolean removeById(Long id);
}