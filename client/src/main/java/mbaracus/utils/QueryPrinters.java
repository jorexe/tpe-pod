package mbaracus.utils;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class QueryPrinters {
    public static void printResultQuery1(Path output, Integer lt14, Integer b15t65, Integer mt65) throws IOException {
        List<String> toPrint = new ArrayList<>();
        toPrint.add("0-14 = " + lt14);
        toPrint.add("15-64 = " + b15t65);
        toPrint.add("65-? = " + mt65);
        writeTo(output, toPrint);
    }

    public static void printResultQuery2(Path output, Map<Integer, Double> map) throws IOException {
        List<String> toPrint = new ArrayList<>();

        for (Integer i: map.keySet()) {
            toPrint.add(String.format("%d = %.2f", i, map.get(i)).replace(".", ","));
        }

        Collections.sort(toPrint);
        writeTo(output, toPrint);
    }

    public static void printResultQuery3(Path output, Map<String, Double> map) throws IOException {
        List<String> toPrint = new ArrayList<>();

        for (String department: map.keySet()) {
            toPrint.add(String.format("%s = %.2f", department, map.get(department)).replace(".", ","));
        }

        writeTo(output, toPrint);
    }

    public static void printResultQuery4(Path output, Map<String, Integer> map) throws IOException {
        List<String> toPrint = new ArrayList<>();

        for (String department: map.keySet()) {
            toPrint.add(department + " = " + map.get(department));
        }

        writeTo(output, toPrint);
    }

    public static void printResultQuery5(Path output, Map<Integer, List<Pair<String, String>>> map) throws IOException {
        List<String> toPrint = new LinkedList<>();

        for (Integer i: map.keySet()) {
            toPrint.add(i.toString());
            for(Pair<String, String> p: map.get(i)) {
                toPrint.add(p.getLeft() + " + " + p.getRight());
            }
            toPrint.add("");
        }

        writeTo(output, toPrint);
    }

    private static void writeTo(Path path, Iterable<? extends CharSequence> things) throws IOException {
        path.toFile().createNewFile();
        Files.write(path, things, Charset.forName("UTF-8"));
    }
}
