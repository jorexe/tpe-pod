package mbaracus.utils;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import mbaracus.enumerators.HouseType;
import mbaracus.model.CensoTuple;
import mbaracus.query1.model.AgeCount;
import mbaracus.query1.model.AgeType;
import mbaracus.query1.mr.Query1MapperFactory;
import mbaracus.query1.mr.Query1ReducerFactory;
import mbaracus.query2.model.HouseCount;
import mbaracus.query2.model.HouseTypeMean;
import mbaracus.query2.mr.CounterMapperFactory;
import mbaracus.query2.mr.CounterReducerFactory;
import mbaracus.query2.mr.MeanMapperFactory;
import mbaracus.query2.mr.MeanReducerFactory;

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
        JobTracker tracker = client.getJobTracker(DEFAULT_JOB_TRACKER);
        KeyValueSource<Integer, CensoTuple> source = KeyValueSource.fromMap(iMap);
        Job<Integer, CensoTuple> job = tracker.newJob(source);

        ICompletableFuture<Map<Integer, HouseCount>> future = job
                .mapper(new CounterMapperFactory())
                .reducer(new CounterReducerFactory())
                .submit();

        Map<Integer, HouseCount> result = future.get();
        IMap<Integer, HouseCount> meanMap = client.getMap("meanMap");
        meanMap.putAll(result);
        KeyValueSource<Integer, HouseCount> meanSource = KeyValueSource.fromMap(meanMap);

        Job<Integer, HouseCount> meanJob = tracker.newJob(meanSource);
        ICompletableFuture<Map<HouseType, HouseTypeMean>> meanFuture = meanJob
                .mapper(new MeanMapperFactory())
                .reducer(new MeanReducerFactory())
                .submit();

        Map<HouseType, HouseTypeMean> meanResult = meanFuture.get();
        QueryPrinters.printResultQuery2(parser.getOutputFile(), meanResult);
    }

    private void executeQuery3() throws IOException, InterruptedException, ExecutionException {
    }

    private void executeQuery4() throws IOException, InterruptedException, ExecutionException {
    }

    private void executeQuery5() throws IOException, InterruptedException, ExecutionException {
    }
}