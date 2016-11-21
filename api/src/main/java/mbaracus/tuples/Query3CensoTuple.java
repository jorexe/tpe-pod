package mbaracus.tuples;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;

import java.io.IOException;

public class Query3CensoTuple extends CensoTuple {
    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(alfabetismo);
        out.writeUTF(nombredepto);
        out.writeUTF(nombreprov);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.alfabetismo = in.readInt();
        this.nombredepto = in.readUTF();
        this.nombreprov = in.readUTF();
    }
}
