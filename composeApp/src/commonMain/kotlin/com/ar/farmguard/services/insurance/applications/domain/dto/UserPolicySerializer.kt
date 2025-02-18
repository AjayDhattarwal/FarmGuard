package com.ar.farmguard.services.insurance.applications.domain.dto

import com.ar.farmguard.services.insurance.applications.domain.model.UserPolicyData
import com.ar.farmguard.services.insurance.applications.domain.model.UserPolicyResponse
import com.ar.farmguard.services.insurance.auth.data.dto.UserDataSerializer
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
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject

class UserPolicySerializer(): KSerializer<UserPolicyResponse> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        UserPolicyResponse::class.simpleName!!
    ) {
        element<Boolean>("status")
        element<String?>("error")
        element<String?>("level")
        element<List<UserPolicyData>?>("data")
    }

    override fun deserialize(decoder: Decoder): UserPolicyResponse = decoder.decodeStructure(descriptor){
        var status: Boolean? = null
        var error: String? = null
        var level: String? = null
        var data : List<UserPolicyData> = emptyList()

        while (true){
            when(val index = decodeElementIndex(UserDataSerializer.descriptor)){
                0 -> status = decodeBooleanElement(UserDataSerializer.descriptor,0)
                1 -> error = decodeStringElement(UserDataSerializer.descriptor,1)
                2 -> level = decodeStringElement(UserDataSerializer.descriptor,2)
                3 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException("Expected Json Decoder")

                    val element = jsonDecoder.decodeJsonElement().jsonObject
                    // that is a json object that key is long values and that changed on evey response , and that key value is always a list of UserPolicyData
                    data = element.values.flatMap {
                        jsonDecoder.json.decodeFromJsonElement(ListSerializer(UserPolicyData.serializer()), it)
                    }
                }
                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure UserPolicyResponse(
            status = status?: false,
            error = error ?: "",
            level = level,
            data = data
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: UserPolicyResponse) = encoder.encodeStructure(descriptor) {
        encodeBooleanElement(descriptor, 0, value.status == true)
        encodeStringElement(descriptor, 1, value.error ?: "")
        encodeStringElement(descriptor, 2, value.level ?: "")
        encodeNullableSerializableElement(descriptor, 3, ListSerializer(UserPolicyData.serializer()), value.data)
    }

}