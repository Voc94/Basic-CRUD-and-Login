package service.pizza;
;
import model.Pizza;

import java.util.List;
import java.util.Optional;

public interface PizzaService {

    List<Pizza> findAll();

    Pizza findById(Long id);

    boolean save(Pizza book);

    int getTimeFromDelivery(Long id);
}
