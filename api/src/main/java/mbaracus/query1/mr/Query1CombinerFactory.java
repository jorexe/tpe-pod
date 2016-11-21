package mbaracus.query1.mr;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import mbaracus.query1.model.AgeCount;
import mbaracus.query1.model.AgeType;

/**
 * Created by jorexe on 21/11/16.
 */
public class Query1CombinerFactory implements CombinerFactory<AgeType, AgeCount, AgeCount> {
    @Override
    public Combiner<AgeCount, AgeCount> newCombiner(AgeType key) {
        return new Combiner<AgeCount, AgeCount>() {

            private int count;

            @Override
            public void combine(AgeCount value) {
                this.count += value.count;
            }

            @Override
            public AgeCount finalizeChunk() {
                return new AgeCount(key, count);
            }

            @Override
            public void reset() {
                this.count = 0;
            }
        };
    }
}
