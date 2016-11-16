package mbaracus.query2.mr;

import mbaracus.enumerators.HouseType;
import mbaracus.query2.model.HouseCount;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class CounterReducerFactory implements ReducerFactory<Integer, HouseCount, HouseCount> {

    @Override
    public Reducer<HouseCount, HouseCount> newReducer(Integer key) {
        return new Reducer<HouseCount, HouseCount>() {
            private int count;
            private HouseType tipoHogar;

            @Override
            public void beginReduce() {
                this.count = 0;
            }

            @Override
            public void reduce(HouseCount value) {
                this.count += value.count;
                this.tipoHogar = value.tipoHogar;
            }

            @Override
            public HouseCount finalizeReduce() {
                return new HouseCount(key, this.count, this.tipoHogar);
            }
        };
    }
}
