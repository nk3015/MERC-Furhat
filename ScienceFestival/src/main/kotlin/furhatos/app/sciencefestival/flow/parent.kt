package furhatos.app.sciencefestival.flow

import furhatos.app.sciencefestival.ScienceFestival
import furhatos.app.sciencefestival.flow.main.Idle
import furhatos.app.sciencefestival.flow.main.Sleeping
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.util.CommonUtils

val Parent: State = state {
    include(idleButtons)
    val logger = CommonUtils.getLogger(ScienceFestival::class.java)
    onUserEnter(instant = true) {
        when { // "it" is the user that entered
            furhat.isAttendingUser -> furhat.glance(it) // Glance at new users entering
            !furhat.isAttendingUser -> furhat.attend(it) // Attend user if not attending anyone
        }
    }

    onUserLeave(instant = true) {
        logger.info("RE ENTER! ${thisState.name}")
        logger.info("RE ENTER STATE! ${this.currentState.name}")
        when(thisState.name) {
            "Sleeping" -> furhat.gesture(Gestures.Shake)
            "Passive" -> furhat.gesture(Gestures.Roll)
            else ->
                when {
                    !users.hasAny() -> { // last user left
                        furhat.attendNobody()
                        goto(Idle)
                    }
                    furhat.isAttending(it) -> furhat.attend(users.other) // current user left
                    !furhat.isAttending(it) -> furhat.glance(it.head.location) // other user left, just glance
                }
        }
    }

}
