package com.upplication.cordova.util;


import com.upplication.cordova.exception.CordovaCommandException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class CordovaCommandIT {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = CordovaCommandException.class)
    public void command_with_no_cordova_path() throws IOException {
        CordovaCommand cordovaCommand = new CordovaCommand(folder.newFolder(), null);
        Map<String, String> envs = new HashMap<>();
        envs.put("PATH", "/usr/bin:/bin:/usr/sbin:/sbin");

        String result = cordovaCommand.exec(new String[]{"create"}, envs);

        assertNotNull(result);

    }

    @Test
    public void spike() throws IOException {
        Environment environment = new Environment();
        environment.setNodePath("/Usr/local/bin/node");
        environment.setCordovaPath("/Usr/local/bin/cordova");
        CordovaCommand cordovaCommand = new CordovaCommand(folder.newFolder(), environment);
        Map<String, String> envs = new HashMap<>();
        envs.put("PATH", "/usr/bin:/bin:/usr/sbin:/sbin");

        String result = cordovaCommand.exec(new String[]{"create", "hellou a", "es.upplication.ios23424", "hellou a"}, envs);

        assertNotNull(result);

    }
}
