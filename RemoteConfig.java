package com.galihlprakoso.bridge.remoteconfig;

/*
    @author Galih Laras Prakoso | Github : galihlprakoso
 */

import android.support.annotation.NonNull;
import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

class RNSboxRemoteConfig implements RemoteConfig{

    private static final String NAME = "RemoteConfig";

    private ReactApplicationContext reactContext;
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

    public RNSboxRemoteConfig(ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
        mFirebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder().build());
    }

    public void setDefaults(ReadableMap defaults){
        mFirebaseRemoteConfig.setDefaults(defaults.toHashMap());
    }

    public void setDebugModeOn(){
        mFirebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build());
    }

    public void fetch(Integer chacheExpiration, final Promise promise){
        if(this.reactContext.getCurrentActivity() != null){
            mFirebaseRemoteConfig.fetch(chacheExpiration)
                    .addOnCompleteListener(this.reactContext.getCurrentActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mFirebaseRemoteConfig.activateFetched();
                                promise.resolve(RNSboxRemoteConfigModule.FETCH_SUCCEED);
                                Log.e(NAME, RNSboxRemoteConfigModule.FETCH_SUCCEED);
                            }else{
                                promise.reject(RNSboxRemoteConfigModule.FETCH_FAILED, "Task failed.");
                            }
                        }
                    });
        }else{
            promise.reject(RNSboxRemoteConfigModule.FETCH_ERROR, "Activity doesn't exists.");
        }
    }

    public void getBoolean(String key, final Promise promise){
        try{
            Boolean result = mFirebaseRemoteConfig.getBoolean(key);
            promise.resolve(result);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    public void getString(String key, final Promise promise){
        try{
            String result = mFirebaseRemoteConfig.getString(key);
            promise.resolve(result);
        }catch (Exception e){
            promise.reject(e);
        }
    }

    public void getDouble(String key, final Promise promise){
        try{
            Double result = mFirebaseRemoteConfig.getDouble(key);
            promise.resolve(result);
        }catch (Exception e){
            promise.reject(e);
        }
    }
}
