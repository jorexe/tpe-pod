package mbaracus.query3.mr;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import mbaracus.query3.model.DepartmentStat;

public class AnalfabetCounterReducerFactory implements ReducerFactory<Integer, DepartmentStat, DepartmentStat> {
    @Override
    public Reducer<DepartmentStat, DepartmentStat> newReducer(Integer key) {
        return new Reducer<DepartmentStat, DepartmentStat>() {
            private int totalHabitants;
            private int analfabets;
            private String nombreDepto;
            private String nombreProv;

            @Override
            public void beginReduce() {
                totalHabitants = 0;
                analfabets = 0;
            }

            @Override
            public void reduce(DepartmentStat value) {
                totalHabitants += value.getTotalHabitants();
                analfabets += value.getAnalfabetos();
                nombreDepto = value.getNombreDepto();
                nombreProv = value.getNombreProv();
            }

            @Override
            public DepartmentStat finalizeReduce() {
                return new DepartmentStat(analfabets, totalHabitants, nombreDepto, nombreProv);
            }
        };
    }
}
