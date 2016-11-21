package com.upplication.cordova.config;


import com.upplication.cordova.CordovaConfig;
import com.upplication.cordova.CordovaProject;
import com.upplication.cordova.junit.CordovaCLIRule;
import com.upplication.cordova.util.ConfigTransactionJob;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AuthorIT {
    @Rule
    public CordovaCLIRule cordovaCLIRule = new CordovaCLIRule();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private CordovaProject cordova;

    @Before
    public void setUp() throws IOException {
        cordova = cordovaCLIRule.get().create(folder.newFolder("cordova-path"));
    }

    @Test
    public void set_author() throws IOException {
        String name = "cordova-author";

        cordova.config().author().setName(name);

        assertEquals(name, cordova.config().author().getName());
    }

    @Test
    public void set_href() throws IOException {
        String link = "http://author.me";

        cordova.config().author().setHref(link);

        assertEquals(link, cordova.config().author().getHref());
    }

    @Test
    public void set_email() throws IOException {
        String email = "auhtor@email.me";

        cordova.config().author().setEmail(email);

        assertEquals(email, cordova.config().author().getEmail());
    }

    @Test
    public void set_author_same_job() throws IOException {
        final String email = "auhtor@email.me";
        final String link = "http://author.me";
        final String name = "cordova-author";

        cordova.config(new ConfigTransactionJob() {
            @Override
            public void exec(CordovaConfig config) throws IOException {
                config.author().setEmail(email);
                config.author().setHref(link);
                config.author().setName(name);
            }
        });

        assertEquals(email, cordova.config().author().getEmail());
        assertEquals(link, cordova.config().author().getHref());
        assertEquals(name, cordova.config().author().getName());
    }
}