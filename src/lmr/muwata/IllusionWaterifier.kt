package lmr.muwata

import lmr.msd.models.Stage

object IllusionWaterifier {
    fun waterify(illusion: Stage) {
        for (sceneId in illusion.scenes.indices) {
            val scene = illusion.scenes[sceneId]
            val coll = scene.collision

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                4 -> { // Grail tablet pit
                    coll.select(28, 48 + 34, 8, 1).erase() // pushblock to ladder
                    coll.select(14, 48 + 42, 18, 1).erase() // floor under ladder
                } 5 -> { //
                    // move.exe shop screen
                    coll.select(30, 10, 13, 1).erase() // first green pushblock
                    coll.select(46, 6, 4, 1).erase() // top green pushblock

                    coll.select(64 + 24, 18, 9, 1).erase() // Lizard puzzle pushblock
                    coll.select(64 + 30, 14, 4, 6).ladderFill() // Lizard puzzle ladder (can't be wet or lizard won't climb it) (TODO: maybe we can just make the top of it wet?
                }
            }
        }
    }
}