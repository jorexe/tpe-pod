package mbaracus.query2.mr;

import mbaracus.query2.model.HouseCount;
import mbaracus.query2.model.QueryDataEntry;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by jorexe on 10/11/16.
 */
public class CounterMapperFactory implements Mapper<Long, QueryDataEntry, String, HouseCount> {

    @Override
    public void map(Long key, QueryDataEntry value, Context<String, HouseCount> context) {
        context.emit(value.departmentName, new HouseCount(value.departmentName, 1));
    }

}
