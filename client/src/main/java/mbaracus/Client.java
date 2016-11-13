package mbaracus;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import mbaracus.query1.model.QueryDataEntry;
import mbaracus.reader.CensoReader;
import mbaracus.reader.CensoTuple;
import mbaracus.utils.ArgumentParser;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;

public class Client {
    public static final String MAP_NAME = "censo-baracus";
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws IOException {
        long startTime, endTime;

        logger.info("Inicio del parseo de entrada");
        startTime = System.currentTimeMillis();
        ArgumentParser parser = new ArgumentParser();
        try {
            parser.parse(args);
        } catch (ParseException e) {
            logger.error(e.getMessage());
            return;
        } catch (UnknownHostException e) {
            logger.error(e.getMessage());
            return;
        }
        endTime = System.currentTimeMillis();
        logger.info("Fin del parseo de entrada" + timeDuration(startTime, endTime));


        HazelcastInstance client = getHzClient(parser);
        System.out.println(client.getCluster());
        IMap<Integer, CensoTuple> iMap = client.getMap(MAP_NAME);


        logger.info("Inicio de la lectura del archivo");
        startTime = System.currentTimeMillis();
        CensoReader.parseCsv(iMap, parser.getInputFile());
        endTime = System.currentTimeMillis();
        logger.info("Fin de la lectura del archivo" + timeDuration(startTime, endTime));


        JobTracker tracker = client.getJobTracker("default");
        KeyValueSource<Integer, CensoTuple> source = KeyValueSource.fromMap(iMap);
        Job<Integer, CensoTuple> job = tracker.newJob(source);

//        // Orquestacion de Jobs y lanzamiento
//        ICompletableFuture<Map<String, FormulaTupla>> future = job
//                .mapper(new ComunaFormulaVotesMapperFactory())
//                .reducer(new WinningFormulaReducerFactory())
//                .submit();
//
//        // Tomar resultado e Imprimirlo
//        Map<String, FormulaTupla> rta = future.get();
//
//        for (Map.Entry<String, FormulaTupla> e : rta.entrySet()) {
//            System.out.println(String.format("Distrito %s => Ganador %s", e.getKey(), e.getValue()));
//        }
//
//        System.exit(0);

        logger.info("Inicio del trabajo map/reduce");
        startTime = System.currentTimeMillis();
        switch (parser.getQuery()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        endTime = System.currentTimeMillis();
        logger.info("Fin del trabajo map/reduce" + timeDuration(startTime, endTime));
    }

    private static HazelcastInstance getHzClient(ArgumentParser parser) {
        ClientConfig ccfg = new ClientConfig();
        String[] arrayAddresses = new String[2];
        arrayAddresses[0] = parser.getIp1().toString();
        arrayAddresses[1] = parser.getIp2().toString();
        ClientNetworkConfig net = new ClientNetworkConfig();
        net.addAddress(arrayAddresses);
        ccfg.setNetworkConfig(net);
        return HazelcastClient.newHazelcastClient(ccfg);
    }

    private static String timeDuration(long timeStart, long timeEnd) {
        return String.format(", duración: %d ms", timeEnd - timeStart);
    }
}
