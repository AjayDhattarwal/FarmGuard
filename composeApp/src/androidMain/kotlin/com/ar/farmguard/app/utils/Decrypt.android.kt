package com.ar.farmguard.app.utils

import com.ar.farmguard.BuildConfig
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

actual fun decryptGcm(
    data: ByteArray,
    key: ByteArray,
    iv: ByteArray
): ByteArray {
    try {
        val keySpec: Key = SecretKeySpec(key, "AES")
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)

        return cipher.doFinal(data)
    } catch (e: Exception) {
        e.printStackTrace()
        return byteArrayOf()
    }
}

actual fun getDECKeyIV(): Pair<String, String> {
    val key: String = BuildConfig.AES_KEY
    val iv: String = BuildConfig.AES_IV
    return Pair(key, iv)
}