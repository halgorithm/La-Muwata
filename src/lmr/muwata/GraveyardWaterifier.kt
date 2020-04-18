package lmr.muwata

import lmr.msd.models.Stage
import lmr.msd.models.CollisionType.*

object GraveyardWaterifier {
    fun waterify(graveyard: Stage) {
        for (sceneId in graveyard.scenes.indices) {
            val scene = graveyard.scenes[sceneId]
            val coll = scene.collision

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                5 -> { // Gauntlet pushblock puzzle
                    coll.select(64 + 20, 10, 4, 1).erase() // top pushblock
                    coll.select(64 + 28, 26, 8, 1).erase() // middle pushblock
                    coll.select(64 + 14, 34, 22, 1).erase() // bottom pushblock
                    coll.select(64, 42, 44, 1).setEach(RuinsWaterifier::dryOff) // floor
                } 7 -> { // Below Kamaitachi
                    coll.select(24, 10, 31, 1).fill(EMPTY) // Kamaitachi pushblock
                }
            }
        }
    }
}