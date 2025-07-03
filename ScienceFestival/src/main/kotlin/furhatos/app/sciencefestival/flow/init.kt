package furhatos.app.sciencefestival.flow

import furhatos.app.sciencefestival.flow.main.Idle
import furhatos.app.sciencefestival.flow.main.Greeting
import furhatos.app.sciencefestival.setting.DISTANCE_TO_ENGAGE
import furhatos.app.sciencefestival.setting.MAX_NUMBER_OF_USERS
import furhatos.app.sciencefestival.setting.VOICE
import furhatos.app.sciencefestival.setting.CHARACTER
import furhatos.event.senses.SenseSkillGUIConnected


import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.furhat.characters.Characters
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.voice.PollyVoice

import furhatos.skills.HostedGUI


// Our GUI declaration
//val default_gui = HostedGUI("My Default GUI", port = 51234)
//val VARIABLE_SET = "VariableSet"
//val CLICK_BUTTON = "ClickButton"

val Init: State = state {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(DISTANCE_TO_ENGAGE, MAX_NUMBER_OF_USERS)
        furhat.voice = PollyVoice(VOICE)
//        furhat.character = CHARACTER
//        furhat.setCharacter(CHARACTER)
        furhat.setCharacter(Characters.Adult.Jamie)
    }
    onEntry {
        /** start interaction */
        when {
//            furhat.isVirtual() -> goto(Greeting) // Convenient to bypass the need for user when running Virtual Furhat
            users.hasAny() -> {
                furhat.attend(users.random)
                goto(Greeting)
            }
            else -> goto(Idle)
        }
    }

//    onEvent<SenseSkillGUIConnected> {
//        default_gui.write("banana") // Adds an initial line of text
//        default_gui.append("split") // Appends a line to already existing text
//        furhat.say("Do you see banana split on the GUI?")
//
//        println(default_gui.toString())
//    }

}
