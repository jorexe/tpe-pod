package mbaracus.client;

import org.apache.commons.cli.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArgumentParser {
    public static final String ARG_ADDRESSES = "Daddresses";
    public static final String ARG_QUERY = "Dquery";
    public static final String ARG_N = "Dn";
    public static final String ARG_PROV = "Dprov";
    public static final String ARG_TOPE = "Dtope";
    public static final String ARG_INPATH = "DinPath";
    public static final String ARG_OUTPATH = "DoutPath";

    public final List<Integer> validQueries;

    private Options options;
    private CommandLine cmd;
    private Integer query;
    private Path inputFile;
    private Path outputFile;
    private InetAddress ip1;
    private InetAddress ip2;
    private String departmentsCount;
    private String habitantsLimit;
    private String province;

    public ArgumentParser() {
        this.validQueries = new ArrayList<>(4);
        addValidQueries();
        addOptions();
    }

    private void addValidQueries() {
        validQueries.add(1);
        validQueries.add(2);
        validQueries.add(3);
        validQueries.add(4);
    }

    private void addOptions() {
        this.options = new Options();

        options.addOption(Option.builder()
                .argName(ARG_ADDRESSES + "=value")
                .hasArg()
                .valueSeparator()
                .desc("IP addresses of nodes")
                .longOpt(ARG_ADDRESSES)
                .required()
                .build());

        options.addOption(Option.builder()
                .argName(ARG_QUERY + "=value")
                .hasArg()
                .valueSeparator()
                .desc("query option")
                .longOpt(ARG_QUERY)
                .required()
                .build());

        options.addOption(Option.builder()
                .argName(ARG_N + "=value")
                .hasArg()
                .valueSeparator()
                .desc("for query option 3")
                .longOpt(ARG_N)
                .required(false)
                .build());

        options.addOption(Option.builder()
                .argName(ARG_PROV + "=value")
                .hasArg().valueSeparator()
                .desc("for query option 4")
                .longOpt(ARG_PROV)
                .required(false)
                .build());

        options.addOption(Option.builder()
                .argName(ARG_TOPE + "=value")
                .hasArg().valueSeparator()
                .desc("for query option 4")
                .longOpt(ARG_TOPE)
                .required(false)
                .build());

        options.addOption(Option.builder()
                .argName(ARG_INPATH + "=value")
                .hasArg()
                .valueSeparator()
                .desc("name of the input file")
                .longOpt(ARG_INPATH)
                .required()
                .build());

        options.addOption(Option.builder()
                .argName(ARG_OUTPATH + "=value")
                .hasArg()
                .valueSeparator()
                .desc("name of the output file")
                .longOpt(ARG_OUTPATH)
                .required()
                .build());
    }

    public void parse(String[] args) throws ParseException, UnknownHostException {
        CommandLineParser parser = new DefaultParser();
        this.cmd = parser.parse(options, args);
        assertOptions();
    }

    // Verify the mandatory data
    private void assertOptions() throws ParseException, UnknownHostException {
        // Retrieval of values and type checking them
        query = Integer.valueOf(cmd.getOptionValue(ARG_QUERY));
        inputFile = Paths.get(cmd.getOptionValue(ARG_INPATH));
        outputFile = Paths.get(cmd.getOptionValue(ARG_OUTPATH));
        String[] ips = cmd.getOptionValue(ARG_ADDRESSES).split(";");
        ip1 = InetAddress.getByName(ips[0]);
        ip2 = InetAddress.getByName(ips[1]);
        departmentsCount = cmd.getOptionValue(ARG_N);
        habitantsLimit = cmd.getOptionValue(ARG_TOPE);
        province = cmd.getOptionValue(ARG_PROV);
        if (!validQueries.contains(query)) {
            throw new ParseException("invalid query value");
        }

        // Combined parameters
        if (cmd.getOptionValue(ARG_QUERY).equals("3")) {
            if (cmd.hasOption(ARG_N)) {
                throw new ParseException("n is mandatory");
            }
        }
        if (cmd.getOptionValue(ARG_QUERY).equals("4")) {
            if (cmd.hasOption(ARG_PROV)) {
                throw new ParseException("prov is mandatory");
            }
            if (cmd.hasOption(ARG_TOPE)) {
                throw new ParseException("tope is mandatory");
            }
        }
    }

    public Integer getQuery() {
        return query;
    }

    public Path getInputFile() {
        return inputFile;
    }

    public Path getOutputFile() {
        return outputFile;
    }

    public InetAddress getIp1() {
        return ip1;
    }

    public InetAddress getIp2() {
        return ip2;
    }

    public String getDepartmentsCount() {
        return departmentsCount;
    }

    public String getHabitantsLimit() {
        return habitantsLimit;
    }

    public String getProvince() {
        return province;
    }
}
