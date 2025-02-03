package com.ar.farmguard.core.data


actual fun decodeBrotli(encodedBytes: ByteArray): String {
    return encodedBytes.decodeToString()
}
