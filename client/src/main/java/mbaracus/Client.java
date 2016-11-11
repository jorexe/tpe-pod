package mbaracus;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
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
        logger.info("52055-52108 Client Starting ...");

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

        HazelcastInstance client = getHzClient(parser);

        System.out.println(client.getCluster());

        IMap<String, CensoTuple> iMap = client.getMap(MAP_NAME);

        CensoReader.parseCsv(iMap, parser.getInputFile());

//        // Ahora el JobTracker y los Workers!
//        JobTracker tracker = client.getJobTracker("default");
//
//        // Ahora el Job desde los pares(key, Value) que precisa MapReduce
//        KeyValueSource<String, Votacion> source = KeyValueSource.fromMap(myMap);
//        Job<String, Votacion> job = tracker.newJob(source);
//
//        // // Orquestacion de Jobs y lanzamiento
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
}
