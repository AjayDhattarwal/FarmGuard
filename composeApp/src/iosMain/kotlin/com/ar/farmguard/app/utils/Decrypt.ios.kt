package com.ar.farmguard.app.utils

import platform.Foundation.NSBundle
import platform.Foundation.NSDictionary
import platform.CoreCrypto.CCCrypt
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.kCCAlgorithmAES
import platform.CoreCrypto.kCCDecrypt
import platform.CoreCrypto.kCCOptionPKCS7Padding
import platform.CoreCrypto.kCCSuccess
import platform.Foundation.dictionaryWithContentsOfFile

@OptIn(ExperimentalForeignApi::class)
actual fun decryptGcm(
    data: ByteArray,
    key: ByteArray,
    iv: ByteArray
): ByteArray {
    try {
        if (key.size != 32) {
            println("Error: Key must be 32 bytes (256 bits), but is ${key.size} bytes. Returning empty ByteArray.")
            return byteArrayOf()
        }
        if (iv.size != 16) {
            println("Error: IV must be 16 bytes (128 bits), but is ${iv.size} bytes. Returning empty ByteArray.")
            return byteArrayOf()
        }

        // Prepare data for decryption
        val decryptedData = ByteArray(data.size)

        // Pin key and iv to native memory
        val keyPointer = key.usePinned { it.addressOf(0) }
        val ivPointer = iv.usePinned { it.addressOf(0) }
        val dataPointer = data.usePinned { it.addressOf(0) }
        val decryptedPointer = decryptedData.usePinned { it.addressOf(0) }

        // Convert sizes to ULong (size_t in C)
        val keySize = key.size.convert<ULong>()
        val ivSize = iv.size.convert<ULong>()
        val dataSize = data.size.convert<ULong>()
        val decryptedDataSize = decryptedData.size.convert<ULong>()

        // AES CBC decryption with PKCS5 padding
        val cryptStatus = CCCrypt(
            kCCDecrypt,
            kCCAlgorithmAES,
            kCCOptionPKCS7Padding,
            keyPointer, keySize,
            ivPointer,
            dataPointer, dataSize,
            decryptedPointer, decryptedDataSize,
            null
        )

        if (cryptStatus == kCCSuccess) {
            return decryptedData
        } else {
            println("Decryption failed with status: $cryptStatus. Returning empty ByteArray.")
            return byteArrayOf()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return byteArrayOf()
    }
}



actual fun getDECKeyIV(): Pair<String, String> {

    val path = NSBundle.mainBundle.pathForResource("keystrokes", ofType = "plist") ?: return Pair("", "")

    val dictionary = NSDictionary.dictionaryWithContentsOfFile(path)

    val key = dictionary?.getValue("AES_KEY") as? String ?: ""
    val iv = dictionary?.getValue("AES_IV") as? String ?: ""

    return Pair(key, iv)
}
