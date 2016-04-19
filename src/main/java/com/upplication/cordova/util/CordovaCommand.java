package com.upplication.cordova.util;

import com.upplication.cordova.exception.CordovaCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CordovaCommand {

    private File project;
    private Environment environment;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CordovaCommand(Environment environment){
        this(null, environment);
    }

    public CordovaCommand(File project, Environment environment){
        this.project = project;
        this.environment = environment;
    }

    public String exec(String ... command) {
        try {
            List<String> commands = new ArrayList<>();
            if (environment != null) {
                commands.add(environment.getNodePath());
                commands.add(environment.getCordovaPath());
            } else {
                commands.add("cordova");
            }
            commands.addAll(Arrays.asList(command));

            ProcessBuilder processBuilder = new ProcessBuilder(commands)
                    .redirectErrorStream(true);

            if (project != null) {
                processBuilder.directory(project);
            }

            Process process = processBuilder.start();
            try (InputStream stream = process.getInputStream()){
                try (StringWriter output = new StringWriter()) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(stream));
                    String line;
                    boolean hasMoreLines = false;
                    while ((line = input.readLine()) != null) {
                        if (hasMoreLines) {
                            output.append("\n");
                        }
                        output.append(line);
                        logger.debug(line);
                        hasMoreLines = true;
                    }

                    if (process.waitFor() != 0) {
                        String msg = "Cordova command failed.\nWorking Directory: " + project +
                                ".\nCommands: " + Arrays.toString(command) +
                                ".\nCaused by: " + getErrorProcess(process) + output.toString();
                        logger.error(msg);
                        throw new CordovaCommandException(msg);
                    } else {

                        String result = output.toString();
                        logger.info("command result: " + result);

                        return result;
                    }
                }
            }
        }
        catch (IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Retrieves the error of a process
     *
     * @param process Process
     * @return Error
     * @throws IOException
     */
    public StringBuilder getErrorProcess(Process process) throws IOException {

        StringBuilder output = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = input.readLine()) != null) {
                output.append(line);
                output.append('\n');
            }

            return output;
        }
    }
}
