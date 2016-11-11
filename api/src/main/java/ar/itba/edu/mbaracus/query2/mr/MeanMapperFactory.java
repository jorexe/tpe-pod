package ar.itba.edu.mbaracus.query2.mr;

import ar.itba.edu.mbaracus.query2.model.HouseCount;
import ar.itba.edu.mbaracus.query2.model.QueryDataEntry;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

/**
 * Created by jorexe on 10/11/16.
 */
public class MeanMapperFactory implements Mapper<Long, QueryDataEntry, String, HouseCount> {

    @Override
    public void map(Long key, QueryDataEntry value, Context<String, HouseCount> context) {
        context.emit(value.departmentName, new HouseCount(value.departmentName, 1));
    }

}
