package furhatos.app.sciencefestival

import furhatos.app.sciencefestival.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class ScienceFestival : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
