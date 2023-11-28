package repository.pizza;

public abstract class PizzaRepositoryDecorator implements PizzaRepository{
    protected PizzaRepository decoratedRepository;

    public PizzaRepositoryDecorator(PizzaRepository pizzaRepository){
        this.decoratedRepository = pizzaRepository;
    }

}