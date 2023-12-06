package service.pizza;

import model.Pizza;
import repository.pizza.PizzaRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PizzaServiceImpl implements PizzaService{

    final PizzaRepository pizzaRepository;

    public PizzaServiceImpl(PizzaRepository pizzaRepository){
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    @Override
    public Pizza findById(Long id) {
        return pizzaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pizza with id: %d not found".formatted(id)));
    }

    @Override
    public boolean save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public boolean removeById(Long id) {
        return pizzaRepository.removeById(id);
    }
    public boolean update(Pizza pizza){
        return pizzaRepository.update(pizza);
    }
    @Override
    public int getTimeFromDelivery(Long id) {
        Pizza pizza = this.findById(id);

        LocalDateTime now = LocalDateTime.now();

        return (int)ChronoUnit.MINUTES.between(pizza.getDeliveryDateTime(), now);
    }
}