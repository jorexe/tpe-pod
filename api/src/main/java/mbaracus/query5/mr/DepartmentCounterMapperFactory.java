package mbaracus.query5.mr;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import mbaracus.tuples.CensoTuple;
import mbaracus.query5.model.DepartmentCount;

public class DepartmentCounterMapperFactory implements Mapper<Integer, CensoTuple, Integer, DepartmentCount> {

    @Override
    public void map(Integer key, CensoTuple value, Context<Integer, DepartmentCount> context) {
        context.emit(value.hashCode(), new DepartmentCount(value.getNombredepto(), value.getNombreprov(), 1));
    }

}
