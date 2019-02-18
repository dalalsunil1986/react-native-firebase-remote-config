package com.galihlprakoso.bridge.remoteconfig;

/*
    @author Galih Laras Prakoso | Github : galihlprakoso
 */

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;

public interface RemoteConfigInterface {
    public void setDefaults(ReadableMap defaults);
    public void setDebugModeOn();
    public void fetch(Integer chacheExpiration, final Promise promise);
    public void getBoolean(String key, final Promise promise);
    public void getString(String key, final Promise promise);
    public void getDouble(String key, final Promise promise);
}
