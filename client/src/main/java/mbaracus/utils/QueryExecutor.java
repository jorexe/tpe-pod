package mbaracus.utils;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobCompletableFuture;
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
import mbaracus.query3.model.DepartmentStat;
import mbaracus.query3.mr.AnalfabetCounterMapperFactory;
import mbaracus.query3.mr.AnalfabetCounterReducerFactory;
import mbaracus.query4.model.Department;
import mbaracus.query4.mr.ProvinceCounterMapperFactory;
import mbaracus.query4.mr.ProvinceCounterReducerFactory;
import mbaracus.query5.model.DepartmentCount;
import mbaracus.query5.mr.DepartmentCounterMapperFactory;
import mbaracus.query5.mr.DepartmentCounterReducerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
        Job<Integer, CensoTuple> job = getInitialJob();

        ICompletableFuture<Map<AgeType, AgeCount>> future = job
                .mapper(new Query1MapperFactory())
                .reducer(new Query1ReducerFactory())
                .submit();

        Map<AgeType, AgeCount> result = future.get();

        QueryPrinters.printResultQuery1(parser.getOutputFile(), result.get(AgeType.A).count, result.get(AgeType.B).count, result.get(AgeType.C).count);
    }

    private void executeQuery2() throws IOException, InterruptedException, ExecutionException {
        Job<Integer, CensoTuple> job = getInitialJob();

        ICompletableFuture<Map<Integer, HouseCount>> future = job
                .mapper(new CounterMapperFactory())
                .reducer(new CounterReducerFactory())
                .submit();

        Map<Integer, HouseCount> result = future.get();
        IMap<Integer, HouseCount> meanMap = client.getMap("meanMap");
        meanMap.putAll(result);
        KeyValueSource<Integer, HouseCount> meanSource = KeyValueSource.fromMap(meanMap);

        Job<Integer, HouseCount> meanJob = getJobTracker().newJob(meanSource);
        ICompletableFuture<Map<HouseType, HouseTypeMean>> meanFuture = meanJob
                .mapper(new MeanMapperFactory())
                .reducer(new MeanReducerFactory())
                .submit();

        Map<HouseType, HouseTypeMean> meanResult = meanFuture.get();
        QueryPrinters.printResultQuery2(parser.getOutputFile(), meanResult);
    }

    private void executeQuery3() throws IOException, InterruptedException, ExecutionException {
        Job<Integer, CensoTuple> job = getInitialJob();

        ICompletableFuture<Map<Integer, DepartmentStat>> future = job
                .mapper(new AnalfabetCounterMapperFactory())
                .reducer(new AnalfabetCounterReducerFactory())
                .submit();

        Map<Integer, DepartmentStat> result = future.get();
        List<DepartmentStat> list = result.values().stream().parallel().sorted((departmentStat, t1) -> {
            if (departmentStat.getIndex() < t1.getIndex()) return 1;
            if (departmentStat.getIndex() > t1.getIndex()) return -1;
            return 0;
        }).limit(parser.getDepartmentsCount()).collect(Collectors.toList());

        QueryPrinters.printResultQuery3(parser.getOutputFile(), list);
    }

    private void executeQuery4() throws IOException, InterruptedException, ExecutionException {
        Job<Integer, CensoTuple> job = getInitialJob();

        JobCompletableFuture<Map<Integer, Department>> future = job
                .mapper(new ProvinceCounterMapperFactory())
                .reducer(new ProvinceCounterReducerFactory())
                .submit();

        Map<Integer, Department> result = future.get();

        List<Department> filteredDepartments = result.values()
                .stream().filter(department -> department.getHabitants() < parser.getHabitantsLimit())
                .collect(Collectors.toList());

        QueryPrinters.printResultQuery4(parser.getOutputFile(), filteredDepartments);
    }

    private void executeQuery5() throws IOException, InterruptedException, ExecutionException {
        JobTracker tracker = client.getJobTracker(DEFAULT_JOB_TRACKER);
        KeyValueSource<Integer, CensoTuple> source = KeyValueSource.fromMap(iMap);
        Job<Integer, CensoTuple> job = tracker.newJob(source);

        ICompletableFuture<Map<String, DepartmentCount>> future = job
                .mapper(new DepartmentCounterMapperFactory())
                .reducer(new DepartmentCounterReducerFactory())
                .submit();

        Map<String, DepartmentCount> result = future.get();
        Map<Integer, List<String>> departments = new ConcurrentHashMap<>();
        result.keySet().stream().parallel().forEach(x -> {
            DepartmentCount departmentCount = result.get(x);
            int thousands = getThousandsFromInteger(departmentCount.count);
            departments.putIfAbsent(thousands, Collections.synchronizedList(new ArrayList<>()));
            departments.get(thousands).add(departmentCount.departmentName);
        });
        QueryPrinters.printResultQuery5(parser.getOutputFile(), departments);
    }

    private int getThousandsFromInteger(int a) {
        return a / 100;
    }

    private Job<Integer, CensoTuple> getInitialJob() {
        KeyValueSource<Integer, CensoTuple> source = KeyValueSource.fromMap(iMap);
        return getJobTracker().newJob(source);
    }

    private JobTracker getJobTracker() {
        return client.getJobTracker(DEFAULT_JOB_TRACKER);
    }
}