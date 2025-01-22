package com.ar.farmguard.app.utils

fun weatherImageSelect(string: String): String {
    if(string.isNotEmpty()){
        val isNight = string.contains("night")
        val timeOfDay = if (isNight) "night" else "day"
        val code = string.substringAfterLast("/").substringBeforeLast(".").toInt()

        return when (code) {
            113, -> "$IMG_BASE_URL_WEATHER/$timeOfDay/113.svg"
            116 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/116.svg"
            119 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/119.svg"
            122 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/122.svg"
            143 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/143.svg"
            176, 185, 353 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/176.svg"
            179, 329, 335, 368, 371 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/179.svg"
            182, 362, 365 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/182.svg"
            200, 386 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/200.svg"
            227, 230, 320, 323, 332,338 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/227.svg"
            248, 260 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/248.svg"
            263, 266 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/263.svg"
            281, 284, 350-> "$IMG_BASE_URL_WEATHER/$timeOfDay/281.svg"
            293, 299, 305, 356, 359-> "$IMG_BASE_URL_WEATHER/$timeOfDay/293.svg"
            296, 302, 308 , 311, 314 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/296.svg"
            317 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/317.svg"
            326 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/326.svg"
            374, 377 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/374.svg"
            389 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/389.svg"
            392 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/392.svg"
            395 -> "$IMG_BASE_URL_WEATHER/$timeOfDay/395.svg"
            else -> "$IMG_BASE_URL_WEATHER/error.svg"
        }
    }else{
        return "$IMG_BASE_URL_WEATHER/error.svg"
    }
}
