package com.upplication.cordova;

import com.upplication.cordova.util.CordovaCommand;
import com.upplication.cordova.util.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CordovaCLI {

    private Environment environment;

    public CordovaCLI(Environment environment){
        this.environment = environment;
    }

    /**
     * create a Cordova project with default reverse domain style identifer
     * 'io.cordova.hellocordova' and a default name 'HelloCordova'
     * @param path File, empty directory that already exists
     * @return CordovaProject
     */
    public CordovaProject create(File path) {
        return create(path, null, null);
    }

    /**
     * create a Cordova project with default name 'HelloCordova'
     * @param path File, empty directory that already exists
     * @param reverseDomainStyleIdentifier String, if null its use: 'io.cordova.hellocordova'
     * @return CordovaProject
     */
    public CordovaProject create(File path, String reverseDomainStyleIdentifier) {
        return create(path, reverseDomainStyleIdentifier, null);
    }

    /**
     * create a Cordova project
     * @param path File, empty directory that already exists
     * @param reverseDomainStyleIdentifier String, if null its use: 'io.cordova.hellocordova'
     * @param displayName String, if null its use 'HelloCordova'
     * @return CordovaProject
     */
    public CordovaProject create(File path, String reverseDomainStyleIdentifier, String displayName) {

        File folderParent = new File(path.getParent());
        String folderProject = path.getName();

        List<String> commands = new ArrayList<>();
        commands.add("create");
        commands.add(folderProject);
        if (reverseDomainStyleIdentifier != null)
            commands.add(reverseDomainStyleIdentifier);
        if (displayName != null)
            commands.add(displayName);

        new CordovaCommand(folderParent, environment).exec(commands.toArray(new String[0]));

        return new CordovaProject(path, environment);
    }
}
