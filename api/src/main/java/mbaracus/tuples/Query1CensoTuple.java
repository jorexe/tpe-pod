package mbaracus.tuples;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;

import java.io.IOException;

public class Query1CensoTuple extends CensoTuple {
    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(edad);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.edad = in.readInt();
    }
}
