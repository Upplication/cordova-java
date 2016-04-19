package com.upplication.cordova.junit;


import java.io.InputStream;
import java.util.Properties;

import com.upplication.cordova.Cordova;
import com.upplication.cordova.CordovaCLI;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class CordovaCLIRule implements TestRule {

    private CordovaCLI cordovaCLI;

    public CordovaCLI get() {
        return this.cordovaCLI;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Properties props = new Properties();

                try (InputStream streamResources = this.getClass().getResourceAsStream("/cordova.properties")){
                    if (streamResources != null) {
                        props.load(streamResources);
                    }
                }

                String nodePath = props.getProperty("node_path");
                String cordovaPath = props.getProperty("cordova_path");
                if (nodePath != null && !nodePath.isEmpty() &&
                        cordovaPath != null && !cordovaPath.isEmpty()) {
                    cordovaCLI = new Cordova(nodePath, cordovaPath).getCLI();
                } else {
                    cordovaCLI = new Cordova().getCLI();
                }

                try {
                    base.evaluate();
                } finally {
                    cordovaCLI = null;
                }
            }
        };
    }
}