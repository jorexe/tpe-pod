package mbaracus.query5.mr;


import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import mbaracus.query5.model.DepartmentCount;

/**
 * Created by jorexe on 17/11/16.
 */
public class DepartmentCounterCombinerFactory implements CombinerFactory<String, DepartmentCount, DepartmentCount> {
    @Override
    public Combiner<DepartmentCount, DepartmentCount> newCombiner(String key) {
        return new Combiner<DepartmentCount, DepartmentCount>() {

            private int count;
            private String dep;
            private String prov;

            @Override
            public void combine(DepartmentCount value) {
                this.count += value.count;
                if (dep == null) {
                    this.dep = value.departmentName;
                }
                if (prov == null) {
                    this.prov = value.departmentProvince;
                }
            }

            @Override
            public DepartmentCount finalizeChunk() {
                return new DepartmentCount(dep, prov, count);
            }

            @Override
            public void reset() {
                this.count = 0;
                this.dep = null;
                this.prov = null;
            }
        };
    }
}
