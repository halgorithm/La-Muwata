package lmr.muwata

import lmr.msd.models.Stage

object MoonlightWaterifier {
    fun waterify(moonlight: Stage) {
        for (sceneId in moonlight.scenes.indices) {
            val scene = moonlight.scenes[sceneId]
            val coll = scene.collision

            RuinsWaterifier.waterifyScene(scene)

            // FIXME: 2Fs
            when (sceneId) {
                6 -> { // Michael Jackson
                    coll.select(17, 48 + 18, 5, 1).erase() // pushblock above MJ
                }
                // case 10 // anubis room, water flow right?
            }
        }
    }
}