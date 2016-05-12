package com.upplication.cordova.util;


import com.upplication.cordova.CordovaConfig;

import java.io.IOException;

public interface ConfigTransactionJob {

    void exec(CordovaConfig config) throws IOException;
}
