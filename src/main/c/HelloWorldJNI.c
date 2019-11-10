#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <unistd.h>
#include <jni.h>
#include "../java/HelloWorldJNI.h"
#include "adafruit/Raspberry_Pi_2/pi_2_dht_read.h"

JNIEXPORT void JNICALL Java_HelloWorldJNI_sayHello(JNIEnv *env, jobject thisObj) {
   printf("Hello JNI!\n");
   return;
}

JNIEXPORT jfloatArray JNICALL Java_HelloWorldJNI_getTemperatureAndHumidity(JNIEnv *env, jobject thisObj){
    float temperature = 25.0;
    float humidity = 65.0;

    jfloat* values = (jfloat *) malloc(2*sizeof(jfloat));
    values[0] = temperature;
    values[1] = humidity;

    jfloatArray outJNIArray = (*env)->NewFloatArray(env, 2);
    if (NULL == outJNIArray) return NULL;

    (*env)->SetFloatArrayRegion(env, outJNIArray, 0, 2, (const jfloat*)values);
    return outJNIArray;
}