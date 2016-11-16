package mbaracus.reader;

import com.hazelcast.core.IMap;
import mbaracus.model.CensoTuple;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.file.Path;

public class CensoReader {
    public static void parseCsv(final IMap<String, CensoTuple> iMap, Path path) throws IOException {
        final InputStream is = new FileInputStream(path.toString());
        final Reader aReader = new InputStreamReader(is);
        ICsvBeanReader beanReader = new CsvBeanReader(aReader, CsvPreference.STANDARD_PREFERENCE);

        final String[] header = beanReader.getHeader(true);
        final CellProcessor[] processors = getProcessors();

        CensoTuple data;
        while ((data = beanReader.read(CensoTuple.class, header, processors)) != null) {
            data.setRowId(beanReader.getLineNumber());
            iMap.set(data.getNombredepto() + ", " + data.getNombreprov(), data);
        }
        if (beanReader != null) {
            beanReader.close();
        }
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
//                tipovivienda,
//                calidadservicios,
//                sexo,
//                edad,
//                alfabetismo,
//                actividad,
//                nombredepto,
//                nombreprov,
//                hogarid
        };
    }
}
