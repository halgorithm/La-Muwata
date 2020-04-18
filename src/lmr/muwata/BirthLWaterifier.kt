package lmr.muwata

import lmr.msd.models.Stage

object BirthLWaterifier {
    fun waterify(birth: Stage) {
        for (sceneId in birth.scenes.indices) {
            val scene = birth.scenes[sceneId]
            val coll = scene.collision

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                4 -> { // below Skanda
                    coll.select((64 * 2) + 31, 42, 2, 1).erase()
                }
            }
        }
    }
}