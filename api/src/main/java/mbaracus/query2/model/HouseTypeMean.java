package mbaracus.query2.model;

import mbaracus.enumerators.HouseType;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class HouseTypeMean implements DataSerializable {
    public HouseType houseType;
    public float mean;
    public Integer count;

    public HouseTypeMean() {
    }

    public HouseTypeMean(HouseType houseType, float mean, Integer count) {
        this.houseType = houseType;
        this.mean = mean;
        this.count = count;
    }

    public HouseTypeMean(float mean) {
        this.mean = mean;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(houseType.ordinal());
        out.writeFloat(mean);
        out.writeInt(count);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.houseType = HouseType.from(in.readInt());
        this.mean = in.readFloat();
        this.count = in.readInt();
    }

    @Override
    public String toString() {
        return "HouseTypeMean{" +
                "houseType=" + houseType +
                ", mean=" + mean +
                ", count=" + count +
                '}';
    }
}
