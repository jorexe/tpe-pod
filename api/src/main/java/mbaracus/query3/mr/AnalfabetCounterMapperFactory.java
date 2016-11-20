package mbaracus.query3.mr;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import mbaracus.tuples.CensoTuple;
import mbaracus.query3.model.DepartmentStat;

public class AnalfabetCounterMapperFactory implements Mapper<Integer, CensoTuple, Integer, DepartmentStat> {
    @Override
    public void map(Integer key, CensoTuple value, Context<Integer, DepartmentStat> context) {
        DepartmentStat depto = new DepartmentStat(value.getAlfabetismo() == 2 ? 1 : 0, 1, value.getNombredepto(), value.getNombreprov());
        context.emit(depto.hashCode(), depto);
    }
}
