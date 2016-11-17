package mbaracus.query5.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class DepartmentCount implements DataSerializable {
    public Integer count;
    public String departmentName;

    public DepartmentCount() {
    }

    public DepartmentCount(Integer count, String departmentName) {
        this.count = count;
        this.departmentName = departmentName;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(count);
        out.writeUTF(departmentName);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.count = in.readInt();
        this.departmentName = in.readUTF();
    }

    @Override
    public String toString() {
        return "DepartmentCount{" +
                ", count=" + count +
                '}';
    }
}
