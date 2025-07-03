package furhatos.app.sciencefestival.setting

import furhatos.app.sciencefestival.ScienceFestival
import java.io.InputStreamReader
import java.text.MessageFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/** Engagement parameters */
const val MAX_NUMBER_OF_USERS = 4 // Max amount of people that Furhat will recognize as users simultaneously
const val DISTANCE_TO_ENGAGE = 2.0 // Min distance for people to be recognised as users

const val CHARACTER = "Jamie"
const val VOICE = "Matthew"

/** Open AI API Key **/
const val API_KEY = "EMPTY"

//const val API_URL = "http://0.0.0.0:21020/v1"
//const val API_URL = "http://0.0.0.0:21020"
//const val API_URL = "http://137.195.93.106:21020/v1"
const val API_URL = "http://137.195.93.106:21020"
//const val API_URL = "http://137.195.93.138:21020/v1"
//const val API_URL = "http://137.195.243.16:8080/v1"
//const val API_URL = "https://robotarium.share.zrok.io/v1"

//const val MODEL = "models/llava-v1.5-13B-AWQ"
//const val MODEL = "vicuna-13b-v1.5"
const val MODEL = "meta-llama/Llama-3.2-11B-Vision-Instruct"

const val TEMPERATURE = 0.5
const val MAX_TOKENS : Long = 96
const val MULTIMODAL=true
//const val MULTIMODAL=false


//val currentDate = LocalDateTime.now()
//val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy - HH:mm:ss")
val currentDate = LocalDate.now(ZoneId.of("Europe/London"))
val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
val formattedDate = currentDate.format(formatter)

val promptStreamReader = ScienceFestival::class.java.getResourceAsStream("/prompt_file.txt")?.reader()

fun readPrompt(streamReader: InputStreamReader?): String {
    var prompt: String = "You are chatty robot. You should speak in a conversational style. Never say more than two sentences. Today is {0}!"
    streamReader.use { reader ->
        if (reader != null) {
            prompt = reader.readText()
        }
    }
    return MessageFormat.format(prompt, formattedDate)
}


//val PROMPT = ScienceFestival::class.java.getResourceAsStream("/prompt_file.txt")
val PROMPT = readPrompt(promptStreamReader)
//val PROMPT = promptStreamReader?.let { readPrompt(it) }
