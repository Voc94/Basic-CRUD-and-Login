package repository.pizza;

import model.Pizza;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository {
    List<Pizza> findAll();

    Optional<Pizza> findById(Long id);

    boolean save(Pizza pizza);

    void removeAll();

}
