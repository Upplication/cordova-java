package com.upplication.cordova.util;


import com.upplication.cordova.exception.CordovaCommandException;
import com.upplication.cordova.junit.Condition;
import com.upplication.cordova.junit.ConditionRule;
import com.upplication.cordova.junit.OnlyMacOSX;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class CordovaCommandIT {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    @Rule
    public ConditionRule rule = new ConditionRule();

    @Test(expected = CordovaCommandException.class)
    public void command_with_no_cordova_path() throws IOException {
        CordovaCommand cordovaCommand = new CordovaCommand(folder.newFolder(), null);
        String result = cordovaCommand.exec(new String[]{"create"}, null);

        assertNotNull(result);
    }

    @Test
    @Condition(OnlyMacOSX.class)
    public void cordova_create_project_macosx() throws IOException {

        CordovaCommand cordovaCommand = new CordovaCommand(folder.newFolder(), null);
        String result = cordovaCommand.exec("create", "hellou a", "es.upplication.ios23424", "hellou a");

        assertNotNull(result);
    }
}
