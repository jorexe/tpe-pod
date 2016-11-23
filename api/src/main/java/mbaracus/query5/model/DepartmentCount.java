package mbaracus.query5.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class DepartmentCount implements DataSerializable {

    public String departmentName;
    public String departmentProvince;
    public Integer count;

    public DepartmentCount() {
    }

    public DepartmentCount(String departmentName, String departmentProvince, Integer count) {
        this.departmentName = departmentName;
        this.departmentProvince = departmentProvince;
        this.count = count;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(count);
        out.writeUTF(departmentProvince);
        out.writeUTF(departmentName);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.count = in.readInt();
        this.departmentProvince= in.readUTF();
        this.departmentName = in.readUTF();
    }

    @Override
    public String toString() {
        return "DepartmentCount{" +
                "departmentName='" + departmentName + '\'' +
                ", departmentProvince='" + departmentProvince + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentCount that = (DepartmentCount) o;

        if (!departmentName.equals(that.departmentName)) return false;
        if (!departmentProvince.equals(that.departmentProvince)) return false;
        return count.equals(that.count);

    }

    @Override
    public int hashCode() {
        int result = departmentName.hashCode();
        result = 31 * result + departmentProvince.hashCode();
        result = 31 * result + count.hashCode();
        return result;
    }
}
