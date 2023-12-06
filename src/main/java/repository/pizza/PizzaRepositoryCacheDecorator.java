package repository.pizza;

import model.Pizza;

import java.util.List;
import java.util.Optional;

public class PizzaRepositoryCacheDecorator extends PizzaRepositoryDecorator{
    private Cache<Pizza> cache;

    public PizzaRepositoryCacheDecorator(PizzaRepository pizzaRepository, Cache<Pizza> cache){
        super(pizzaRepository);
        this.cache = cache;
    }

    @Override
    public List<Pizza> findAll() {
        if (cache.hasResult()){
            return cache.load();
        }

        List<Pizza> pizzas = decoratedRepository.findAll();
        cache.save(pizzas);

        return pizzas;
    }

    @Override
    public Optional<Pizza> findById(Long id) {

        if (cache.hasResult()){
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(Pizza pizza) {
        cache.invalidateCache();
        return decoratedRepository.save(pizza);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public boolean removeById(Long id) {
        cache.invalidateCache();
        return decoratedRepository.removeById(id);
    }

    @Override
    public boolean update(Pizza pizza) {
        cache.invalidateCache();
        return decoratedRepository.update(pizza);
    }
}