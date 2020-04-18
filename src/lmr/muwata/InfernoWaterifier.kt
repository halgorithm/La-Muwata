package lmr.muwata

import lmr.msd.models.Stage
import lmr.msd.models.CollisionType.*

object InfernoWaterifier {
    fun waterify(inferno: Stage) {
        for (sceneId in inferno.scenes.indices) {
            val scene = inferno.scenes[sceneId]
            val coll = scene.collision

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                2 -> { // Pit right of Pazuzu
                    val yOffset = 48 * 2;

                    coll.select(0, yOffset + 18, 28, 1).erase() // top pushblock right of flares puzzle
                    coll.select(38, yOffset + 26, 10, 1).erase()
                    coll.select(22, yOffset + 34, 20, 1).erase()
                    coll.select(20, yOffset + 42, 34, 1).setEach(RuinsWaterifier::dryOff)
                } 4 -> { // Flares puzzle
                    // The air is widened 1 tile on each side only because this screen in particular is trickier than most pushblock puzzles
                    coll.select(15, 10, 12, 1).erase()
                    coll.select(35, 10, 16, 1).erase()

                    coll.select(2, 18, 15, 1).erase()
                    coll.select(23, 18, 20, 1).erase()
                    coll.select(50, 18, 14, 1).erase()

                    coll.select(2, 26, 24, 1).erase()
                    coll.select(34, 26, 17, 1).erase()

                    coll.select(25, 34, 17, 1).erase()

                    coll.select(2, 42, 56, 1).erase()
                } 5 -> { // Below shop
                    // TODO: lava flow up near the lava itself?
                    coll.select(41, 48 + 3, 8, 19).fill(WATER_FLOW_UP) // water column above lava
                    coll.select(2, 48 + 22, 62, 4).fill(WATER_FLOW_UP) // all lava
                } 7 -> { // Below Grail tablet
                    // Below grail tablet
                    coll.select(0, 22, 64, 4).fill(WATER_FLOW_UP) // all lava
                    coll.select(27, 3, 10, 19).fill(WATER_FLOW_UP) // water column above lava

                    // Ice cape screen
                    coll.select(64 + 8, 10, 6, 1).erase() // top pushblock platform
                    coll.select(64 + 12, 18, 10, 1).erase() // middle pushblock platform
                    coll.select(64, 22, 62, 4).setEach { tile -> // all lava
                        if (isLadder(tile)) tile else WATER_FLOW_UP
                    }
                    coll.select(64 + 27, 4, 14, 18).fill(WATER_FLOW_UP) // water column above pedestal
                }
            }
        }
    }
}