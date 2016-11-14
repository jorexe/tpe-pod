package mbaracus.query1.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import mbaracus.model.CensoTuple;

import java.io.IOException;

public class Query1DataEntry extends CensoTuple {

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(getEdad());
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        setEdad(in.readInt());
    }
}
