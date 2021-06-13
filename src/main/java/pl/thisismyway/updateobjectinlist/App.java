package pl.thisismyway.updateobjectinlist;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class App {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                // Specify which benchmarks to run.
                .include(UpdateObjectInOneListBasedOnSecondOne.class.getName() + ".*")
                .build();

        new Runner(opt).run();
    }

}