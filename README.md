# react-native-firebase-remote-config
Simple Firebase Remote Config bridge for React Native.

1. Just create that two files in your android application folder ( app/src/main/java/<package.name>/ ).
2. Register the package on your MainApplication.java
```java
@Override
protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
            ...
            new RemoteConfigPackage()
    );
}
```
3. Create a file inside your ReactNative project. For example RemoteConfig.js and write these codes inside that file :
```javascript
import { NativeModules } from 'react-native';

const RemoteConfig = NativeModules.RemoteConfig;

module.exports = RemoteConfig;
```

4. Sample usage on React Native :

```javascript
import RemoteConfig from './RemoteConfig.js'

//Execute this code on your component lifecycle
RemoteConfig.setDefaults({
  image_url: 'https://kctexinternational.com/wp-content/uploads/2017/10/slider-img-1.png'
});
// Dont execute this on production
RemoteConfig.setDebugModeOn();

try {
  await RemoteConfig.fetch(0);
  const imageUrl = await RemoteConfig.getString('image_url');
  this.setState({ imageUrl });
} catch (e) {
  console.log(e);
}
```
