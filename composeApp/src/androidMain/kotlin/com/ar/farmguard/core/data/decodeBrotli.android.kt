package com.ar.farmguard.core.data

import org.brotli.dec.BrotliInputStream

actual fun decodeBrotli(encodedBytes: ByteArray): String {
    return BrotliInputStream(encodedBytes.inputStream()).use { brotliInputStream ->
        brotliInputStream.readBytes().decodeToString()
    }
}
