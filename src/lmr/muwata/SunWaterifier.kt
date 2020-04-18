package lmr.muwata

import lmr.msd.models.Stage

object SunWaterifier {
    fun waterify(sun: Stage) {
        for (sceneId in sun.scenes.indices) {
            val scene = sun.scenes[sceneId]
            val coll = scene.collision

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                6 -> { // Above Ellmac
                    coll.select(16, 9, 3, 2).erase() // Blue platform
                }
            }
        }
    }
}