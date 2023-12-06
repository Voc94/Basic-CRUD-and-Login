package model;
import model.Pizza;
public class SalesRecord {
    private Long pizza_id;
    private Long employee_id;
    private Long buyer_id;

    public Long getBuyerId() {
        return this.buyer_id;
    }

    public void setBuyerId(Long buyer_id) {
        this.buyer_id = buyer_id;
    }

    public void setEmployeeId(Long employee_id) {
        this.employee_id = employee_id;
    }

    public void setPizza(Long pizza) {
        this.pizza_id = pizza;
    }

    public Long getEmployeeId() {
        return this.employee_id;
    }

    public Long getPizza() {
        return this.pizza_id;
    }
}
