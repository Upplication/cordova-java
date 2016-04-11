package com.upplication.cordova.util;

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
            if (process.waitFor() != 0) {
                String msg = "Cordova command failed.\nWorking Directory: " + project +
                        ".\nCommands: " + Arrays.toString(command) +
                        ".\nCaused by: " + getErrorProcess(process) + getProcessOutput(process);
                logger.error(msg);
                throw new IllegalStateException(msg);
            }

            String result = getProcessOutput(process).toString();
            logger.info(result);

            return result;
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
        return getProcessStream(process.getErrorStream());
    }

    /**
     * Retrieves the result of a process
     *
     * @param process Process
     * @return Result
     * @throws IOException
     */
    public StringBuilder getProcessOutput(Process process) throws IOException {
        return getProcessStream(process.getInputStream());
    }

    /**
     * Retrieves a process input stream as string builder
     *
     * @param stream Input stream
     * @return String builder
     * @throws IOException
     */
    private StringBuilder getProcessStream(InputStream stream) throws IOException {
        StringBuilder output = new StringBuilder();

        BufferedReader input = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = input.readLine()) != null) {
            output.append(line);
            output.append('\n');
        }

        return output;
    }
}
