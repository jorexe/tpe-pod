package mbaracus.query1.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class AgeCount implements DataSerializable {
    public AgeType ageType;
    public int count;

    public AgeCount() {
    }

    public AgeCount(AgeType ageType, int count) {
        this.ageType = ageType;
        this.count = count;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(ageType.ordinal());
        out.writeInt(count);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.ageType = AgeType.values()[in.readInt()];
        this.count = in.readInt();
    }
}
