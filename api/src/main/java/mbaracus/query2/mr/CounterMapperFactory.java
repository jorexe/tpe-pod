package mbaracus.query2.mr;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import mbaracus.tuples.CensoTuple;
import mbaracus.query2.model.HouseCount;

public class CounterMapperFactory implements Mapper<Integer, CensoTuple, Integer, HouseCount> {

    @Override
    public void map(Integer key, CensoTuple value, Context<Integer, HouseCount> context) {
        context.emit(value.getHogarId(), new HouseCount(value.getHogarId(), 1, value.getTipoVivienda()));
    }

}
