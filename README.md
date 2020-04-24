# GraalVmJniApkLink

DISCLAIMER: This was an experement, it does not actually work for loading JME in Android with GraalVM. But does show the process of communicating between android and Graal.  
  
First, get GraalVM: https://download2.gluonhq.com/substrate/graalvm/graalvm-svm-linux-20.1.0-ea+28.zip  
Now, set your PATH, JAVA_HOME, and GRAALVM_HOME. Both JAVA_HOME and GRAALVM_HOME will point to graalVM.  
  
Next, you need to build the bridge.  
Go into the `bridge/` folder, and run:  
`./gradlew build nativeBuild nativePackage`  
If everything is setup correctly, it will build and produce some `.so` files which we will round up, and copy into the `OutsideAndroid/outside/arm64-v8a` folder.  
The first one is located `bridge/build/client/aarch64-android/liboutside-android-bridge.so`  
The other two are located: `bridge/build/client/aarch64-android/gvm/apk/lib/arm64-v8a` they are `libfreetype.so` and `libsubstrate.so`.  
Copy these into the `OutsideAndroid/outside/arm64-v8a` folder.  
  
Finally we can build the android apk for jme.  
In Android Studio, open the `OutsideAndroid` folder as a project, and build the app.  
  
From here you can deploy it to any android device version 28+, and that support arm64-v8. 
  
  
  
  Running this will produce a error, caused by the JNI linkage. 
  ```
  04/23 16:29:15: Launching 'app' on BullittGroupLimited S61.
$ adb shell am start -n "io.tlf.outside.android/io.tlf.outside.android.FullscreenActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
Connected to process 21870 on device 'bullittgrouplimited-s61-S611825033339'.
Capturing and displaying logcat messages from application. This behavior can be disabled in the "Logcat output" section of the "Debugger" settings page.
I/Perf: Connecting to perf service.
W/outside.androi: Accessing hidden method Landroid/graphics/drawable/Drawable;->getOpticalInsets()Landroid/graphics/Insets; (light greylist, linking)
    Accessing hidden field Landroid/graphics/Insets;->left:I (light greylist, linking)
    Accessing hidden field Landroid/graphics/Insets;->right:I (light greylist, linking)
    Accessing hidden field Landroid/graphics/Insets;->top:I (light greylist, linking)
W/outside.androi: Accessing hidden field Landroid/graphics/Insets;->bottom:I (light greylist, linking)
W/outside.androi: Accessing hidden method Landroid/view/View;->computeFitSystemWindows(Landroid/graphics/Rect;Landroid/graphics/Rect;)Z (light greylist, reflection)
W/outside.androi: Accessing hidden method Landroid/view/ViewGroup;->makeOptionalFitsSystemWindows()V (light greylist, reflection)
W/outside.androi: Accessing hidden method Landroid/widget/TextView;->getTextDirectionHeuristic()Landroid/text/TextDirectionHeuristic; (light greylist, linking)
W/outside.androi: Accessing hidden method Landroid/graphics/FontFamily;-><init>()V (light greylist, reflection)
    Accessing hidden method Landroid/graphics/FontFamily;->addFontFromAssetManager(Landroid/content/res/AssetManager;Ljava/lang/String;IZIII[Landroid/graphics/fonts/FontVariationAxis;)Z (light greylist, reflection)
    Accessing hidden method Landroid/graphics/FontFamily;->addFontFromBuffer(Ljava/nio/ByteBuffer;I[Landroid/graphics/fonts/FontVariationAxis;II)Z (light greylist, reflection)
    Accessing hidden method Landroid/graphics/FontFamily;->freeze()Z (light greylist, reflection)
    Accessing hidden method Landroid/graphics/FontFamily;->abortCreation()V (light greylist, reflection)
    Accessing hidden method Landroid/graphics/Typeface;->createFromFamiliesWithDefault([Landroid/graphics/FontFamily;Ljava/lang/String;II)Landroid/graphics/Typeface; (light greylist, reflection)
D/OpenGLRenderer: Skia GL Pipeline
I/Adreno: QUALCOMM build                   : 916a0de, I00d131a0ef
    Build Date                       : 02/04/19
    OpenGL ES Shader Compiler Version: EV031.25.03.02
    Local Branch                     : mybranche928714c-d47c-f13c-a752-5cb90179c3d8
    Remote Branch                    : quic/gfx-adreno.lnx.1.0.r48-rel
    Remote Branch                    : NONE
    Reconstruct Branch               : NOTHING
I/Adreno: Build Config                     : S L 6.0.7 AArch64
I/Adreno: PFP: 0x005ff112, ME: 0x005ff066
I/ConfigStore: android::hardware::configstore::V1_0::ISurfaceFlingerConfigs::hasWideColorDisplay retrieved: 0
I/ConfigStore: android::hardware::configstore::V1_0::ISurfaceFlingerConfigs::hasHDRDisplay retrieved: 0
I/OpenGLRenderer: Initialized EGL, version 1.4
D/OpenGLRenderer: Swap behavior 2
W/ActivityThread: handleWindowVisibility: no activity for token android.os.BinderProxy@610c445
V/AndroidHarness: Removing Handler class: com.android.internal.logging.AndroidHandler
D/com.jme3.app.AndroidHarnessFragment: FINE onCreate
    FINE Creating settings
E/graphhopper: Call Outside Native: Enter
    Call Outside Native: Processing classpath arg
    Call Outside Native: Got classpath arg
    Call Outside Native: Calling Main
D/Outside Native Started: determineCpuFeaures
    Outside GraalVM Bridge
E/graphhopper: Call Outside Native: Main Call Complete
    Call Outside Native: Getting SimpleApplication
    
    --------- beginning of crash
A/libc: Fatal signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0x72657265646e in tid 21870 (outside.android), pid 21870 (outside.android)
```
