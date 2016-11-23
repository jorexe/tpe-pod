package mbaracus;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import mbaracus.enumerators.HouseType;
import mbaracus.query1.model.AgeCount;
import mbaracus.query1.model.AgeType;
import mbaracus.query1.mr.Query1CombinerFactory;
import mbaracus.query1.mr.Query1MapperFactory;
import mbaracus.query1.mr.Query1ReducerFactory;
import mbaracus.query2.model.HouseCount;
import mbaracus.query2.model.HouseTypeMean;
import mbaracus.query2.mr.*;
import mbaracus.query3.model.DepartmentStat;
import mbaracus.query3.mr.AnalfabetCounterCombinerFactory;
import mbaracus.query3.mr.AnalfabetCounterMapperFactory;
import mbaracus.query3.mr.AnalfabetCounterReducerFactory;
import mbaracus.query4.model.Department;
import mbaracus.query4.mr.ProvinceCounterCombinerFactory;
import mbaracus.query4.mr.ProvinceCounterMapperFactory;
import mbaracus.query4.mr.ProvinceCounterReducerFactory;
import mbaracus.query5.model.DepartmentCount;
import mbaracus.query5.mr.DepartmentCounterCombinerFactory;
import mbaracus.query5.mr.DepartmentCounterMapperFactory;
import mbaracus.query5.mr.DepartmentCounterReducerFactory;
import mbaracus.tuples.CensoTuple;
import mbaracus.utils.ArgumentParser;
import mbaracus.utils.QueryPrinters;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

class QueryExecutor {
    private static final String DEFAULT_JOB_TRACKER = "default";

    private HazelcastInstance client;
    private IMap<Integer, CensoTuple> iMap;
    private ArgumentParser parser;

    QueryExecutor(HazelcastInstance client, IMap<Integer, CensoTuple> iMap, ArgumentParser parser) {
        this.client = client;
        this.iMap = iMap;
        this.parser = parser;
    }

    void submit(Integer query) throws IOException, InterruptedException, ExecutionException {
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
        Job<Integer, CensoTuple> job = getInitialJob();
        ICompletableFuture<Map<AgeType, AgeCount>> future;
        if (parser.useCombiners()) {
            future = job
                    .mapper(new Query1MapperFactory())
                    .combiner(new Query1CombinerFactory())
                    .reducer(new Query1ReducerFactory())
                    .submit();
        } else {
            future = job
                    .mapper(new Query1MapperFactory())
                    .reducer(new Query1ReducerFactory())
                    .submit();
        }

        Map<AgeType, AgeCount> result = future.get();

        QueryPrinters.printResultQuery1(parser.getOutputFile(), result.get(AgeType.A).count, result.get(AgeType.B).count, result.get(AgeType.C).count);
    }

    private void executeQuery2() throws IOException, InterruptedException, ExecutionException {
        Job<Integer, CensoTuple> job = getInitialJob();

        ICompletableFuture<Map<Integer, HouseCount>> future;
        if (parser.useCombiners()) {
            future = job
                    .mapper(new CounterMapperFactory())
                    .combiner(new CounterCombinerFactory())
                    .reducer(new CounterReducerFactory())
                    .submit();
        } else {
            future = job
                    .mapper(new CounterMapperFactory())
                    .reducer(new CounterReducerFactory())
                    .submit();
        }

        Map<Integer, HouseCount> result = future.get();
        IMap<Integer, HouseCount> meanMap = client.getMap("meanMap");
        meanMap.putAll(result);
        KeyValueSource<Integer, HouseCount> meanSource = KeyValueSource.fromMap(meanMap);

        Job<Integer, HouseCount> meanJob = getJobTracker().newJob(meanSource);
        ICompletableFuture<Map<HouseType, HouseTypeMean>> meanFuture;
        if (parser.useCombiners()) {
            meanFuture = meanJob
                .mapper(new MeanMapperFactory())
                .combiner(new MeanCombinerFactory())
                .reducer(new MeanReducerFactory())
                .submit();
        } else {
            meanFuture = meanJob
                .mapper(new MeanMapperFactory())
                .reducer(new MeanReducerFactory())
                .submit();
        }

        Map<HouseType, HouseTypeMean> meanResult = meanFuture.get();
        QueryPrinters.printResultQuery2(parser.getOutputFile(), meanResult);
        meanMap.clear();
    }

    private void executeQuery3() throws IOException, InterruptedException, ExecutionException {
        Job<Integer, CensoTuple> job = getInitialJob();

        ICompletableFuture<Map<Integer, DepartmentStat>> future;
        if (parser.useCombiners()) {
            future = job
                .mapper(new AnalfabetCounterMapperFactory())
                .combiner(new AnalfabetCounterCombinerFactory())
                .reducer(new AnalfabetCounterReducerFactory())
                .submit();
        } else {
            future = job
                .mapper(new AnalfabetCounterMapperFactory())
                .reducer(new AnalfabetCounterReducerFactory())
                .submit();
        }

        Map<Integer, DepartmentStat> result = future.get();
        List<DepartmentStat> list = result.values().stream().parallel()
                .sorted((departmentStat, t1) -> {
                    if (departmentStat.getIndex() < t1.getIndex()) return 1;
                    if (departmentStat.getIndex() > t1.getIndex()) return -1;
                    return 0;
                }).limit(parser.getDepartmentsCount()).collect(Collectors.toList());
        QueryPrinters.printResultQuery3(parser.getOutputFile(), list);
    }

    private void executeQuery4() throws IOException, InterruptedException, ExecutionException {
        Job<Integer, CensoTuple> job = getInitialJob();

        JobCompletableFuture<Map<Integer, Department>> future;
        if (parser.useCombiners()) {
            future = job
                .mapper(new ProvinceCounterMapperFactory())
                .combiner(new ProvinceCounterCombinerFactory())
                .reducer(new ProvinceCounterReducerFactory())
                .submit();
        } else {
            future = job
                .mapper(new ProvinceCounterMapperFactory())
                .reducer(new ProvinceCounterReducerFactory())
                .submit();
        }

        Map<Integer, Department> result = future.get();

        List<Department> filteredDepartments = result.values()
                .stream().parallel()
                .filter(department -> department.getNombreProv().equals(parser.getProvince()))
                .filter(department -> department.getHabitants() < parser.getHabitantsLimit())
                .sorted((d1, d2) -> (-1) * Integer.compare(d1.getHabitants(), d2.getHabitants())
                ).collect(Collectors.toList());

        QueryPrinters.printResultQuery4(parser.getOutputFile(), filteredDepartments);
    }

    private void executeQuery5() throws IOException, InterruptedException, ExecutionException {
        JobTracker tracker = client.getJobTracker(DEFAULT_JOB_TRACKER);
        KeyValueSource<Integer, CensoTuple> source = KeyValueSource.fromMap(iMap);
        Job<Integer, CensoTuple> job = tracker.newJob(source);
        JobCompletableFuture<Map<Integer, DepartmentCount>> future;
        if (parser.useCombiners()) {
            future = job
                .mapper(new DepartmentCounterMapperFactory())
                .combiner(new DepartmentCounterCombinerFactory())
                .reducer(new DepartmentCounterReducerFactory())
                .submit();
        } else {
            future = job
                .mapper(new DepartmentCounterMapperFactory())
                .reducer(new DepartmentCounterReducerFactory())
                .submit();
        }

        Map<Integer, DepartmentCount> result = future.get();
        QueryPrinters.printResultQuery5(parser.getOutputFile(), result);
    }

    private Job<Integer, CensoTuple> getInitialJob() {
        KeyValueSource<Integer, CensoTuple> source = KeyValueSource.fromMap(iMap);
        return getJobTracker().newJob(source);
    }

    private JobTracker getJobTracker() {
        return client.getJobTracker(DEFAULT_JOB_TRACKER);
    }
}