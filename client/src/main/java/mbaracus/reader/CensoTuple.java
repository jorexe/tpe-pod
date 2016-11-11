package mbaracus.reader;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;

public class CensoTuple implements DataSerializable {
    private Integer tipoVivienda;
    private Integer calidadServicios;
    private Integer sexo;
    private Integer edad;
    private Integer alfabetismo;
    private Integer actividad;
    private String nombredepto;
    private String nombreprov;
    private Integer hogarId;

    public CensoTuple() {
    }

    public CensoTuple(Integer tipoVivienda, Integer calidadServicios, Integer sexo, Integer edad, Integer alfabetismo, Integer actividad, String nombreDpto, String nombreProvincia, Integer hogarId) {
        this.tipoVivienda = tipoVivienda;
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

    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {

    }

    public Integer getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(Integer tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
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
}
