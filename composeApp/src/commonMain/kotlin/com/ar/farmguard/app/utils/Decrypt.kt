package com.ar.farmguard.app.utils

import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.json.Json
import okio.ByteString.Companion.decodeHex

expect fun decryptGcm(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray

expect fun getDECKeyIV(): Pair<String,String>

inline fun <reified T> String.deserializeString(): T {

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val encryptedData = this.decodeHex().toByteArray()

    val data = getDECKeyIV()
    val keyBytes = data.first.toByteArray(Charsets.UTF_8)
    val ivBytes = data.second.toByteArray(Charsets.UTF_8)

    println("Encrypted Data: ${data.first},  ${data.second}")


    val decryptedString = decryptGcm(encryptedData, keyBytes, ivBytes).trimPadding().decodeToString()

    println("Decrypted String: $decryptedString")

    return json.decodeFromString<T>(decryptedString)
}


fun ByteArray.trimPadding(): ByteArray {
    var trimIndex = this.size
    while (trimIndex > 0 && this[trimIndex - 1] == 0.toByte()) {
        trimIndex--
    }
    return this.copyOf(trimIndex)
}


enum class StringType {
    JSON, HEX, STRING
}

inline fun String.checkType(): StringType {
    return when {
        isValidJson() -> StringType.JSON

        isHexString() -> StringType.HEX

        else -> StringType.STRING
    }
}

inline fun String.isValidJson(): Boolean {
    val trimmed = this.trim()
    return (trimmed.startsWith("{") && trimmed.endsWith("}")) || (trimmed.startsWith("[") && trimmed.endsWith("]"))
}

inline fun String.isHexString(): Boolean {
    return this.matches(Regex("^[0-9a-fA-F]+$"))
}
