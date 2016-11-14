package mbaracus.query2.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class HouseCount implements DataSerializable {
    public String departmentName;
    public int count;

    public HouseCount() {
    }

    public HouseCount(String departmentName, int count) {
        this.departmentName = departmentName;
        this.count = count;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(departmentName);
        out.writeInt(count);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.departmentName = in.readUTF();
        this.count = in.readInt();
    }
}
