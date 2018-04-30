package com.upplication.cordova.commands;

import com.upplication.cordova.*;
import com.upplication.cordova.junit.CordovaCLIRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VersionIT {

    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    private CordovaCLI cordovaCLI;

    @Before
    public void setUp() {
        cordovaCLI = cordovaCLIRule.get();
    }

    @Test
    public void version() throws IOException {
        String version = cordovaCLI.getVersion();
        assertNotNull(version);
        assertEquals("8.0.0", version);
    }
}
