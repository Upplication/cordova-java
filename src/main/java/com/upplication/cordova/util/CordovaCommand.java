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

    /**
     * Execute a list of cordova commands
     * @param command var args of cordova commands to exec
     * @return String with the output of the command
     *
     * @throws CordovaCommandException if the result is not exit with a 0
     *
     * @see CordovaCommand#exec(String[], Map)
     */
    public String exec(String ... command) {
        return exec(command, null);
    }

    /**
     * Execute a list of cordova commands
     * @param command List String of cordova commands
     * @return String with the output
     *
     * @throws CordovaCommandException if the result is not exit with a 0
     *
     * @see CordovaCommand#exec(String[], Map)
     */
    public String exec(List<String> command) {
        return exec(command.toArray(new String[command.size()]), null);
    }

    /**
     * Execute a list of commands using the environment and the project provided (if not null)
     * If the environment is not provided we execute all the commands AFTER the cordova command, so they must be
     * arguments for the cordova cli command.
     *
     * @param command Array of commands to execute in order, mandatory
     * @param newEnv Map with the environment to set to the ProcessBuilder if not null
     * @return String the output of the command
     * @throws CordovaCommandException if the result is not exit with a 0
     */
    public String exec(String[] command, Map<String,String> newEnv) {
        try {
            List<String> commands = new ArrayList<>();
            if (environment != null) {
                if (environment.getNodePath() != null)
                    commands.add(environment.getNodePath());
                if (environment.getCordovaPath() != null)
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
        catch (IOException e) {
            String message = "Cannot run the 'cordova' command";
            logger.error(message, e);
            throw new CordovaCommandException(message, e);
        }
        catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Retrieves the error of a process
     *
     * @param process Process
     * @return The error message
     * @throws IOException if the error stream is already closed or something unexpected happens
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
