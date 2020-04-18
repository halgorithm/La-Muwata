package lmr.muwata

import lmr.msd.models.Stage

object SpringWaterifier {
    fun waterify(spring: Stage) {
        for (sceneId in spring.scenes.indices) {
            val scene = spring.scenes[sceneId]
            if (sceneId == 24) continue  // Lake overlay scene, can't have water outside or it overwrites a ladder

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                2 -> { // normal lake screen
                    scene.collision.select(30, 42, 10, 2).setEach(RuinsWaterifier::dryOff) // pushblock
                }
            }
        }
    }
}