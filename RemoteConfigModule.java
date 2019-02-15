package com.sayurbox.bridge.remoteconfig;

/*
    @author Galih Laras Prakoso | Github : galihlprakoso
 */

import android.support.annotation.NonNull;
import android.util.Log;
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

    private static final String NAME = "RemoteConfig";
    public static final String FETCH_ERROR = "REMOTE_CONFIG_ERROR";
    public static final String FETCH_SUCCEED = "REMOTE_CONFIG_FETCH_SUCCEED";
    public static final String FETCH_FAILED = "REMOTE_CONFIG_FETCH_FAILED";

    private ReactApplicationContext reactContext;
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

    public RemoteConfigModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        mFirebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder().build());
    }

    @Override
    public String getName() { return NAME; }

    @ReactMethod
    public void setDefaults(ReadableMap defaults){
        mFirebaseRemoteConfig.setDefaults(defaults.toHashMap());
    }

    @ReactMethod
    public void setDebugModeOn(){
        mFirebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build());
    }

    @ReactMethod
    public void fetch(Integer chacheExpiration, final Promise promise){
        if(this.reactContext.getCurrentActivity() != null){
            mFirebaseRemoteConfig.fetch(chacheExpiration)
                    .addOnCompleteListener(this.reactContext.getCurrentActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mFirebaseRemoteConfig.activateFetched();
                                promise.resolve(FETCH_SUCCEED);
                                Log.e(NAME, FETCH_SUCCEED);
                            }else{
                                promise.reject(FETCH_FAILED, "Task failed.");
                            }
                        }
                    });
        }else{
            promise.reject(FETCH_ERROR, "Activity doesn't exists.");
        }
    }

    @ReactMethod
    public void getBoolean(String key, final Promise promise){
        try{
            Boolean result = mFirebaseRemoteConfig.getBoolean(key);
            promise.resolve(result);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getString(String key, final Promise promise){
        try{
            String result = mFirebaseRemoteConfig.getString(key);
            promise.resolve(result);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getDouble(String key, final Promise promise){
        try{
            Double result = mFirebaseRemoteConfig.getDouble(key);
            promise.resolve(result);
        }catch (Exception e){
            promise.reject(e);
        }
    }
}
