package mbaracus.query1.mr;

import mbaracus.query1.model.AgeCount;
import mbaracus.query1.model.AgeType;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * Created by jorexe on 10/11/16.
 */
public class QueryReducerFactory implements ReducerFactory<AgeType, AgeCount, AgeCount> {

    @Override
    public Reducer<AgeCount, AgeCount> newReducer(AgeType key) {
        return new Reducer<AgeCount, AgeCount>() {
            private int count;

            @Override
            public void beginReduce() {
                this.count = 0;
            }

            @Override
            public void reduce(AgeCount value) {
                this.count += value.count;
            }

            @Override
            public AgeCount finalizeReduce() {
                return new AgeCount(key, this.count);
            }
        };
    }
}
