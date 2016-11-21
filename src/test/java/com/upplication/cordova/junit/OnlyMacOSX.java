package com.upplication.cordova.junit;

public class OnlyMacOSX implements ConditionRule.Condition {
    public boolean isSatisfied() {
        return System.getProperty("os.name").toLowerCase().contains("mac os x");
    }
}
