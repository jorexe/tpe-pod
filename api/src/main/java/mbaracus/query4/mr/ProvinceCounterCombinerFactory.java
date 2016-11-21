package mbaracus.query4.mr;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import mbaracus.query4.model.Department;

/**
 * Created by jorexe on 21/11/16.
 */
public class ProvinceCounterCombinerFactory implements CombinerFactory<Integer, Department, Department> {
    @Override
    public Combiner<Department, Department> newCombiner(Integer key) {
        return new Combiner<Department, Department>() {
            private int totalHabitants;
            private String nombreProv;
            private String nombreDpto;

            @Override
            public void combine(Department value) {
                if (nombreProv == null) {
                    this.nombreProv = value.getNombreProv();
                }
                if (nombreDpto == null) {
                    this.nombreDpto = value.getNombreDpto();
                }
                totalHabitants++;
            }

            @Override
            public Department finalizeChunk() {
                return new Department(nombreProv, nombreDpto, totalHabitants);
            }

            @Override
            public void reset() {
                this.totalHabitants = 0;
                this.nombreProv = null;
                this.nombreDpto = null;
            }
        };
    }
}
