package lmr.muwata

import lmr.msd.models.Stage
import lmr.msd.models.CollisionType.*

object DimensionalWaterifier {
    fun waterify(dimensional: Stage) {
        for (sceneId in dimensional.scenes.indices) {
            val scene = dimensional.scenes[sceneId]
            val coll = scene.collision

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                1 -> { // Merman
                    coll.select(4, 48, 48, 1).fill(GROUND)
                } 2 -> { // Ox
                    coll.select(31, 0, 2, 1).fill(GROUND)
                } 5 -> { // Flying lion
                    coll.select(8, 0, 48, 1).fill(GROUND)
                } 7 -> { // Wolf
                    coll.select(6, 0, 48, 1).fill(GROUND)
                }
            }
        }
    }
}