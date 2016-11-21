package mbaracus.reader;

import com.hazelcast.core.IMap;
import mbaracus.tuples.*;
import mbaracus.utils.ArgumentParser;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.file.Path;

public class CensoReader {
    public static void parseCsv(final IMap<Integer, CensoTuple> iMap, Path path, ArgumentParser parser) throws IOException {
        final InputStream is = new FileInputStream(path.toString());
        final Reader aReader = new InputStreamReader(is);
        ICsvBeanReader beanReader = new CsvBeanReader(aReader, CsvPreference.STANDARD_PREFERENCE);

        final String[] header = getHeadersByQuery(beanReader, parser.getQuery());
        final CellProcessor[] processors = getProcessorsByQuery(parser.getQuery());

        CensoTuple data;
        Integer row;
        while ((data = beanReader.read(getClassByQuery(parser.getQuery()), header, processors)) != null) {
            data.setRowId(beanReader.getLineNumber());
            row = beanReader.getLineNumber();
            //TODO Maybe this validation could be using org.supercsv.cellprocessor.constraint.Equals on CellProcessor
            if (parser.getQuery() != 4 || data.getNombreprov().equals(parser.getProvince())) {
                addToMap(iMap, row, data);
            }
        }
        beanReader.close();
    }

    private static void addToMap(IMap<Integer, CensoTuple> iMap, Integer i, CensoTuple tuple) {
        iMap.set(i, tuple);
    }

    private static String[] getHeadersByQuery(ICsvBeanReader beanReader, int query) throws IOException {
        String[] defaultHeaders = beanReader.getHeader(true); // Remove headers
        // {"tipovivienda","calidadservicios","sexo","edad","alfabetismo","actividad","nombredepto","nombreprov","hogarid"}
        switch (query) {
            case 1:
                return new String[]{null, null, null, "edad", null, null, null, null, null};
            case 2:
                return new String[]{"tipovivienda", null, null, null, null, null, null, null, "hogarid"};
            case 3:
                return new String[]{null, null, null, null, "alfabetismo", null, "nombredepto", "nombreprov", null};
            case 4:
                return new String[]{null, null, null, null, null, null, "nombredepto", "nombreprov", null};
            case 5:
                return new String[]{null, null, null, null, null, null, "nombredepto", "nombreprov", null};
            default:
                return defaultHeaders;
        }
    }

    private static Class<? extends CensoTuple> getClassByQuery(int query) {
        switch (query) {
            case 1:
                return Query1CensoTuple.class;
            case 2:
                return Query2CensoTuple.class;
            case 3:
                return Query3CensoTuple.class;
            case 4:
                return Query4CensoTuple.class;
            case 5:
                return Query5CensoTuple.class;
            default:
                return CensoTuple.class;
        }
    }

    private static CellProcessor[] getProcessorsByQuery(int query) {
        switch (query) {
            case 1:
                return new CellProcessor[]{
                        null, // Tipo vivienda
                        null, // Calidad servicios
                        null, // Sexo
                        new ParseInt(new NotNull()), // Edad
                        null, // Alfabetismo
                        null, // Actividad
                        null, // Nombre departamento
                        null, // Nombre provincia
                        null // HogarId
                };
            case 2:
                return new CellProcessor[]{
                        new ParseInt(new NotNull()), // Tipo vivienda
                        null, // Calidad servicios
                        null, // Sexo
                        null, // Edad
                        null, // Alfabetismo
                        null, // Actividad
                        null, // Nombre departamento
                        null, // Nombre provincia
                        new ParseInt(new NotNull()) // HogarId
                };
            case 3:
                return new CellProcessor[]{
                        null, // Tipo vivienda
                        null, // Calidad servicios
                        null, // Sexo
                        null, // Edad
                        new ParseInt(new NotNull()), // Alfabetismo
                        null, // Actividad
                        new NotNull(), // Nombre departamento
                        new NotNull(), // Nombre provincia
                        null // HogarId
                };
            case 4:
                return new CellProcessor[]{
                        null, // Tipo vivienda
                        null, // Calidad servicios
                        null, // Sexo
                        null, // Edad
                        null, // Alfabetismo
                        null, // Actividad
                        new NotNull(), // Nombre departamento
                        new NotNull(), // Nombre provincia
                        null // HogarId
                };
            case 5:
                return new CellProcessor[]{
                        null, // Tipo vivienda
                        null, // Calidad servicios
                        null, // Sexo
                        null, // Edad
                        null, // Alfabetismo
                        null, // Actividad
                        new NotNull(), // Nombre departamento
                        new NotNull(), // Nombre provincia
                        null // HogarId
                };
            default:
                return getDefaultProcessors();
        }
    }

    private static CellProcessor[] getDefaultProcessors() {
        return new CellProcessor[]{
                new ParseInt(new NotNull()), // Tipo vivienda
                new ParseInt(new NotNull()), // Calidad servicios
                new ParseInt(new NotNull()), // Sexo
                new ParseInt(new NotNull()), // Edad
                new ParseInt(new NotNull()), // Alfabetismo
                new ParseInt(new NotNull()), // Actividad
                new NotNull(), // Nombre departamento
                new NotNull(), // Nombre provincia
                new ParseInt(new NotNull()) // HogarId
        };
    }
}
