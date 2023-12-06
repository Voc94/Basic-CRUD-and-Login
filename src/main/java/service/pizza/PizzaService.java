package service.pizza;
;
import model.Pizza;

import java.util.List;
import java.util.Optional;

public interface PizzaService {

    List<Pizza> findAll();

    Pizza findById(Long id);
    boolean removeById(Long id);
    boolean update(Pizza pizza);
    boolean save(Pizza pizza);

    int getTimeFromDelivery(Long id);
}
