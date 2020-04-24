#include <jni.h>
#include <stdio.h>
#include <android/log.h>
#include <errno.h>
#include <unistd.h>
#include <pthread.h>
#include <iostream>
#include <fstream>
#include <string>

#define  LOGE(ignore, ...)  __android_log_print(ANDROID_LOG_ERROR, "outside", __VA_ARGS__)

extern "C" {
// this interface is from GraalVM
// https://github.com/oracle/graal/blob/master/substratevm/src/com.oracle.svm.hosted/src/com/oracle/svm/hosted/image/AbstractBootImage.java#L81
int run_main(int paramArgc, char **paramArgv);

//Found using nm -gD liboutside-android-bridge.so > dump.txt
jobject Bridge_getJme_6fcba43cc9d9cb04d2992e812ff0825b90d03deb(const char *classpath);
}



int start_logger(const char *app_name);

static int pfd[2];
static pthread_t thr;
static const char *tag = "myapp";

// for logs we need this and the start_logger since android eats fprintf
static void *thread_func(void *arg) {
    ssize_t rdsz;
    char buf[128];
    while ((rdsz = read(pfd[0], buf, sizeof buf - 1)) > 0) {
        if (buf[rdsz - 1] == '\n')
            --rdsz;
        buf[rdsz] = 0; /* add null-terminator */
        __android_log_write(ANDROID_LOG_DEBUG, tag, buf);
    }
    return 0;
}

int start_logger(const char *app_name) {
    tag = app_name;

    /* make stdout line-buffered and stderr unbuffered */
    setvbuf(stdout, 0, _IOLBF, 0);
    setvbuf(stderr, 0, _IONBF, 0);

    /* create the pipe and redirect stdout and stderr */
    pipe(pfd);
    dup2(pfd[1], 1);
    dup2(pfd[1], 2);

    /* spawn the logging thread */
    if (pthread_create(&thr, 0, thread_func, 0) == -1)
        return -1;
    pthread_detach(thr);
    return 0;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    start_logger("Outside Native Started");
    return JNI_VERSION_1_4;
}

extern "C" JNIEXPORT jobject JNICALL Java_io_tlf_outside_android_JmeJfxHarness_getJme(JNIEnv *env, jobject harness, jstring classpath) {
    LOGE(stderr, "Call Outside Native: Enter");

    LOGE(stderr, "Call Outside Native: Processing classpath arg");
    const char *class_path = env->GetStringUTFChars(classpath, 0);
    LOGE(stderr, "Call Outside Native: Got classpath arg");

    LOGE(stderr, "Call Outside Native: Calling Main");
    const char *args[] = {"dummy"}; //It is required that we pass a var, otherwise we get a java.lang.NegativeArraySizeException
    run_main(1, (char **) args); //The integer is the number of variables we are passing
    LOGE(stderr, "Call Outside Native: Main Call Complete");

    LOGE(stderr, "Call Outside Native: Getting SimpleApplication");
    JNIEnv *graalEnv;
    JavaVM* graalVM = getGraalVM();
    int attach_graal_det = ((*graalVM)->GetEnv(graalVM, (void **)&graalEnv, JNI_VERSION_1_6) == JNI_OK); \
    (*graalVM)->AttachCurrentThreadAsDaemon(graalVM, (void **) &graalEnv, NULL); \
    LOGE(stderr, "Call Outside Native: Got SimpleApplication");

    LOGE(stderr, "Call Outside Native: Releasing classpath arg");
    // uh, is this ugliness really necessary?
    env->ReleaseStringUTFChars(classpath, class_path);

    LOGE(stderr, "Call Outside Native: Returning");
    return ret_obj;
}