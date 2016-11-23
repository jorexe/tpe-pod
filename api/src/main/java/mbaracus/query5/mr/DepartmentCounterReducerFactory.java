package mbaracus.query5.mr;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import mbaracus.query5.model.DepartmentCount;

public class DepartmentCounterReducerFactory implements ReducerFactory<Integer, DepartmentCount, DepartmentCount> {

    @Override
    public Reducer<DepartmentCount, DepartmentCount> newReducer(Integer key) {
        return new Reducer<DepartmentCount, DepartmentCount>() {
            private int count;
            private String dep;
            private String prov;

            @Override
            public void beginReduce() {
                this.count = 0;
            }

            @Override
            public void reduce(DepartmentCount value) {
                this.count += value.count;
                this.dep = value.departmentName;
                this.prov = value.departmentProvince;
            }

            @Override
            public DepartmentCount finalizeReduce() {
                return new DepartmentCount(this.dep, this.prov, this.count);
            }
        };
    }
}
