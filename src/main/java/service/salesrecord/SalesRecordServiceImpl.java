package service.salesrecord;

import model.Pizza;
import model.SalesRecord;
import repository.pizza.PizzaRepository;
import repository.record.SalesRecordRepository;

import java.util.List;

public class SalesRecordServiceImpl implements SalesRecordService{
    final SalesRecordRepository salesRecordRepository;
    public SalesRecordServiceImpl(SalesRecordRepository salesRecordRepository){
        this.salesRecordRepository = salesRecordRepository;
    }
    @Override
    public List<SalesRecord> findAll() {
        return salesRecordRepository.findAll();
    }
    @Override
    public boolean save(SalesRecord salesRecord) {
        return salesRecordRepository.save(salesRecord);
    }
}
