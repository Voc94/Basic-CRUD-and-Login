package repository.record;

import model.Pizza;
import model.SalesRecord;

import java.util.List;
import java.util.Optional;

public interface SalesRecordRepository {
    List<SalesRecord> findAll();
    boolean save(SalesRecord salesRecord);
    void removeAll();

}
