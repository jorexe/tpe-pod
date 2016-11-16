package mbaracus.query2.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import mbaracus.enumerators.HouseType;

import java.io.IOException;

public class HouseCount implements DataSerializable {
    public HouseType tipoHogar;
    public Integer hogarId;
    public Integer count;

    public HouseCount() {
    }

    public HouseCount(Integer hogarId, Integer count, HouseType tipoHogar) {
        this.hogarId = hogarId;
        this.count = count;
        this.tipoHogar = tipoHogar;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(hogarId);
        out.writeInt(count);
        out.writeInt(tipoHogar.ordinal());
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.hogarId = in.readInt();
        this.count = in.readInt();
        this.tipoHogar = HouseType.from(in.readInt());
    }

    @Override
    public String toString() {
        return "HouseCount{" +
                "tipoHogar=" + tipoHogar +
                ", hogarId=" + hogarId +
                ", count=" + count +
                '}';
    }
}
