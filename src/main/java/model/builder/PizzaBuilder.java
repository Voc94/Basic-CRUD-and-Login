package model.builder;

import model.Pizza;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PizzaBuilder {

    private Pizza pizza;


    public PizzaBuilder(){
        pizza = new Pizza();
    }

    public PizzaBuilder setId(Long id){
        pizza.setId(id);
        return this;
    }

    public PizzaBuilder setChef(String chef){
        pizza.setChef(chef);
        return this;
    }

    public PizzaBuilder setName(String name){
        pizza.setName(name);
        return this;
    }

    public PizzaBuilder setDeliveryDateTime(LocalDateTime deliveryDateTime){
        pizza.setDeliveyDateTime(deliveryDateTime);
        return this;
    }

    public Pizza build()
    {
        return pizza;
    }


}