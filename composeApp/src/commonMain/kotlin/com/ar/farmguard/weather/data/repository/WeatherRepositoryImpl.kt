package com.ar.farmguard.weather.data.repository

import com.ar.farmguard.app.utils.GEN_API_KEY
import com.ar.farmguard.core.domain.DataError
import com.ar.farmguard.core.domain.Result
import com.ar.farmguard.core.domain.onSuccess
import com.ar.farmguard.weather.domain.models.request.GeminiWeatherRequest
import com.ar.farmguard.weather.domain.models.response.LocationInfo
import com.ar.farmguard.weather.domain.models.response.WeatherSummary
import com.ar.farmguard.weather.domain.models.response.WeatherResponse
import com.ar.farmguard.weather.domain.network.WeatherApi
import com.ar.farmguard.weather.domain.repository.WeatherRepository
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.Content
import dev.shreyaspatil.ai.client.generativeai.type.Schema
import dev.shreyaspatil.ai.client.generativeai.type.TextPart
import dev.shreyaspatil.ai.client.generativeai.type.content
import dev.shreyaspatil.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class WeatherRepositoryImpl(
    private val api: WeatherApi
): WeatherRepository {

    private val instruction = Content(
        parts = listOf(
            TextPart("You are a helpful AI assistant for farmers and agriculture enthusiasts."),
            TextPart("Provide information related to farming, agriculture, weather, and crop management."),
            TextPart("Offer advice, insights, and practical tips to farmers."),
            TextPart("All your responses must be in JSON format."),
            TextPart("You have to create Note Based on the weather conditions and  forecast , and max 15 to 20 words for Note, heading 10 to 15 words and description with key points "),
            TextPart("example of Note: Fog can reduce visibility. Be cautious when operating machinery."),
            TextPart("example of Note: Heavy rain alert. Check drainage systems and be prepared for potential flooding. Secure farm equipment and livestock."),
            TextPart("example of Note: Rain is beneficial for crops, but monitor fields for waterlogging or runoff. Consider delaying fertilizer application.")
        )
    )

    private val jsonSchema = Schema.obj(
        name = "systemInstruction",
        description = "systemInstruction",
        Schema.str("heading", "The title of the note."),
        Schema.str("description", "A brief description of the note."),
        Schema.str("note", "The actual note.")
    )

    private val _forecast = MutableStateFlow<WeatherResponse?>(null)
    override val forecast = _forecast.asStateFlow()

    override suspend fun fetchWeather(
        coordinate: String,
    ): Result<WeatherResponse, DataError.Remote> {
        return api.getForecast(coordinate).onSuccess {
            _forecast.value = it
        }
    }

    override suspend fun getLocationInfo(
        latitude: String,
        longitude: String,
        lang: String
    ): Result<LocationInfo, DataError.Remote> {
        return api.getLocationInfo(latitude, longitude, lang)

    }

    override suspend fun getWeatherNote(data: GeminiWeatherRequest): Result<WeatherSummary, DataError.Remote> =
        withContext(Dispatchers.IO) {
            try{
                val jsonString = Json.encodeToString(data)
                val model = GenerativeModel(
                    modelName = "gemini-1.5-pro",
                    apiKey = GEN_API_KEY,
                    systemInstruction = instruction,
                    generationConfig = generationConfig {
                        responseMimeType = "application/json"
                        responseSchema = jsonSchema
                    }
                )
                val input = content {
                    text(
                        """
                            Analyze the following JSON data of weather and provide a NOTE for agriculture enthusiasts :
                            ```json
                            $jsonString
                            ```
                        
                        """
                    )
                }

                val response = model.generateContent(input)
                if (response.text != null) {
                    try {
                        val note = Json.decodeFromString<WeatherSummary>(response.text!!)
                        return@withContext Result.Success(note)
                    } catch (e: SerializationException) {
                        e.printStackTrace()
                        return@withContext Result.Error(DataError.Remote.SERIALIZATION)
                    }
                }

                return@withContext Result.Error(DataError.Remote.UNKNOWN)

            } catch (e: Exception){
                e.printStackTrace()
                return@withContext Result.Error(DataError.Remote.UNKNOWN)
            }
        }


}