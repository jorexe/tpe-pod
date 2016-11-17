package mbaracus.query3.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class DepartmentStat implements DataSerializable {
    private int analfabetos;
    private int totalHabitants;
    private String nombreDepto;
    private String nombreProv;

    public DepartmentStat(int analfabetos, int totalHabitants, String nombreDepto, String nombreProv) {
        this.analfabetos = analfabetos;
        this.totalHabitants = totalHabitants;
        this.nombreDepto = nombreDepto;
        this.nombreProv = nombreProv;
    }

    public DepartmentStat() {
    }

    public int getAnalfabetos() {
        return analfabetos;
    }

    public void setAnalfabetos(int analfabetos) {
        this.analfabetos = analfabetos;
    }

    public int getTotalHabitants() {
        return totalHabitants;
    }

    public void setTotalHabitants(int totalHabitants) {
        this.totalHabitants = totalHabitants;
    }

    public String getNombreProv() {
        return nombreProv;
    }

    public void setNombreProv(String nombreProv) {
        this.nombreProv = nombreProv;
    }

    public String getNombreDepto() {
        return nombreDepto;
    }

    public void setNombreDepto(String nombreDepto) {
        this.nombreDepto = nombreDepto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentStat that = (DepartmentStat) o;

        if (!nombreDepto.equals(that.nombreDepto)) return false;
        return nombreProv.equals(that.nombreProv);

    }

    @Override
    public int hashCode() {
        int result = nombreDepto.hashCode();
        result = 31 * result + nombreProv.hashCode();
        return result;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeUTF(this.nombreDepto);
        out.writeUTF(this.nombreProv);
        out.writeInt(this.analfabetos);
        out.writeInt(this.totalHabitants);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.nombreDepto = in.readUTF();
        this.nombreProv = in.readUTF();
        this.analfabetos = in.readInt();
        this.totalHabitants = in.readInt();
    }

    public float getIndex() {
        return (float) analfabetos / totalHabitants;
    }

}
