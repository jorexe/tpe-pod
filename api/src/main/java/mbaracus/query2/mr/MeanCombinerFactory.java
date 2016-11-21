package mbaracus.query2.mr;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import mbaracus.enumerators.HouseType;
import mbaracus.query2.model.HouseTypeMean;

/**
 * Created by jorexe on 21/11/16.
 */
public class MeanCombinerFactory implements CombinerFactory<HouseType, HouseTypeMean, HouseTypeMean> {
    @Override
    public Combiner<HouseTypeMean, HouseTypeMean> newCombiner(HouseType key) {
        return new Combiner<HouseTypeMean, HouseTypeMean>() {
            private HouseTypeMean houseTypeMean;

            @Override
            public void combine(HouseTypeMean value) {
                if (this.houseTypeMean == null) {
                    this.houseTypeMean = new HouseTypeMean(value.houseType, value.mean, value.count);
                } else {
                    int totalCount = houseTypeMean.count + value.count;
                    houseTypeMean.mean = ((houseTypeMean.mean * houseTypeMean.count) + (value.mean * value.count)) / totalCount;
                    houseTypeMean.count = totalCount;
                }
            }

            @Override
            public HouseTypeMean finalizeChunk() {
                return houseTypeMean;
            }

            @Override
            public void reset() {
                this.houseTypeMean = null;
            }
        };
    }
}
