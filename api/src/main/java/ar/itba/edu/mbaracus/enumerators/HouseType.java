package ar.itba.edu.mbaracus.enumerators;

/**
 * Created by jorexe on 10/11/16.
 */
public enum HouseType {
    HOUSE("Casa"),
    RANCH("Rancho"),
    BOX("Casilla"),
    DEPARTMENT("Departamento"),
    ROOM_RENTED("Pieza inquilinato"),
    ROOM_PENSION("Pieza Hotel Familiar/Pension/Vivienda Movil"),
    NONE("Calle");

    String loc;

    HouseType(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return this.loc;
    }
}
