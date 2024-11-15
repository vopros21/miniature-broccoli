package eu.singledev;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mike Kostenko on 15/11/2024
 */
@Command(name = "datefinder", usageHelpWidth = 200, mixinStandardHelpOptions = true, version = "datefinder 1.0",
        parameterListHeading = "%n@|bold,underline Parameters|@:%n",
        optionListHeading = "%n@|bold,underline Options|@:%n",
        description = "Finds and prints dates in a given file.")
public class DateFinder implements Callable<Integer> {

    @Parameters(index = "0" ,paramLabel = "FILEPATH", description = "The file path to scan for dates.")
    private String filePath;

    @Option(names = "-o", paramLabel = "OUTPUT with a very long label", description = "Output file. If not specified, output to console.")
    private String outputFile;

    @Override
    public Integer call() throws Exception {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            Pattern datePattern = Pattern.compile("\\b\\d{4}-\\d{2}-\\d{2}\\b");

            for (String line : lines) {
                Matcher matcher = datePattern.matcher(line);
                while (matcher.find()) {
                    System.out.println("Found date: " + matcher.group());
                }
            }

            return 0;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return 1;
        }
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new DateFinder());
        commandLine.setUsageHelpLongOptionsMaxWidth(40);
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
