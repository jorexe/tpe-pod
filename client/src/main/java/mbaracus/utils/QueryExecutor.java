package mbaracus.utils;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import mbaracus.model.CensoTuple;
import mbaracus.query1.model.AgeCount;
import mbaracus.query1.model.AgeType;
import mbaracus.query1.mr.Query1MapperFactory;
import mbaracus.query1.mr.Query1ReducerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class QueryExecutor {
    private static final String DEFAULT_JOB_TRACKER = "default";

    private HazelcastInstance client;
    private IMap<Integer, CensoTuple> iMap;
    private ArgumentParser parser;

    public QueryExecutor(HazelcastInstance client, IMap<Integer, CensoTuple> iMap, ArgumentParser parser) {
        this.client = client;
        this.iMap = iMap;
        this.parser = parser;
    }

    public void submit(Integer query) throws IOException, InterruptedException, ExecutionException{
        switch (query) {
            case 1:
                executeQuery1();
                break;
            case 2:
                executeQuery2();
                break;
            case 3:
                executeQuery3();
                break;
            case 4:
                executeQuery4();
                break;
            case 5:
                executeQuery5();
                break;
        }
    }

    private void executeQuery1() throws IOException, InterruptedException, ExecutionException {
        JobTracker tracker = client.getJobTracker(DEFAULT_JOB_TRACKER);
        KeyValueSource<Integer, CensoTuple> source = KeyValueSource.fromMap(iMap);
        Job<Integer, CensoTuple> job = tracker.newJob(source);

        ICompletableFuture<Map<AgeType, AgeCount>> future = job
                .mapper(new Query1MapperFactory())
                .reducer(new Query1ReducerFactory())
                .submit();

        Map<AgeType, AgeCount> result = future.get();

        QueryPrinters.printResultQuery1(parser.getOutputFile(), result.get(AgeType.A).count, result.get(AgeType.B).count, result.get(AgeType.C).count);
    }

    private void executeQuery2() throws IOException, InterruptedException, ExecutionException {
    }

    private void executeQuery3() throws IOException, InterruptedException, ExecutionException {
    }

    private void executeQuery4() throws IOException, InterruptedException, ExecutionException {
    }

    private void executeQuery5() throws IOException, InterruptedException, ExecutionException {
    }
}