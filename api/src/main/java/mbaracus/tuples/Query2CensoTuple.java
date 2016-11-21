package mbaracus.tuples;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import mbaracus.enumerators.HouseType;

import java.io.IOException;

public class Query2CensoTuple extends CensoTuple {
    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(hogarId);
        out.writeShort(tipoVivienda.ordinal());
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.hogarId = in.readInt();
        this.tipoVivienda = HouseType.from(in.readShort());
    }
}
