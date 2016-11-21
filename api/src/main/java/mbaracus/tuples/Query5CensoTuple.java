package mbaracus.tuples;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;

import java.io.IOException;

public class Query5CensoTuple extends CensoTuple {
    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(nombredepto);
        out.writeUTF(nombreprov);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.nombredepto= in.readUTF();
        this.nombreprov = in.readUTF();
    }
}
