package mbaracus.query4.mr;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import mbaracus.model.CensoTuple;
import mbaracus.query4.model.Department;

public class ProvinceCounterMapperFactory implements Mapper<Integer, CensoTuple, Integer, Department> {
    @Override
    public void map(Integer key, CensoTuple value, Context<Integer, Department> context) {
        Department department = new Department(value.getNombreprov(), value.getNombredepto(), 1);
        context.emit(department.hashCode(), department);
    }
}
