package pl.thisismyway.objectchecksum;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import pl.thisismyway.updateobjectinlist.UpdateObjectInOneListBasedOnSecondOne;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class App {
    public static void main(String[] args) throws Exception {
        PrintStream out = new PrintStream(new FileOutputStream("benchmark_result_" + System.currentTimeMillis() + ".txt"));
        System.setOut(out);

        Options opt = new OptionsBuilder()
                // Specify which benchmarks to run.
                .include(ObjectStructurePrinting.class.getName() + ".*")
                .build();

        new Runner(opt).run();
    }
}
