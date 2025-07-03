package furhatos.app.sciencefestival.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language


class CancelIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("cancel", "stop", "abort")
    }
}

class ExitIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Stop", "Go back to sleep", "Exit", "Silence", "Go to sleep")
    }
}

class GoToPassiveIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "hold on",
            "wait a minute",
            "hold on for a minute",
            "wait a bit",
            "wait a little",
            "hold on for a while",
            "chill for a while",
            "chill a bit"
        )
    }
}

class WakeupIntent : Intent() {
    fun getEnum(lang: Language): List<String> {
        return listOf(
            "furhat",
            "wake up",
            "wakeup",
            "are you there",
            "are you here")
    }

    override fun getExamples(lang: Language): List<String> {
        return getEnum(lang)
    }

    override fun getSpeechRecPhrases(lang: Language): List<String> {
        return getEnum(lang)
    }
}

class DoPresentationIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "tell us who you are",
            "who are you",
            "do your presentation",
            "present yourself",
            "can you do your presentation",
            "can you present yourself",
            "introduce yourself",
            "can you introduce yourself",
            "tell us about yourself"
        )
    }
}

class ChatEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "chat:chat,chatting", "talk:talk,talking", "discuss:discuss,discussing"
        )
    }
}

class ChatIntent(val chat: ChatEntity? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I wanna @chat",
            "I want to @chat",
            "Can we @chat a bit",
            "can we @chat a little",
            "can we @chat",
            "can we @chat about something",
            "how about @chat something",
            "how about @chat",
            "can we have a little @chat"
        )
    }
}


// ADD ROBOTARIUM   intents
class RobotariumEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "robotarium:robotarium,Robotarium", "national_robotarium:national robotarium,National Robotarium",
        )
    }
    override fun getSpeechRecPhrases(lang: Language): List<String> {
        return getEnum(lang)
    }
}

class RobotariumIntent(val robotarium: RobotariumEntity? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@robotarium",
            "I want to know about the @robotarium",
            "Can you tell me about the @robotarium a bit",
            "know @robotarium",
            "what is the @robotarium",
            "more about the @robotarium",
            "how about @robotarium",
            "how about the @robotarium",
            "question about the @robotarium",
        )
    }
}


// ADD event intents


class LookEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "see:see,seeing", "find:find,finding", "look:look,looking"
        )
    }
}

class LookIntent(val find: LookEntity? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I wanna @find ",
            "I want to @find",
            "Can you @find my",
            "Can you @find a",
            "can you @find it",
        )
    }
}

class DateTime : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "What day is today?",
            "Tell me the date today",
            "date",
            "time",
            "what time",
            "what date",
            "what is the time now",
            "whats is todays date")
    }
}

class Weather : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "could you tell me what is today's temperature",
            "What is todays weather",
            "weather",
            "can i know the weather",
            "is it too rainy",
            "raining outside",
            "forecast",
            "isnt it too cold aint it",
            "drizzly",
            "is it sunny",
            "windy",
            "foggy",
            "can you tell me what is the temperature here"

        )
    }}

class GoodBye : Intent(){
    override fun getExamples(lang: Language): List<String> {
        return listOf("Thank you for your time","Goodbye","bye","cya","see you")}}