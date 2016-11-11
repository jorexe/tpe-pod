package ar.itba.edu.mbaracus.query2.model;

import ar.itba.edu.mbaracus.enumerators.HouseType;
import ar.itba.edu.mbaracus.model.DataEntry;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;

import java.io.IOException;

/**
 * Created by jorexe on 10/11/16.
 */
public class QueryDataEntry extends DataEntry {

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(houseType.ordinal());
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.houseType = HouseType.values()[in.readInt()];
    }
}
