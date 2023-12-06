package repository.pizza;

import model.Pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PizzaRepositoryMock implements PizzaRepository{

    private List<Pizza> pizzas;

    public PizzaRepositoryMock(){
        pizzas = new ArrayList<>();
    }

    @Override
    public List<Pizza> findAll() {
        return pizzas;
    }

    @Override
    public Optional<Pizza> findById(Long id) {
        return pizzas.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Pizza book) {
        return pizzas.add(book);
    }

    @Override
    public void removeAll() {
        pizzas.clear();
    }

    @Override
    public boolean update(Pizza pizza) {
        Optional<Pizza> existingPizza = findById(pizza.getId());

        if (existingPizza.isPresent()) {
            // Remove the existing pizza
            pizzas.remove(existingPizza.get());

            // Add the updated pizza
            pizzas.add(pizza);

            return true; // Update successful
        } else {
            return false; // Pizza with the given ID not found
        }
    }

    @Override
    public boolean removeById(Long id) {
        Pizza pizzaToRemove = findById(id).orElse(null);
        return pizzaToRemove != null && pizzas.remove(pizzaToRemove);
    }
}