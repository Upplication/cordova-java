package com.upplication.cordova.junit;

public class OnlyWindows implements ConditionRule.Condition {
    public boolean isSatisfied() {
        return System.getProperty("os.name").toLowerCase().contains("window");
    }
}
