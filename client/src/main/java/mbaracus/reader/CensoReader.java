package mbaracus.reader;

import com.hazelcast.core.IMap;
import mbaracus.model.CensoTuple;
import mbaracus.query5.model.Query5CensoTuple;
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
            if (parser.getQuery() == 4) {
                if (!data.getNombreprov().equals(parser.getProvince())) {
                    break;
                }
            }
            data.setRowId(beanReader.getLineNumber());
            row = beanReader.getLineNumber();
            //TODO Maybe this validation could be using org.supercsv.cellprocessor.constraint.Equals on CellProcessor
            if (parser.getQuery() == 4 && !data.getNombreprov().equals(parser.getProvince())) {
                // Skip this tuple so we don't waste time in unnecessary IO
            } else {
                addToMap(iMap, row, data);
            }
        }
        if (beanReader != null) {
            beanReader.close();
        }
    }

    private static void addToMap(IMap<Integer, CensoTuple> iMap, Integer i, CensoTuple tuple) {
        iMap.set(i, tuple);
    }

    private static String[] getHeadersByQuery(ICsvBeanReader beanReader, int query) throws IOException {
        switch (query) {
            case 1:
                //TODO Implement headers
                break;
            case 2:
                //TODO Implement headers
                break;
            case 3:
                //TODO Implement headers
                break;
            case 4:
                //TODO Implement headers
                break;
            case 5:
                beanReader.getHeader(true);
                return new String[] {null, null, null, null, null, null, "nombredepto", "nombreprov", null};
        }
        //TODO This is the default header, delete this line when all cases completed
        //return new String[] {"tipovivienda","calidadservicios","sexo","edad","alfabetismo","actividad","nombredepto","nombreprov","hogarid"};
        //Return default header otherwise
        return beanReader.getHeader(true);
    }

    private static Class<? extends CensoTuple> getClassByQuery(int query) {
        switch (query) {
            case 1:
                //TODO Implement class
                break;
            case 2:
                //TODO Implement class
                break;
            case 3:
                //TODO Implement class
                break;
            case 4:
                //TODO Implement class
                break;
            case 5:
                return Query5CensoTuple.class;
        }
        return CensoTuple.class;
    }

    private static CellProcessor[] getProcessorsByQuery(int query) {
        switch (query) {
            case 1:
                //TODO Implement processors
                break;
            case 2:
                //TODO Implement processors
                break;
            case 3:
                //TODO Implement processors
                break;
            case 4:
                //TODO Implement processors
                break;
            case 5:
                return new CellProcessor[] {
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
        }
        return getDefaultProcessors();
    }

    private static CellProcessor[] getDefaultProcessors() {
        return new CellProcessor[] {
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
