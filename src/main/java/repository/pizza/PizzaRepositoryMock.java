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
}