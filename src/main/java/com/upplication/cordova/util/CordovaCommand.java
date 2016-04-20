package com.upplication.cordova.util;

import com.upplication.cordova.exception.CordovaCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Helper to exec cordova command
 */
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
        return exec(command, null);
    }

    public String exec(String[] command, Map<String,String> newEnv) {
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

            if (newEnv != null) {
                Map<String, String> env = processBuilder.environment();
                env.putAll(newEnv);
            }

            if (project != null) {
                processBuilder.directory(project);
            }

            Process process = processBuilder.start();
            try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))){
                try (StringWriter output = new StringWriter()) {
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
                                ".\nCommands: " + commands +
                                ".\nCaused by: " + getErrorProcess(process) + output.toString();
                        logger.error(msg);
                        throw new CordovaCommandException(msg);
                    } else {

                        String result = output.toString();
                        logger.info("Command: " + commands + " with result:\n" + result);

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
