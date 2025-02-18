package com.ar.farmguard.services.insurance.status.data

import com.ar.farmguard.services.insurance.status.domain.models.ApplicationStatusData
import com.ar.farmguard.services.insurance.status.domain.models.ApplicationStatusResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
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

class ApplicationStatusDto: KSerializer<ApplicationStatusResponse> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        ApplicationStatusResponse::class.simpleName!!
    ) {
        element<Boolean>("status")
        element<String?>("error")
        element<String?>("level")
        element<List<ApplicationStatusData>?>("data")
    }

    override fun deserialize(decoder: Decoder): ApplicationStatusResponse = decoder.decodeStructure(descriptor) {
        var status: Boolean? = null
        var error: String? = null
        var level: String? = null
        var data : List<ApplicationStatusData> = emptyList()

        while (true){
            when(val index = decodeElementIndex(descriptor)){
                0 -> status = decodeBooleanElement(descriptor,0)
                1 -> error = decodeStringElement(descriptor,1)
                2 -> level = decodeStringElement(descriptor,2)
                3 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected Json Decoder")

                    val element = jsonDecoder.decodeJsonElement()

                    data = if (element is JsonArray) {
                        element.map { jsonElement ->
                            jsonDecoder.json.decodeFromJsonElement(ApplicationStatusData.serializer(), jsonElement)
                        }
                    }else{
                        emptyList()
                    }
                }
                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure ApplicationStatusResponse(
            status = status?: false,
            error = error ?: "",
            level = level,
            data = data
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: ApplicationStatusResponse) = encoder.encodeStructure(
        descriptor
    ) {
        encodeBooleanElement(descriptor, 0, value.status)
        encodeStringElement(descriptor, 1, value.error)
        encodeStringElement(descriptor, 2, value.level ?: "")
        encodeNullableSerializableElement(descriptor, 3, ListSerializer(ApplicationStatusData.serializer()), value.data)
    }

}