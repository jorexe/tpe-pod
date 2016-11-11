package mbaracus.query2.model;

import mbaracus.enumerators.HouseType;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

/**
 * Created by jorexe on 10/11/16.
 */
public class HouseTypeMean implements DataSerializable {
    public HouseType houseType;
    public float mean;
    public int count;

    public HouseTypeMean() {
    }

    public HouseTypeMean(HouseType houseType, float mean, int count) {
        this.houseType = houseType;
        this.mean = mean;
        this.count = count;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(houseType.ordinal());
        out.writeFloat(mean);
        out.writeInt(count);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.houseType = HouseType.values()[in.readInt()];
        this.mean = in.readFloat();
        this.count = in.readInt();
    }
}
