package com.sayurbox.bridge.remoteconfig;

/*
    @author Galih Laras Prakoso | Github : galihlprakoso
 */

import android.support.annotation.NonNull;
import com.facebook.react.BuildConfig;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class RemoteConfigModule extends ReactContextBaseJavaModule {

    public static final String NAME = "RemoteConfig";
    public static final String ERROR = "REMOTE_CONFIG_ERROR";
    public static final String FETCH_SUCCEED = "REMOTE_CONFIG_FETCH_SUCCEED";
    public static final String FETCH_FAILED = "REMOTE_CONFIG_FETCH_FAILED";

    private ReactApplicationContext reactContext;
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private FirebaseRemoteConfigSettings mConfigSettings = new FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build();

    public RemoteConfigModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        mFirebaseRemoteConfig.setConfigSettings(mConfigSettings);
    }

    @Override
    public String getName() { return NAME; }

    @ReactMethod
    public void setDefaults(ReadableMap defaults){
        mFirebaseRemoteConfig.setDefaults(defaults.toHashMap());
    }

    @ReactMethod
    public void fetch(Integer chacheExpiration, final Promise promise){
        if(this.reactContext.getCurrentActivity() != null){
            mFirebaseRemoteConfig.fetch(chacheExpiration.longValue())
                .addOnCompleteListener(this.reactContext.getCurrentActivity(), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        mFirebaseRemoteConfig.activateFetched();
                        promise.resolve(FETCH_SUCCEED);
                    }else{
                        promise.resolve(FETCH_FAILED);
                    }
                }
            });
        }else{
            promise.reject(ERROR, "Activity doesn't exists.");
        }
    }

    @ReactMethod
    public Boolean getBoolean(String key){
        return mFirebaseRemoteConfig.getBoolean(key);
    }

    @ReactMethod
    public String getString(String key){
        return mFirebaseRemoteConfig.getString(key);
    }

    @ReactMethod
    public Double getDouble(String key){
        return mFirebaseRemoteConfig.getDouble(key);
    }
}
