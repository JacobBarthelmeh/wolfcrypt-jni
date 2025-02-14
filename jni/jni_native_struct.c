/* jni_native_struct.c
 *
 * Copyright (C) 2006-2021 wolfSSL Inc.
 *
 * This file is part of wolfSSL. (formerly known as CyaSSL)
 *
 * wolfSSL is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * wolfSSL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */

#ifndef __ANDROID__
    #include <wolfssl/options.h>
#endif
#include <wolfssl/wolfcrypt/types.h>

#include <com_wolfssl_wolfcrypt_NativeStruct.h>
#include <wolfcrypt_jni_NativeStruct.h>
#include <wolfcrypt_jni_error.h>

/* #define WOLFCRYPT_JNI_DEBUG_ON */
#include <wolfcrypt_jni_debug.h>

#pragma GCC diagnostic ignored "-Wint-to-pointer-cast"

JavaVM* g_vm = NULL;

/* called when native library is loaded */
jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    /* store JavaVM */
    g_vm = vm;
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_NativeStruct_xfree(
    JNIEnv* env, jobject this, jlong ptr)
{
    LogStr("Freeing (%p)\n", (void*)ptr);

    XFREE((void*)ptr, NULL, DYNAMIC_TYPE_TMP_BUFFER);
}

/*
 * Utilitary functions
 */
void* getNativeStruct(JNIEnv* env, jobject this)
{
    jclass class;
    jfieldID field;
    jlong nativeStruct = 0;

    if (this) {
        class = (*env)->GetObjectClass(env, this);
        field = (*env)->GetFieldID(env, class, "pointer", "J");

        /* GetFieldID may throw exception */
        if ((*env)->ExceptionOccurred(env)) {
            (*env)->ExceptionDescribe(env);
            (*env)->ExceptionClear(env);

        } else {
            nativeStruct = (*env)->GetLongField(env, this, field);
        }

        if (!nativeStruct)
            throwWolfCryptException(env, "Failed to retrieve native struct");

        return (void*) nativeStruct;
    }

    return NULL;
}

void setByteArrayMember(
    JNIEnv* env, jobject this, const char* name, jbyteArray value)
{
    jclass class;
    jfieldID field;

    class = (*env)->GetObjectClass(env, this);
    field = (*env)->GetFieldID(env, class, name, "[B");

    /* GetFieldID may throw an exception */
    if ((*env)->ExceptionOccurred(env)) {
        return;
    }

    (*env)->SetObjectField(env, this, field, (jobject)value);
}

byte* getDirectBufferAddress(JNIEnv* env, jobject buffer)
{
    return buffer ? (*env)->GetDirectBufferAddress(env, buffer) : NULL;
}

word32 getDirectBufferLimit(JNIEnv* env, jobject buffer)
{
    jclass class;
    jmethodID method;

    class  = (*env)->GetObjectClass(env, buffer);
    method = (*env)->GetMethodID(env, class, "limit", "()I");

    /* GetMethodID may throw an exception */
    if ((*env)->ExceptionOccurred(env)) {
        (*env)->ExceptionDescribe(env);
        (*env)->ExceptionClear(env);
        return 0;
    }

    return (word32) (*env)->CallIntMethod(env, buffer, method);
}

void setDirectBufferLimit(JNIEnv* env, jobject buffer, jint limit)
{
    jclass class;
    jmethodID method;

    class = (*env)->GetObjectClass(env, buffer);
    method = (*env)->GetMethodID(env, class, "limit", "(I)Ljava/nio/Buffer;");

    /* GetMethodID may throw an exception */
    if ((*env)->ExceptionOccurred(env)) {
        return;
    }

    /* may throw exception */
    (*env)->CallObjectMethod(env, buffer, method, limit);
}

byte* getByteArray(JNIEnv* env, jbyteArray array)
{
    return array ? (byte*)(*env)->GetByteArrayElements(env, array, NULL) : NULL;
}

void releaseByteArray(JNIEnv* env, jbyteArray array, byte* elements, jint abort)
{
    if (elements)
        (*env)->ReleaseByteArrayElements(env, array, (jbyte*) elements,
            abort ? JNI_ABORT : 0);
}

word32 getByteArrayLength(JNIEnv* env, jbyteArray array)
{
    return array ? (*env)->GetArrayLength(env, array) : 0;
}
