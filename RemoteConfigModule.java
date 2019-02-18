package com.galihlprakoso.bridge.remoteconfig;

/*
    @author Galih Laras Prakoso | Github : galihlprakoso
 */

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class RemoteConfigModule extends ReactContextBaseJavaModule {

    private static final String NAME = "RemoteConfig";

    public static final String FETCH_ERROR = "REMOTE_CONFIG_ERROR";
    public static final String FETCH_SUCCEED = "REMOTE_CONFIG_FETCH_SUCCEED";
    public static final String FETCH_FAILED = "REMOTE_CONFIG_FETCH_FAILED";

    private RemoteConfig remoteConfig;

    public RemoteConfigModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.remoteConfig = new RemoteConfig(reactContext);
    }

    @Override
    public String getName() { return NAME; }

    @ReactMethod
    public void setDefaults(ReadableMap defaults) {
        remoteConfig.setDefaults(defaults);
    }
    @ReactMethod
    public void setDebugModeOn(){
       remoteConfig.setDebugModeOn();
    }
    @ReactMethod
    public void fetch(Integer chacheExpiration, final Promise promise){
        remoteConfig.fetch(chacheExpiration,promise);
    }
    @ReactMethod
    public void getBoolean(String key, final Promise promise){
        remoteConfig.getBoolean(key,promise);
    }
    @ReactMethod
    public void getString(String key, final Promise promise){
        remoteConfig.getString(key,promise);
    }
    @ReactMethod
    public void getDouble(String key, final Promise promise){
        remoteConfig.getDouble(key,promise);
    }
}
