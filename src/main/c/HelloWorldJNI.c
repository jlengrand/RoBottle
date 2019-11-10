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