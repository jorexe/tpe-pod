package mbaracus.query4.mr;

import com.hazelcast.mapreduce.KeyPredicate;
import mbaracus.tuples.CensoTuple;

/**
 * Created by jorexe on 17/11/16.
 */
public class ProvinceCounterKeyPredicate implements KeyPredicate<CensoTuple> {

    private String prov;

    public ProvinceCounterKeyPredicate(String prov) {
        this.prov = prov;
    }

    @Override
    public boolean evaluate(CensoTuple s) {
        return prov.equals(s.getNombreprov());
    }
}
