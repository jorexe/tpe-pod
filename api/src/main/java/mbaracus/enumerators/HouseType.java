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

    public static HouseType from(Integer i) {
        switch (i) {
            case 0:
                return NO_DATA;
            case 1:
                return HOUSE;
            case 2:
                return RANCH;
            case 3:
                return BOX;
            case 4:
                return DEPARTMENT;
            case 5:
                return ROOM_RENTED;
            case 6:
                return ROOM_PENSION;
            case 7:
                return LOCAL;
            case 8:
                return MOBILE;
            case 9:
                return NONE;
            default:
                return ERROR;
        }
    }
}
