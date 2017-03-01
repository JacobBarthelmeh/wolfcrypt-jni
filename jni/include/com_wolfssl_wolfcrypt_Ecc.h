/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_wolfssl_wolfcrypt_Ecc */

#ifndef _Included_com_wolfssl_wolfcrypt_Ecc
#define _Included_com_wolfssl_wolfcrypt_Ecc
#ifdef __cplusplus
extern "C" {
#endif
#undef com_wolfssl_wolfcrypt_Ecc_NULL
#define com_wolfssl_wolfcrypt_Ecc_NULL 0LL
/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    mallocNativeStruct
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_wolfssl_wolfcrypt_Ecc_mallocNativeStruct
  (JNIEnv *, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1init
  (JNIEnv *, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_free
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1free
  (JNIEnv *, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_make_key
 * Signature: (Lcom/wolfssl/wolfcrypt/Rng;I)V
 */
JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1make_1key
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_check_key
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1check_1key
  (JNIEnv *, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_shared_secret
 * Signature: (Lcom/wolfssl/wolfcrypt/Ecc;)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1shared_1secret
  (JNIEnv *, jobject, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_import_private
 * Signature: ([B[B)V
 */
JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1import_1private
  (JNIEnv *, jobject, jbyteArray, jbyteArray);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_export_private
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1export_1private
  (JNIEnv *, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_import_x963
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1import_1x963
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_export_x963
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1export_1x963
  (JNIEnv *, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_EccPrivateKeyDecode
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1EccPrivateKeyDecode
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_EccKeyToDer
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1EccKeyToDer
  (JNIEnv *, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_EccPublicKeyDecode
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1EccPublicKeyDecode
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_EccPublicKeyToDer
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1EccPublicKeyToDer
  (JNIEnv *, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_sign_hash
 * Signature: ([BLcom/wolfssl/wolfcrypt/Rng;)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1sign_1hash
  (JNIEnv *, jobject, jbyteArray, jobject);

/*
 * Class:     com_wolfssl_wolfcrypt_Ecc
 * Method:    wc_ecc_verify_hash
 * Signature: ([B[B)Z
 */
JNIEXPORT jboolean JNICALL Java_com_wolfssl_wolfcrypt_Ecc_wc_1ecc_1verify_1hash
  (JNIEnv *, jobject, jbyteArray, jbyteArray);

#ifdef __cplusplus
}
#endif
#endif
