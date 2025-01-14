package com.ar.farmguard.services.insurance.auth.data.dto

import com.ar.farmguard.services.insurance.auth.domain.models.remote.UserData
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder

object UserDataSerializer : KSerializer<UserData> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        UserData::class.simpleName!!
    ) {
        element<Boolean>("status")
        element<String?>("error")
        element<String?>("level")
        element<String?>("data")
    }

    override fun deserialize(decoder: Decoder): UserData = decoder.decodeStructure(descriptor) {
        var status: Boolean? = null
        var error: String? = null
        var level: String? = null
        var data : String? = null

        while (true){
            when(val index = decodeElementIndex(descriptor)){
                0 -> status = decodeBooleanElement(descriptor,0)
                1 -> error = decodeStringElement(descriptor,1)
                2 -> level = decodeStringElement(descriptor,2)
                3 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected Json Decoder")

                    val element = jsonDecoder.decodeJsonElement()

                    data = if (element is JsonArray) {
                        null
                    }else{
                        element.toString()
                    }
                }
                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure UserData(
            status = status?: false,
            error = error ?: "",
            level = level,
            data = data
        )
    }

    override fun serialize(encoder: Encoder, value: UserData) = encoder.encodeStructure(
        descriptor
    ) {
        encodeBooleanElement(descriptor, 0, value.status)
        encodeStringElement(descriptor, 1, value.error)
        encodeStringElement(descriptor, 2, value.level ?: "")
        encodeStringElement(descriptor, 3, value.data ?: "")
    }
}