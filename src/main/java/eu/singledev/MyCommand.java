package eu.singledev;

import picocli.CommandLine.Command;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

/**
 * @author Mike Kostenko on 15/11/2024
 */
@Command(name = "myCommand", description = "Example command with @Spec")
public class MyCommand implements Runnable {

    @Spec
    CommandSpec spec;

    @Override
    public void run() {
        // Use the injected CommandSpec
        System.out.println("Executing command: " + spec.name());
    }

    public static void main(String[] args) {
        picocli.CommandLine.run(new MyCommand(), args);
    }
}
