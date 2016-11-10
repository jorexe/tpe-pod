package ar.itba.edu.mbaracus.query1.model;

/**
 * Created by jorexe on 10/11/16.
 */
public enum AgeType {
    A("0-14 Años"),
    B("15-64 Años"),
    C("65 o más Años");

    String loc;

    AgeType(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return this.loc;
    }

    public static AgeType getAgeTypeByAge(int age) {
        if (age >= 0 && age <= 14) {
            return A;
        }
        if (age >= 15 && age <= 64) {
            return B;
        }
        if (age >= 65) {
            return C;
        }
        throw new RuntimeException("Invalid age " + age);
    }
}
