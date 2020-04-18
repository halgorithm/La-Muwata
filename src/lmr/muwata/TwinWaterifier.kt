package lmr.muwata

import lmr.msd.models.Stage
import lmr.msd.models.CollisionType.*

object TwinWaterifier {
    fun waterify(twin: Stage) {
        for (sceneId in twin.scenes.indices) {
            val scene = twin.scenes[sceneId]
            val coll = scene.collision
            if (sceneId == 39) continue

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                3, 12 -> { // Left and right vertical sections
                    for (screenId in 0..2) {
                        val yOffset = screenId * 48
                        // FIXME: ceilings are still present after poison has been lifted ):
//                        coll.select(13, yOffset, 2, 3).fill(GROUND) // Left ladder ceiling prevents bypassing transitions
//                        coll.select(49, yOffset, 2, 3).fill(GROUND) // Right ladder ceiling prevents bypassing transitions
                        coll.select(13, yOffset + 4, 2, 1).setEach(RuinsWaterifier::dryOff) // Faster left ladder up transitions
                        coll.select(49, yOffset + 4, 2, 1).setEach(RuinsWaterifier::dryOff) // Faster right ladder up transitions
                        coll.select(13, yOffset + 47, 2, 1).setEach(RuinsWaterifier::dryOff) // Faster left ladder down transitions
                        coll.select(49, yOffset + 47, 2, 1).setEach(RuinsWaterifier::dryOff) // Faster right ladder up transitions
                    }
                } 6 -> { // Lamp of Time shop scene
                    coll.select(64 + 10, 18, 4, 1).fill(EMPTY) // top bomb pushblock
                    coll.select(64 + 14, 26, 12, 1).fill(EMPTY) // middle platform
                    coll.select(64 + 16, 34, 4, 1).fill(EMPTY) // center pushblock
                    coll.select(64 + 52, 34, 4, 1).fill(EMPTY) // right pushblock
                    coll.select(64 + 24, 42, 25, 1).fill(EMPTY) // floor
                }
            }

            if (sceneId == 3) {
                coll.select(48, 0, 4, 5).waterLadderFill() // Fix field transition ladder to Mausoleum
            }
        }
    }
}