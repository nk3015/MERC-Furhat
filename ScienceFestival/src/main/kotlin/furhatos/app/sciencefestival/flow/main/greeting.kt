package furhatos.app.sciencefestival.flow.main

import furhatos.app.sciencefestival.flow.Parent
import furhatos.app.sciencefestival.flow.camerafeed.getImgBase64
//import furhatos.app.sciencefestival.flow.idleButtons
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.furhat.camerafeed.getSnapShot
//import furhatos.nlu.common.Goodbye
import furhatos.app.sciencefestival.nlu.*
import furhatos.gestures.Gestures
import furhatos.nlu.NullIntent

import java.awt.image.BufferedImage

import furhatos.app.sciencefestival.flow.responses.getOpenAIResponse
//import furhatos.app.sciencefestival.flow.responses.getSimpleOpenAIResponse
import furhatos.autobehavior.setDefaultMicroexpression
import furhatos.nlu.Response



val Greeting: State = state(Parent) {
    onEntry {
        /** Camera feed */
        furhat.cameraFeed.enable()     //Enables the camera feed
        furhat.attend(users.random)

        val (image, faces) = furhat.cameraFeed.getSnapShot() ?: (null to 0)
        val b64 = (image as? BufferedImage)?.let { getImgBase64(it) }

        furhat.say(async = true) {
            +Gestures.GazeAway
            +"Hi there!"
        }

        val robotResponse = call {
//            getSimpleOpenAIResponse("#INTRO", b64)
            getOpenAIResponse("#INTRO", b64)
        } as String?
        furhat.say(robotResponse ?: "Can I help you?")

//        reentry()
//        goto(Active())
        goto(MainChat)
    }

    onReentry {
        furhat.listen()
    }
}


// State where Furhat assumes the user will respond
fun Active(response: Response<*>? = null, returning: Boolean = false) : State = state(Parent) {
    onEntry {
//        send(AttendUsers(shouldAlterAttentionOnSpeech = false))
//        furhat.setLED("White")
        furhat.gesture(Gestures.OpenEyes, priority = 1)
        furhat.attend(users.random)
        furhat.setDefaultMicroexpression()
        when {
            response != null -> raise(response)  // Raise our response so that our handlers can do their magic
            returning -> reentry()
            else -> furhat.say({
                random{
                    +"how can I help?"
                    +"what can I do for you?"
                    +"What can I do to help?"
                    +"How can I help you?"
                    +"Can I do anything for you?"
                    +"Is there anything I can do for you?"
                }
            })

        }
        goto(MainChat)
    }

    onReentry {
//        send(AttendUsers(shouldAlterAttentionOnSpeech = false))
//        furhat.ask(phrases.canIhelpWithAnythingElse())
        furhat.say({
            random{
                +"how can I help?"
                +"what can I do for you?"
                +"What can I do to help?"
                +"How can I help you?"
                +"Can I do anything for you?"
                +"Is there anything I can do for you?"
            }
        })
        goto(MainChat)
    }

    var nomatch = 0
    onResponse<GoToPassiveIntent> {
        furhat.say({
            random {
                +"Okay"
                +"Sure"
            }
        })
        goto(Passive)
    }
    onResponse(listOf(ExitIntent(), CancelIntent(), )) {
        furhat.say("Okay, goodbye")
        delay(2000)
        furhat.say {
            random {
                +"I hope that was an insightful demonstration of generative A I and social robots"
                +"I hope you enjoyed conversing with a social robot "
                +"I hope you found that interesting"
            }
        }
        goto(Sleeping)
    }

    onResponse(cond = { it.intent == NullIntent }) {
        when(++nomatch) {
            1 -> furhat.say("Sorry, I didn't understand that")
            else -> furhat.say("Sorry, I still didn't understand that")
        }
        reentry()
    }

    onNoResponse {
//        goto(Passive)
        val (image,faces) = furhat.cameraFeed.getSnapShot() ?: (null to 0)
        val b64 = (image as? BufferedImage)?.let { getImgBase64(it) }
        val robotResponse = call {
//            getDialogCompletion()
            getOpenAIResponse("Mention something new and interesting you can see on the image", b64)
        } as String?
        furhat.say(robotResponse?:"Sorry, I didn't hear anything")
    }

    onResponseFailed {
        furhat.say("Sorry, my speech recognizer is not working")
    }
}

val MainChat = state(Parent) {
    onEntry {
        delay(2000)
        Furhat.dialogHistory.clear()
//        furhat.say("Hello, I am Furhat")
        reentry()
    }

    onReentry {
        furhat.listen()
    }

//    onUserLeave {
//        goto(Idle)
//    }

    onResponseFailed {
//        furhat.ask(it.text)
        println("no internet")
    }

//    onResponse<GoodBye> {
    onResponse(listOf(ExitIntent(), CancelIntent(), )) {
        furhat.say("Okay, goodbye")
        delay(2000)
        furhat.say {
            random {
                +"I hope that was an insightful demonstration of generative A I and social robots"
                +"I hope you enjoyed conversing with a social robot "
                +"I hope you found that interesting"
            }
        }
        goto(Sleeping)
//        reentry()
    }
    onResponse<GoToPassiveIntent> {
        furhat.say({
            random {
                +"Okay"
                +"Sure"
            }
        })
        goto(Passive)
    }

    onResponse<DoPresentationIntent> {
        furhat.say(async = true) {
            +Gestures.GazeAway
            random {
                +"Let's see"
                +"Well"
                +"Wait a second"
                +"Hmmm"
            }
        }
        val robotResponse = call {
//            getSimpleOpenAIResponse("tell us about the hri labs")
            getOpenAIResponse("tell us about the hri labs")
        } as String?
        furhat.say(robotResponse?:"Could you please repeat that")
        reentry()
    }


    onResponse<RobotariumIntent> {
        val robotResponse = call {
//            getSimpleOpenAIResponse("tell me about the national robotarium")
            getOpenAIResponse("tell me about the national robotarium")
        } as String?
        furhat.say(robotResponse?:"can help you know more about the national robotarium")
        reentry()
    }

    onResponse {
        val (image,faces) = furhat.cameraFeed.getSnapShot() ?: (null to 0)
        val b64 = (image as? BufferedImage)?.let { getImgBase64(it) }

        furhat.say(async = true) {
            +Gestures.GazeAway
            random {
                +"Let's see"
                +"Let me think"
                +"Wait a second"
                +"Hmmm"
            }
        }

        val robotResponse = call {
//            getDialogCompletion()
            getOpenAIResponse(it.text, b64)
//            getSimpleOpenAIResponse(it.text, b64)
        } as String?
        furhat.say(robotResponse?:"Could you please repeat that")
        reentry()
    }

    onNoResponse {
//        val (image,faces) = furhat.cameraFeed.getSnapShot() ?: (null to 0)
////        println(faces)
//        val b64 = (image as? BufferedImage)?.let { getImgBase64(it) }
////        println(b64)
//        val robotResponse = call {
////            getDialogCompletion()
//            getOpenAIResponse("Mention something new and interesting you can see on the image", b64)
////            getSimpleOpenAIResponse(
////                "Mention something interesting you can see on the image",
////                b64
////            )
//        } as String?
//        furhat.say(robotResponse?:"Sorry, I didn't hear anything")
        reentry()
    }

}

val Passive = state {
    //include(idleButtons)

    onEntry {
//        send(LookAround())
        furhat.listen()
    }

    onReentry {
        furhat.listen()
    }

    onNoResponse {
        furhat.listen()
    }

    onResponse(cond = { it.intent == NullIntent }) {
        furhat.listen()
    }

    onResponse(cond = { it.intent is ChatIntent }) {
        goto(Active(it))
    }

    onResponse(cond = { it.intent is LookIntent }) {
        goto(Active(it))
    }

    onResponse<WakeupIntent> {
        goto(Greeting)
    }

    onResponse(listOf(DoPresentationIntent(), RobotariumIntent(), )) {
        goto(Greeting)
    }
}

// State requiring the user to "wake up" Furhat
val Sleeping = state {
//    include(idleButtons)
    onEntry {
//        furhat.setLED("off")
        furhat.setDefaultMicroexpression(blinking = false, eyeMovements = false, facialMovements = false)
        furhat.gesture(Gestures.CloseEyes, priority = 1)
        furhat.stopSpeaking()
        furhat.listen()
    }

    onReentry {
        furhat.listen()
    }

    onExit {
        //dialogLogger.startSession(cloudToken = "a1671cbb-9b42-4127-b095-5ed12a02ec39")
    }

    onResponse<WakeupIntent> {
        goto(Active())
    }

    onResponse(cond = { it.intent == NullIntent }) {
        furhat.listen()
    }

    onNoResponse {
        furhat.listen()
    }

    onResponse(listOf(DoPresentationIntent(), RobotariumIntent(), )) {
        goto(Greeting)
    }
}