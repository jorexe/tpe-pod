package mbaracus.query2.mr;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import mbaracus.enumerators.HouseType;
import mbaracus.query2.model.HouseCount;

/**
 * Created by jorexe on 21/11/16.
 */
public class CounterCombinerFactory implements CombinerFactory<Integer, HouseCount, HouseCount> {
    @Override
    public Combiner<HouseCount, HouseCount> newCombiner(Integer key) {
        return new Combiner<HouseCount, HouseCount>() {
            private int count;
            private HouseType tipoHogar;

            @Override
            public void combine(HouseCount value) {
                this.count += value.count;
                this.tipoHogar = value.tipoHogar;
            }

            @Override
            public HouseCount finalizeChunk() {
                return new HouseCount(key, this.count, this.tipoHogar);
            }

            @Override
            public void reset() {
                this.count = 0;
            }
        };
    }
}
