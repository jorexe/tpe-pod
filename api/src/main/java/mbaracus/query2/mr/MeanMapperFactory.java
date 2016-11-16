package mbaracus.query2.mr;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import mbaracus.enumerators.HouseType;
import mbaracus.query2.model.HouseCount;
import mbaracus.query2.model.HouseTypeMean;

public class MeanMapperFactory implements Mapper<Integer, HouseCount, HouseType, HouseTypeMean> {

    @Override
    public void map(Integer key, HouseCount value, Context<HouseType, HouseTypeMean> context) {
        context.emit(value.tipoHogar, new HouseTypeMean(value.tipoHogar, value.count, 1));
    }
}
