package ar.itba.edu.mbaracus.query2.mr;

import ar.itba.edu.mbaracus.query2.model.HouseCount;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * Created by jorexe on 10/11/16.
 */
public class CounterReducerFactory implements ReducerFactory<String, HouseCount, HouseCount> {

    @Override
    public Reducer<HouseCount, HouseCount> newReducer(String key) {
        return new Reducer<HouseCount, HouseCount>() {
            private int count;

            @Override
            public void beginReduce() {
                this.count = 0;
            }

            @Override
            public void reduce(HouseCount value) {
                this.count += value.count;
            }

            @Override
            public HouseCount finalizeReduce() {
                return new HouseCount(key, this.count);
            }
        };
    }
}