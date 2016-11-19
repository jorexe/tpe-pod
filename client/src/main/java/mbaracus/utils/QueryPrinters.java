package mbaracus.utils;

import mbaracus.enumerators.HouseType;
import mbaracus.query2.model.HouseTypeMean;
import mbaracus.query3.model.DepartmentStat;
import mbaracus.query4.model.Department;
import mbaracus.query5.model.DepartmentCount;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class QueryPrinters {
    public static void printResultQuery1(Path output, Integer lt14, Integer b15t65, Integer mt65) throws IOException {
        List<String> toPrint = new ArrayList<>();
        toPrint.add("0-14 = " + lt14);
        toPrint.add("15-64 = " + b15t65);
        toPrint.add("65-? = " + mt65);
        writeTo(output, toPrint);
    }

    public static void printResultQuery2(Path output, Map<HouseType, HouseTypeMean> map) throws IOException {
        List<String> toPrint = new ArrayList<>();

        for (HouseType houseType : map.keySet()) {
            toPrint.add(String.format("%d = %.2f", houseType.ordinal(), map.get(houseType).mean).replace(".", ","));
        }

        Collections.sort(toPrint);
        writeTo(output, toPrint);
    }

    public static void printResultQuery3(Path output, List<DepartmentStat> departmentStatList) throws IOException {
        List<String> toPrint = new ArrayList<>();

        for (DepartmentStat departmentStat : departmentStatList) {
            toPrint.add(String.format("%s = %.2f", departmentStat.getNombreDepto(), departmentStat.getIndex()).replace(".", ","));
        }

        writeTo(output, toPrint);
    }

    public static void printResultQuery4(Path output, List<Department> list) throws IOException {
        List<String> toPrint = new ArrayList<>();

        for (Department department : list) {
            toPrint.add(department.getNombreDpto() + " = " + department.getHabitants());
        }

        writeTo(output, toPrint);
    }

    public static void printResultQuery5(Path output, Map<String, DepartmentCount> result) throws IOException {

        Map<Integer, List<String>> departments = new ConcurrentHashMap<>();
        result.keySet().stream().parallel().forEach(x -> {
            DepartmentCount departmentCount = result.get(x);
            int thousands = getThousandsFromInteger(departmentCount.count);
            departments.putIfAbsent(thousands, Collections.synchronizedList(new ArrayList<>()));
            departments.get(thousands).add(departmentCount.departmentName + " (" + departmentCount.departmentProvince + ")");
        });

        List<String> toPrint = new LinkedList<>();
        departments.keySet().stream().sorted().forEach(x -> {
            List<String> list = departments.get(x);
            if (list.size() > 1) {
                toPrint.add(Integer.toString(x * 100));
                for (int j = 0; j < list.size() - 1; j++) {
                    for (int k = j + 1; k < list.size(); k++) {
                        toPrint.add(list.get(j) + " + " + list.get(k));
                    }
                }
            }
            toPrint.add("");
        });
        writeTo(output, toPrint);
    }

    private static void writeTo(Path path, Iterable<? extends CharSequence> things) throws IOException {
        path.toFile().createNewFile();
        Files.write(path, things, Charset.forName("UTF-8"));
    }

    private static int getThousandsFromInteger(int a) {
        return a / 100;
    }
}
