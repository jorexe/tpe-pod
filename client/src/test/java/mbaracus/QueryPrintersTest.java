package mbaracus;

import mbaracus.enumerators.HouseType;
import mbaracus.query2.model.HouseTypeMean;
import mbaracus.query3.model.DepartmentStat;
import mbaracus.query4.model.Department;
import mbaracus.utils.QueryPrinters;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryPrintersTest {
    private static final String PREFIX = "./client/src/test/resources/";
    private static final Path QUERY_1_OUTPUT = Paths.get(PREFIX + "query1Output.txt");
    private static final Path QUERY_2_OUTPUT = Paths.get(PREFIX + "query2Output.txt");
    private static final Path QUERY_3_OUTPUT = Paths.get(PREFIX + "query3Output.txt");
    private static final Path QUERY_4_OUTPUT = Paths.get(PREFIX + "query4Output.txt");
    private static final Path QUERY_5_OUTPUT = Paths.get(PREFIX + "query5Output.txt");

    @Before
    public void setUp() {
    }

    @Test
    public void query1Test() throws IOException {
        QueryPrinters.printResultQuery1(QUERY_1_OUTPUT, 25376, 64160, 10464);

        String[] lines = Files.lines(QUERY_1_OUTPUT).toArray(String[]::new);
        assertEquals("0-14 = 25376", lines[0]);
        assertEquals("15-64 = 64160", lines[1]);
        assertEquals("65-? = 10464", lines[2]);
    }

    @Test
    public void query2Test() throws IOException {
        Map<HouseType, HouseTypeMean> map = new HashMap<>();
        map.put(HouseType.from(0), new HouseTypeMean(15.16f));
        map.put(HouseType.from(1), new HouseTypeMean(3.42f));
        map.put(HouseType.from(2), new HouseTypeMean(3.97f));
        map.put(HouseType.from(3), new HouseTypeMean(3.92f));
        map.put(HouseType.from(4), new HouseTypeMean(2.39f));
        map.put(HouseType.from(5), new HouseTypeMean(2.48f));
        map.put(HouseType.from(6), new HouseTypeMean(1.79f));
        map.put(HouseType.from(7), new HouseTypeMean(2.47f));
        map.put(HouseType.from(8), new HouseTypeMean(3.0f));
        map.put(HouseType.from(9), new HouseTypeMean(1.67f));

        QueryPrinters.printResultQuery2(QUERY_2_OUTPUT, map);

        String[] lines = Files.lines(QUERY_2_OUTPUT).toArray(String[]::new);
        assertEquals("0 = 15,16", lines[0]);
        assertEquals("1 = 3,42", lines[1]);
        assertEquals("2 = 3,97", lines[2]);
        assertEquals("3 = 3,92", lines[3]);
        assertEquals("4 = 2,39", lines[4]);
        assertEquals("5 = 2,48", lines[5]);
        assertEquals("6 = 1,79", lines[6]);
        assertEquals("7 = 2,47", lines[7]);
        assertEquals("8 = 3,00", lines[8]);
        assertEquals("9 = 1,67", lines[9]);
    }

    @Test
    public void query3Test() throws IOException {
        List<DepartmentStat> list = new ArrayList<>(5);
        list.add(new DepartmentStat(4, 10, "Catan Lil"));
        list.add(new DepartmentStat(25, 100, "Telsen"));
        list.add(new DepartmentStat(2, 10, "Rinconada"));
        list.add(new DepartmentStat(18, 100, "San Blas de los Sauces"));
        list.add(new DepartmentStat(17, 100, "Tehuelches"));

        QueryPrinters.printResultQuery3(QUERY_3_OUTPUT, list);

        String[] lines = Files.lines(QUERY_3_OUTPUT).toArray(String[]::new);
        assertEquals("Catan Lil = 0,40", lines[0]);
        assertEquals("Telsen = 0,25", lines[1]);
        assertEquals("Rinconada = 0,20", lines[2]);
        assertEquals("San Blas de los Sauces = 0,18", lines[3]);
        assertEquals("Tehuelches = 0,17", lines[4]);
    }

    @Test
    public void query4Test() throws IOException {
        List<Department> list = new ArrayList<>(5);
        list.add(new Department("San Fernando", 972));
        list.add(new Department("Comandante Fernández", 312));
        list.add(new Department("General Güemes", 190));
        list.add(new Department("Libertador General San Martín", 156));
        list.add(new Department("Mayor Luis J. Fontana", 137));

        QueryPrinters.printResultQuery4(QUERY_4_OUTPUT, list);

        String[] lines = Files.lines(QUERY_4_OUTPUT).toArray(String[]::new);
        assertEquals("San Fernando = 972", lines[0]);
        assertEquals("Comandante Fernández = 312", lines[1]);
        assertEquals("General Güemes = 190", lines[2]);
        assertEquals("Libertador General San Martín = 156", lines[3]);
        assertEquals("Mayor Luis J. Fontana = 137", lines[4]);
    }

//    @Test
//    public void query5Test() throws IOException {
//        Map<Integer, List<Pair<String, String>>> map = new HashMap<>();
//        List<Pair<String, String>> l1 = new LinkedList<>();
//        List<Pair<String, String>> l2 = new LinkedList<>();
//        l1.add(Pair.of("Quilmes", "San Fernando"));
//        l1.add(Pair.of("Almirante Brown", "Quilmes"));
//        l1.add(Pair.of("Almirante Brown", "San Fernando"));
//        l2.add(Pair.of("Merlo", "General San Martín"));
//        map.put(1300, l1);
//        map.put(1400, l2);
//
//        QueryPrinters.printResultQuery5(QUERY_5_OUTPUT, map);
//
//        String[] lines = Files.lines(QUERY_5_OUTPUT).toArray(String[]::new);
//        assertEquals("1300", lines[0]);
//        assertEquals("Quilmes + San Fernando", lines[1]);
//        assertEquals("Almirante Brown + Quilmes", lines[2]);
//        assertEquals("Almirante Brown + San Fernando", lines[3]);
//        assertEquals("", lines[4]);
//        assertEquals("1400", lines[5]);
//        assertEquals("Merlo + General San Martín", lines[6]);
//    }
}
