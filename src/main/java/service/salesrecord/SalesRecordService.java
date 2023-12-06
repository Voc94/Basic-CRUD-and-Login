package service.salesrecord;

import model.Pizza;
import model.SalesRecord;

import java.util.List;

public interface SalesRecordService {
    List<SalesRecord> findAll();
    boolean save(SalesRecord salesRecord);
}
