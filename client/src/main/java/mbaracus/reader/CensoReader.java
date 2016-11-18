package mbaracus.reader;

import com.hazelcast.core.IMap;
import mbaracus.model.CensoTuple;
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

        final String[] header = beanReader.getHeader(true);
        final CellProcessor[] processors = getProcessors();

        CensoTuple data;
        Integer row;
        while ((data = beanReader.read(CensoTuple.class, header, processors)) != null) {
            if (parser.getQuery() == 4) {
                if (!data.getNombreprov().equals(parser.getProvince())) {
                    break;
                }
            }
            data.setRowId(beanReader.getLineNumber());
            row = beanReader.getLineNumber();

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

    private static CellProcessor[] getProcessors() {
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
