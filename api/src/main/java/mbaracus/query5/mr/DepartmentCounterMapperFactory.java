package mbaracus.query5.mr;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import mbaracus.model.CensoTuple;
import mbaracus.query5.model.DepartmentCount;

public class DepartmentCounterMapperFactory implements Mapper<Integer, CensoTuple, String, DepartmentCount> {

    @Override
    public void map(Integer key, CensoTuple value, Context<String, DepartmentCount> context) {
        context.emit(value.getNombredepto(), new DepartmentCount(1, value.getNombredepto()));
    }

}
