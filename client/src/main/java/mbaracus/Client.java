package mbaracus;

import mbaracus.utils.ArgumentParser;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
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
}
