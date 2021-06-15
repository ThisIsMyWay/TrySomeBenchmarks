package pl.thisismyway.objectchecksum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import pl.thisismyway.objectchecksum.object.AnotherObject;
import pl.thisismyway.objectchecksum.object.ParentObject;
import pl.thisismyway.objectchecksum.object.SomeObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// The goal of this benchmark is to compare the ways of printing all the values from the object.
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class ObjectStructurePrinting {


    @Param({"0", "5", "10", "15", "20", "25", "30", "35", "40"})
    private int blackholeTokens;

    private ParentObject createExtensiveObject() {
        SomeObject someObject = new SomeObject(true, "looooooooooooooooooooooonnnnnnnnnnnnggggggggggggggg string what what what", 1231231, new AnotherObject("yet another"));

        Map<String, SomeObject> map = new HashMap<>();
        SomeObject firstObjectForMap = new SomeObject(false, "some string value", 423421, new AnotherObject("string in another value"));
        map.put("first_object", firstObjectForMap);
        SomeObject secondObjectForMap = new SomeObject(true, "another string value", 432, new AnotherObject("string in one another value"));
        map.put("second_object", secondObjectForMap);


        SomeObject firstObjectForList = new SomeObject(false, "one string valueee", 753, new AnotherObject("string in another valueee"));

        SomeObject secondObjectForList = new SomeObject(false, "some string valueee", 345, new AnotherObject("string in anotherdfd valueee"));

        List<SomeObject> listOfObjects = List.of(firstObjectForList, secondObjectForList);

        return new ParentObject(someObject, listOfObjects, map);
    }

    @Benchmark
    public String baseLine() {
        Blackhole.consumeCPU(blackholeTokens);
        return new String("fdas");
    }

    @Benchmark
    public String printingByReflectionToString() {
        Blackhole.consumeCPU(blackholeTokens);
        return ReflectionToStringBuilder.reflectionToString(createExtensiveObject());
    }

    @Benchmark
    public String printingByObjectMapper() throws JsonProcessingException {
        Blackhole.consumeCPU(blackholeTokens);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(createExtensiveObject());
    }

    @Benchmark
    public String printingByToString() {
        Blackhole.consumeCPU(blackholeTokens);
        return createExtensiveObject().toString();
    }
}
