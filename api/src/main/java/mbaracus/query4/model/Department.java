package mbaracus.query4.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class Department implements DataSerializable {
    private String nombreProv;
    private String nombreDpto;
    private Integer habitants;

    public Department(String nombreprov, String nombredepto, int habitants) {
        this.nombreProv = nombreprov;
        this.nombreDpto = nombredepto;
        this.habitants = habitants;
    }

    public Department() {
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(nombreProv);
        out.writeUTF(nombreDpto);
        out.writeInt(habitants);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.nombreProv = in.readUTF();
        this.nombreDpto = in.readUTF();
        this.habitants = in.readInt();
    }

    public String getNombreProv() {
        return this.nombreProv;
    }

    public String getNombreDpto() {
        return nombreDpto;
    }

    public Integer getHabitants() {
        return habitants;
    }

    public void addHabitant() {
        this.habitants++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (nombreProv != null ? !nombreProv.equals(that.nombreProv) : that.nombreProv != null) return false;
        return nombreDpto != null ? nombreDpto.equals(that.nombreDpto) : that.nombreDpto == null;

    }

    @Override
    public int hashCode() {
        int result = nombreProv != null ? nombreProv.hashCode() : 0;
        result = 31 * result + (nombreDpto != null ? nombreDpto.hashCode() : 0);
        return result;
    }
}
