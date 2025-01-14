package com.ar.farmguard.services.insurance.auth.data.dto


import com.ar.farmguard.services.insurance.auth.domain.models.remote.OtpResponse
import com.ar.farmguard.services.insurance.auth.domain.models.remote.ResponseDataOTP
import kotlinx.serialization.ExperimentalSerializationApi
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
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonDecoder

object OtpResponseSerializer: KSerializer<OtpResponse> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
    OtpResponse::class.simpleName!!
    ) {
        element<Boolean>("status")
        element<String?>("error")
        element<String?>("level")
        element<ResponseDataOTP?>("data")
    }

    override fun deserialize(decoder: Decoder): OtpResponse = decoder.decodeStructure(descriptor) {
        var status: Boolean? = null
        var error: String? = null
        var level: String? = null
        var data : ResponseDataOTP? = null

        while (true){
            when(val index = decodeElementIndex(descriptor)){
                0 -> status = decodeBooleanElement(descriptor,0)
                1 -> error = decodeStringElement(descriptor,1)
                2 -> level = decodeStringElement(descriptor,2)
                3 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected Json Decoder")

                    val element = jsonDecoder.decodeJsonElement()

                    data = if (element is JsonObject) {
                        val response = jsonDecoder.json.decodeFromJsonElement(ResponseDataOTP.serializer(), element)
                        response
                    }else{
                        null
                    }
                }
                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure OtpResponse(
            status = status?: false,
            error = error ?: "",
            level = level,
            data = data
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: OtpResponse) = encoder.encodeStructure(
    descriptor
    ) {
        encodeBooleanElement(descriptor, 0, value.status)
        encodeStringElement(descriptor, 1, value.error)
        encodeStringElement(descriptor, 2, value.level ?: "")
        encodeNullableSerializableElement(descriptor, 3, ResponseDataOTP.serializer(), value.data)
    }

}