package mbaracus.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import mbaracus.enumerators.HouseType;

import java.io.IOException;

public class CensoTuple implements DataSerializable {

    protected Integer rowId;
    protected HouseType tipoVivienda;
    protected Integer calidadServicios;
    protected Integer sexo;
    protected Integer edad;
    protected Integer alfabetismo;
    protected Integer actividad;
    protected String nombredepto;
    protected String nombreprov;
    protected Integer hogarId;

    public CensoTuple() {
    }

    public CensoTuple(Integer tipoVivienda, Integer calidadServicios, Integer sexo, Integer edad, Integer alfabetismo, Integer actividad, String nombreDpto, String nombreProvincia, Integer hogarId) {
        this.tipoVivienda = HouseType.from(tipoVivienda);
        this.calidadServicios = calidadServicios;
        this.sexo = sexo;
        this.edad = edad;
        this.alfabetismo = alfabetismo;
        this.actividad = actividad;
        this.nombredepto = nombreDpto;
        this.nombreprov = nombreProvincia;
        this.hogarId = hogarId;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeInt(rowId);
        out.writeShort(tipoVivienda.ordinal());
        out.writeInt(calidadServicios);
        out.writeInt(sexo);
        out.writeInt(edad);
        out.writeInt(alfabetismo);
        out.writeInt(actividad);
        out.writeUTF(nombredepto);
        out.writeUTF(nombreprov);
        out.writeInt(hogarId);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.rowId = in.readInt();
        this.tipoVivienda = HouseType.from(in.readShort());
        this.calidadServicios = in.readInt();
        this.sexo = in.readInt();
        this.edad = in.readInt();
        this.alfabetismo = in.readInt();
        this.actividad = in.readInt();
        this.nombredepto= in.readUTF();
        this.nombreprov = in.readUTF();
        this.hogarId = in.readInt();
    }

    @Override
    public String toString() {
        return "CensoTuple{" +
                "tipoVivienda=" + tipoVivienda +
                ", calidadServicios=" + calidadServicios +
                ", sexo=" + sexo +
                ", edad=" + edad +
                ", alfabetismo=" + alfabetismo +
                ", actividad=" + actividad +
                ", nombredepto='" + nombredepto + '\'' +
                ", nombreprov='" + nombreprov + '\'' +
                ", hogarId=" + hogarId +
                '}';
    }

    @Override
    public int hashCode() {
        int result = tipoVivienda != null ? tipoVivienda.hashCode() : 0;
        result = 31 * result + (calidadServicios != null ? calidadServicios.hashCode() : 0);
        result = 31 * result + (sexo != null ? sexo.hashCode() : 0);
        result = 31 * result + (edad != null ? edad.hashCode() : 0);
        result = 31 * result + (alfabetismo != null ? alfabetismo.hashCode() : 0);
        result = 31 * result + (actividad != null ? actividad.hashCode() : 0);
        result = 31 * result + (nombredepto != null ? nombredepto.hashCode() : 0);
        result = 31 * result + (nombreprov != null ? nombreprov.hashCode() : 0);
        result = 31 * result + (hogarId != null ? hogarId.hashCode() : 0);
        return result;
    }

    public HouseType getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(Integer tipoVivienda) {
        this.tipoVivienda = HouseType.from(tipoVivienda);
    }

    public Integer getCalidadServicios() {
        return calidadServicios;
    }

    public void setCalidadServicios(Integer calidadServicios) {
        this.calidadServicios = calidadServicios;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getAlfabetismo() {
        return alfabetismo;
    }

    public void setAlfabetismo(Integer alfabetismo) {
        this.alfabetismo = alfabetismo;
    }

    public Integer getActividad() {
        return actividad;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
    }

    public String getNombredepto() {
        return nombredepto;
    }

    public void setNombredepto(String nombreDpto) {
        this.nombredepto = nombreDpto.trim();
    }

    public String getNombreprov() {
        return nombreprov;
    }

    public void setNombreprov(String nombreProvincia) {
        this.nombreprov = nombreProvincia.trim();
    }

    public Integer getHogarId() {
        return hogarId;
    }

    public void setHogarId(Integer hogarId) {
        this.hogarId = hogarId;
    }

    public void setRowId(Integer id) {
        this.rowId = id;
    }
}
