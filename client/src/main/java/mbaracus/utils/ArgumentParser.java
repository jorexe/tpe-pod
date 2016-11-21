package mbaracus.utils;

import org.apache.commons.cli.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArgumentParser {
    private static final String ARG_ADDRESSES = "Daddresses";
    private static final String ARG_QUERY = "Dquery";
    private static final String ARG_N = "Dn";
    private static final String ARG_PROV = "Dprov";
    private static final String ARG_TOPE = "Dtope";
    private static final String ARG_INPATH = "DinPath";
    private static final String ARG_OUTPATH = "DoutPath";
    private static final String ARG_PASSWORD = "Dpass";
    private static final String ARG_GROUP = "Dgroup";
    private static final String ARG_COMBINERS = "c";
    private static final String ARG_REUSE = "Dreuse";

    private final List<Integer> validQueries;

    private Options options;
    private CommandLine cmd;
    private Integer query;
    private Path inputFile;
    private Path outputFile;
    private InetAddress clusterIP;
    private Integer departmentsCount;
    private Integer habitantsLimit;
    private String province;
    private String clusterPassword;
    private String clusterName;
    private boolean useCombiners;
    private boolean reuseMap;

    public ArgumentParser() {
        this.validQueries = new ArrayList<>(5);
        addValidQueries();
        addOptions();
    }

    private void addValidQueries() {
        validQueries.add(1);
        validQueries.add(2);
        validQueries.add(3);
        validQueries.add(4);
        validQueries.add(5);
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

        options.addOption(Option.builder()
                .argName(ARG_PASSWORD + "=value")
                .hasArg()
                .valueSeparator()
                .desc("clusterPassword")
                .longOpt(ARG_PASSWORD)
                .required(false)
                .build());

        options.addOption(Option.builder()
                .argName(ARG_GROUP + "=value")
                .hasArg()
                .valueSeparator()
                .desc("clusterName")
                .longOpt(ARG_GROUP)
                .required(false)
                .build());
        options.addOption(ARG_REUSE, false, "reuse map");
        options.addOption(ARG_COMBINERS, false, "enable combinators");
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
        clusterIP = InetAddress.getByName(cmd.getOptionValue(ARG_ADDRESSES));
        clusterPassword = cmd.getOptionValue(ARG_PASSWORD);
        clusterName = cmd.getOptionValue(ARG_GROUP);
        useCombiners = cmd.hasOption(ARG_COMBINERS);
        reuseMap = cmd.hasOption(ARG_REUSE);

        if (!validQueries.contains(query)) {
            throw new ParseException("invalid query value");
        }

        // Combined parameters
        if (cmd.getOptionValue(ARG_QUERY).equals("3")) {
            if (!cmd.hasOption(ARG_N)) {
                throw new ParseException("n is mandatory");
            }
            departmentsCount = Integer.parseInt(cmd.getOptionValue(ARG_N));
        }
        if (cmd.getOptionValue(ARG_QUERY).equals("4")) {
            if (!cmd.hasOption(ARG_PROV)) {
                throw new ParseException("prov is mandatory");
            }
            if (!cmd.hasOption(ARG_TOPE)) {
                throw new ParseException("tope is mandatory");
            }
            province = cmd.getOptionValue(ARG_PROV);
            habitantsLimit = Integer.parseInt(cmd.getOptionValue(ARG_TOPE));
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

    public InetAddress getClusterIP() {
        return clusterIP;
    }

    public Integer getDepartmentsCount() {
        return departmentsCount;
    }

    public Integer getHabitantsLimit() {
        return habitantsLimit;
    }

    public String getProvince() {
        return province;
    }

    public String getClusterPassword() {
        return clusterPassword;
    }

    public String getClusterName() {
        return clusterName;
    }

    public boolean useCombiners() {
        return useCombiners;
    }

    public boolean reuseMap() {
        return reuseMap;
    }
}
