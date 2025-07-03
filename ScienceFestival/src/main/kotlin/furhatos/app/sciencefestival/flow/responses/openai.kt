package furhatos.app.sciencefestival.flow.responses

import furhatos.flow.kotlin.DialogHistory
import furhatos.flow.kotlin.Furhat

import furhatos.app.sciencefestival.setting.API_KEY
import furhatos.app.sciencefestival.setting.API_URL
import furhatos.app.sciencefestival.setting.MODEL
import furhatos.app.sciencefestival.setting.PROMPT
import furhatos.app.sciencefestival.setting.TEMPERATURE
import furhatos.app.sciencefestival.setting.MAX_TOKENS
import furhatos.app.sciencefestival.setting.MULTIMODAL


/** Open AI Java imports **/
import com.openai.client.OpenAIClient
import com.openai.client.okhttp.OpenAIOkHttpClient
//import com.openai.core.JsonValue
import com.openai.models.chat.completions.ChatCompletion
import com.openai.models.chat.completions.ChatCompletionCreateParams
import com.openai.models.chat.completions.ChatCompletionUserMessageParam
import com.openai.models.chat.completions.ChatCompletionContentPart
import com.openai.models.chat.completions.ChatCompletionContentPartImage
import com.openai.models.chat.completions.ChatCompletionContentPartText




/** Open AI Java FUNCTIONS **/

val openAI : OpenAIClient = OpenAIOkHttpClient.builder()
    .apiKey(API_KEY)
    .baseUrl(API_URL.plus("/v1") )
    .build()


fun getOpenAIResponse(request: String, image: String? = null, historyLength: Int = 10): String {
    val history = Furhat.dialogHistory.all.takeLast(historyLength).mapNotNull {
        when (it) {
            is DialogHistory.ResponseItem -> {
                "User: ${it.response.text}"
            }
            is DialogHistory.UtteranceItem -> {
                "Assistant: ${it.toText()}"
            }
            else -> null
        }
    }.joinToString(separator = "\n")
    var inputPrompt = PROMPT
    inputPrompt+= "\n Here is the history of the current conversation so far:\n"
    inputPrompt+=history
    inputPrompt+="\n You should produce a formal response. Response should be no longer than 3 sentences and you should try to summarise whatever you say. Don't try to repeat what has been spoken already in the history. Also if the question is asked out of the context, of what the information you are given or what you can see in the image, do not try to answer it. Do not attempt to fabricate or hallucinate any answers. "


//    [{"type": "text", "text": utterance},{"type": "image_url", "image_url": {"url": image}}]

    val chatParams = when(MULTIMODAL) {
        true -> {
//            val objectValue: JsonValue = JsonValue.from(
//                listOf(
//                    mapOf<String, String>(
//                        "type" to "text",
//                        "text" to request
//                    ),
//                    mapOf<String, Any>(
//                        "type" to "image_url",
//                        "image_url" to mapOf( "url" to image )
//                    )
//                )
//            )
////            println(objectValue)
////            val userMessage = ChatCompletionUserMessageParam.builder().content(lastUtterance).putAdditionalProperty("image_url", JsonValue.from(mapOf( "url" to image ))).build()
//            val userMessage = ChatCompletionUserMessageParam.builder().putAdditionalProperty("content", objectValue).build()
////            putAdditionalProperty("image_url", JsonValue.from(mapOf( "url" to image ))).build()
//            println(userMessage)
            val imageContentPart =
                ChatCompletionContentPart.ofImageUrl(
                    ChatCompletionContentPartImage.builder()
                        .imageUrl(
                            ChatCompletionContentPartImage.ImageUrl.builder()
                                .detail(ChatCompletionContentPartImage.ImageUrl.Detail.AUTO)
                                .url(image!!)
                                .build()
                        )
                        .build()
                )
            val requestContentPart =
                ChatCompletionContentPart.ofText(
                    ChatCompletionContentPartText.builder()
                        .text(request)
                        .build()
                )

            ChatCompletionCreateParams.builder()
                .model(MODEL)
                .addSystemMessage(inputPrompt)
//                .addUserMessage(userMessage.content())
                .addUserMessageOfArrayOfContentParts(listOf(requestContentPart, imageContentPart))
                .temperature(TEMPERATURE)
//                .maxCompletionTokens(MAX_TOKENS)
                .maxTokens(MAX_TOKENS)
                .build()
        }
        false -> {
            ChatCompletionCreateParams.builder()
                .model(MODEL)
                .addSystemMessage(inputPrompt)
                .addUserMessage(request)
                .temperature(TEMPERATURE)
                .build()
        }
    }
        println(chatParams)

//    val chatParams = ChatCompletionCreateParams.builder()
//        .model(serviceModel)
//        .addSystemMessage(systemPrompt)
//        .addUserMessage(lastUtterance)
//        .temperature(temperature)
//        .build()

    try {
        val chatCompletion: ChatCompletion = openAI.chat().completions().create(chatParams).validate()
        println(chatCompletion.choices().first().message().content().get())
        return chatCompletion.choices().first().message().content().get()
    } catch (e: Exception) {
        println("Problem with connection to OpenAI: " + e.message)
    }
    return "Sorry, I'm having a problem connecting to the service"
}
