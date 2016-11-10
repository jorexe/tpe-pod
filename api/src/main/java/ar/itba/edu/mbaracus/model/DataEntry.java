package ar.itba.edu.mbaracus.model;

import ar.itba.edu.mbaracus.enumerators.HouseType;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

/**
 * Created by jorexe on 10/11/16.
 */
public abstract class DataEntry implements DataSerializable{

    public long id;
    public int age;
    public boolean alfabetism;
    public HouseType houseType;
    public String departmentName;
    public String provinceName;

    public DataEntry() {
    }

    public DataEntry(long id, int age, boolean alfabetism, HouseType houseType, String departmentName, String provinceName) {
        this.id = id;
        this.age = age;
        this.alfabetism = alfabetism;
        this.houseType = houseType;
        this.departmentName = departmentName;
        this.provinceName = provinceName;
    }

    @Override
    public abstract void writeData(ObjectDataOutput out) throws IOException;

    @Override
    public abstract void readData(ObjectDataInput in) throws IOException;
}
