package furhatos.app.sciencefestival.flow.main

import furhatos.app.sciencefestival.flow.Init
import furhatos.app.sciencefestival.flow.idleButtons
import furhatos.app.sciencefestival.nlu.WakeupIntent
import furhatos.flow.kotlin.*

val Idle: State = state(Init) {
    include(idleButtons)
    onEntry {
        furhat.attendNobody()
        Furhat.dialogHistory.clear()
        furhat.stopSpeaking()
//        furhat.listen()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Greeting)
    }


//    onResponse<WakeupIntent> {
//        goto(Greeting)
//    }
//
//    onNoResponse {
//        furhat.listen()
//    }
}
