package mbaracus.query3.mr;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import mbaracus.query3.model.DepartmentStat;

/**
 * Created by jorexe on 21/11/16.
 */
public class AnalfabetCounterCombinerFactory implements CombinerFactory<Integer, DepartmentStat, DepartmentStat> {
    @Override
    public Combiner<DepartmentStat, DepartmentStat> newCombiner(Integer key) {
        return new Combiner<DepartmentStat, DepartmentStat>() {

            private int totalHabitants;
            private int analfabets;
            private String nombreDepto;
            private String nombreProv;

            @Override
            public void combine(DepartmentStat value) {
                totalHabitants += value.getTotalHabitants();
                analfabets += value.getAnalfabetos();
                nombreDepto = value.getNombreDepto();
                nombreProv = value.getNombreProv();
            }

            @Override
            public DepartmentStat finalizeChunk() {
                return new DepartmentStat(analfabets, totalHabitants, nombreDepto, nombreProv);
            }

            @Override
            public void reset() {
                totalHabitants = 0;
                analfabets = 0;
                nombreDepto = null;
                nombreProv = null;
            }
        };
    }
}
