package mbaracus.query2.model;

import mbaracus.enumerators.HouseType;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import mbaracus.model.CensoTuple;

import java.io.IOException;

public class QueryDataEntry extends CensoTuple {

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
//        out.writeInt(tipoVivienda.ordinal());
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
//        this.tipoVivienda = HouseType.values()[in.readInt()];
    }
}
