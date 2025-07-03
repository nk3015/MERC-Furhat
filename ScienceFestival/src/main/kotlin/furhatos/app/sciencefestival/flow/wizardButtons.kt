package furhatos.app.sciencefestival.flow

import furhatos.app.sciencefestival.flow.main.Active
import furhatos.app.sciencefestival.flow.main.Greeting
import furhatos.app.sciencefestival.flow.main.Passive
import furhatos.app.sciencefestival.flow.main.Sleeping
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.Section
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.partialState
import furhatos.gestures.Gestures

val idleButtons = partialState {
    onButton("Go to sleep", section = Section.LEFT, color = Color.Red) {
        goto(Sleeping)
    }

    onButton("Wake up and wait", section = Section.LEFT, color = Color.Yellow) {
        goto(Passive)
    }

    onButton("Start chat!", section = Section.LEFT, color = Color.Green) {
        goto(Active())
    }

    onButton(label = "Welcome group", section = Section.RIGHT, color = Color.Blue) {
        goto(Greeting)
    }
}