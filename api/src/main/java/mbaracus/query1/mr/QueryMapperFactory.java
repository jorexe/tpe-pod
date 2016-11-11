package mbaracus.query1.mr;

import mbaracus.query1.model.AgeCount;
import mbaracus.query1.model.AgeType;
import mbaracus.query1.model.QueryDataEntry;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by jorexe on 10/11/16.
 */
public class QueryMapperFactory implements Mapper<Long, QueryDataEntry, AgeType, AgeCount> {

    @Override
    public void map(Long key, QueryDataEntry value, Context<AgeType, AgeCount> context) {
        AgeType ageType = AgeType.getAgeTypeByAge(value.age);
        context.emit(ageType, new AgeCount(ageType, 1));
    }
}
