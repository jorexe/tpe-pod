package mbaracus.query2.mr;

import mbaracus.enumerators.HouseType;
import mbaracus.query2.model.HouseTypeMean;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

/**
 * Created by jorexe on 10/11/16.
 */
public class MeanReducerFactory implements ReducerFactory<HouseType, HouseTypeMean, HouseTypeMean> {

    @Override
    public Reducer<HouseTypeMean, HouseTypeMean> newReducer(HouseType key) {
        return new Reducer<HouseTypeMean, HouseTypeMean>() {
            private HouseTypeMean houseTypeMean;

            @Override
            public void beginReduce() {
                this.houseTypeMean = null;
            }

            @Override
            public void reduce(HouseTypeMean value) {
                if (this.houseTypeMean == null) {
                    this.houseTypeMean = value;
                } else {
                    int totalCount = houseTypeMean.count + value.count;
                    houseTypeMean.mean = ((houseTypeMean.mean * houseTypeMean.count) + (value.mean * value.count)) / totalCount;
                    houseTypeMean.count = totalCount;
                }
            }

            @Override
            public HouseTypeMean finalizeReduce() {
                return houseTypeMean;
            }
        };
    }
}
