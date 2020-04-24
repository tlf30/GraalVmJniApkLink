# GraalVmJniApkLink

DISCLAIMER: This was an experement, it does not actually work for loading JME in Android with GraalVM. But does show the process of communicating between android and Graal.  
  
First, get GraalVM: https://download2.gluonhq.com/substrate/graalvm/graalvm-svm-linux-20.1.0-ea+28.zip  
Now, set your PATH, JAVA_HOME, and GRAALVM_HOME. Both JAVA_HOME and GRAALVM_HOME will point to graalVM.  
  
Next, you need to build the bridge.  
Go into the `bridge/` folder, and run:  
`./gradlew build nativeBuild`  
If everything is setup correctly, it will build and produce some `.so` files which we will round up, and copy into the `OutsideAndroid/outside/arm64-v8a` folder.  
The first one is located `bridge/build/client/aarch64-android/liboutside-android-bridge.so`  
The other two are located: `bridge/build/client/aarch64-android/gvm/apk/lib/arm64-v8a` they are `libfreetype.so` and `libsubstrate.so`.  
Copy these into the `OutsideAndroid/outside/arm64-v8a` folder.  
  
Finally we can build the android apk for jme.  
In Android Studio, open the `OutsideAndroid` folder as a project, and build the app.  
  
From here you can deploy it to any android device version 28+, and that support arm64-v8. 
