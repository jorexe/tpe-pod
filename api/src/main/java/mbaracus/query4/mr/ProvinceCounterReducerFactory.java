package mbaracus.query4.mr;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import mbaracus.query4.model.Department;

public class ProvinceCounterReducerFactory implements ReducerFactory<Integer, Department, Department> {
    @Override
    public Reducer<Department, Department> newReducer(Integer key) {
        return new Reducer<Department, Department>() {
            private int totalHabitants;
            private String nombreProv;
            private String nombreDpto;

            @Override
            public void beginReduce() {
                this.totalHabitants = 0;
            }

            @Override
            public void reduce(Department value) {
                if (nombreProv == null) {
                    this.nombreProv = value.getNombreProv();
                }
                if (nombreDpto == null) {
                    this.nombreDpto = value.getNombreDpto();
                }
                totalHabitants++;
            }

            @Override
            public Department finalizeReduce() {
                return new Department(nombreProv, nombreDpto, totalHabitants);
            }
        };
    }
}
