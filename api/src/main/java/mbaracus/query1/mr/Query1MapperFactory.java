package mbaracus.query1.mr;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import mbaracus.tuples.CensoTuple;
import mbaracus.query1.model.AgeCount;
import mbaracus.query1.model.AgeType;

public class Query1MapperFactory implements Mapper<Integer, CensoTuple, AgeType, AgeCount> {

    @Override
    public void map(Integer key, CensoTuple value, Context<AgeType, AgeCount> context) {
        AgeType ageType = AgeType.getAgeTypeByAge(value.getEdad());
        context.emit(ageType, new AgeCount(ageType, 1));
    }
}
