package poms;

import java.util.List;

public class Dataset extends BaseDTO {
    public List<Record> record;


    public List<Record> getRecord() {
        return record;
    }

    public void setRecord(List<Record> record) {
        this.record = record;
    }
}
