package model.builder;

import model.Pizza;
import model.SalesRecord;
import repository.record.SalesRecordRepository;

import java.time.LocalDateTime;

public class SalesRecordBuilder {

    private SalesRecord salesRecord;


    public SalesRecordBuilder(){
        salesRecord = new SalesRecord();
    }

    public SalesRecordBuilder setBuyer(Long id){
        salesRecord.setBuyerId(id);
        return this;
    }

    public SalesRecordBuilder setPizzaId(Long pizza){
        salesRecord.setPizza(pizza);
        return this;
    }
    public SalesRecordBuilder setEmployeeId (Long id){
        salesRecord.setEmployeeId(id);
        return this;
    }

    public SalesRecord build()
    {
        return salesRecord;
    }


}
