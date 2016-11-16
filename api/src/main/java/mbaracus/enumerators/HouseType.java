package mbaracus.enumerators;

public enum HouseType {
    NO_DATA("Sin datos"),
    HOUSE("Casa"),
    RANCH("Rancho"),
    BOX("Casilla"),
    DEPARTMENT("Departamento"),
    ROOM_RENTED("Pieza inquilinato"),
    ROOM_PENSION("Pieza Hotel Familiar/Pension/Vivienda Movil"),
    LOCAL("Local no construido para habitacion"),
    MOBILE("Vivienda movil"),
    NONE("Calle"),
    ERROR("Error");

    String loc;

    HouseType(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return this.loc;
    }

    public static HouseType from(int i) {
        if (i > HouseType.values().length - 1 || i < 0)
            return ERROR;
        return HouseType.values()[i];

    }
}
