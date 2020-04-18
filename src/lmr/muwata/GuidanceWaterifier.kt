package lmr.muwata

import lmr.msd.models.Stage

object GuidanceWaterifier : ZoneWaterifier() {
    fun waterify(guidance: Stage) {
        for (sceneId in guidance.scenes.indices) {
            val scene = guidance.scenes[sceneId]
            val coll = scene.collision

            RuinsWaterifier.waterifyScene(scene)

            when (sceneId) {
                4 -> { // Holy Grail pit
                    coll.select(51, 34, 7, 1).setEach(RuinsWaterifier::dryOff) // Top of pit pushblock
                    coll.select(4, 82, 13, 1).setEach(RuinsWaterifier::dryOff) // Holy Grail pushblock
                } 7 -> { // Ankh jewel chest
                    coll.select(64 + 14, 34, 5, 1).setEach(RuinsWaterifier::dryOff) // pushblock left of Ankh jewel chest
                } 8 -> { // Above Amphisbaena
                    coll.select(28, 10, 4, 1).setEach(RuinsWaterifier::dryOff) // top pushblock platform
                    coll.select(20, 26, 24, 1).setEach(RuinsWaterifier::dryOff) // middle pushblock platform
                }
            }
        }
    }
}