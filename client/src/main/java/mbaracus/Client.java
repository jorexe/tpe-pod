package mbaracus;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import mbaracus.reader.CensoReader;
import mbaracus.model.CensoTuple;
import mbaracus.utils.ArgumentParser;
import mbaracus.utils.QueryExecutor;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class Client {
    public static final String MAP_NAME = "censo-baracus";
    private static final String CLUSTER_NAME = null;
    private static final String CLUSTER_PASSWORD = null;
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
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
        IMap<String, CensoTuple> iMap = client.getMap(MAP_NAME);

        logger.info("Inicio de la lectura del archivo");
        startTime = System.currentTimeMillis();

        CensoReader.parseCsv(iMap, parser.getInputFile());

        endTime = System.currentTimeMillis();
        logger.info("Fin de la lectura del archivo" + timeDuration(startTime, endTime));

        logger.info("Inicio del trabajo map/reduce");
        startTime = System.currentTimeMillis();

        QueryExecutor executor = new QueryExecutor(client, iMap, parser);
        executor.submit(parser.getQuery());

        endTime = System.currentTimeMillis();
        logger.info("Fin del trabajo map/reduce" + timeDuration(startTime, endTime));

        System.exit(0);
    }

    private static HazelcastInstance getHzClient(ArgumentParser parser) {
        ClientConfig ccfg = new ClientConfig();
        if (CLUSTER_NAME != null && CLUSTER_PASSWORD != null) {
            ccfg.getGroupConfig().setName(CLUSTER_NAME).setPassword(CLUSTER_PASSWORD);
        }

        String[] arrayAddresses = new String[2];
        arrayAddresses[0] = parser.getIp1().getHostAddress();
        arrayAddresses[1] = parser.getIp2().getHostAddress();
        ClientNetworkConfig net = new ClientNetworkConfig();
        net.addAddress(arrayAddresses);
        ccfg.setNetworkConfig(net);

        return HazelcastClient.newHazelcastClient(ccfg);
    }

    private static String timeDuration(long timeStart, long timeEnd) {
        return String.format(", duración: %d ms", timeEnd - timeStart);
    }
}