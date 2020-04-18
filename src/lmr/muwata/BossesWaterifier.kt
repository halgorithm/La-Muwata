package lmr.muwata

import lmr.msd.models.CollisionType.*
import lmr.msd.models.Stage

object BossesWaterifier {
    fun waterifyAmphisbaena(amphisbaena: Stage) {
        // TODO: rcd edits
        for (scene in amphisbaena.scenes) {
            scene.collision.setEach { tile, x, _ ->
                if (!RuinsWaterifier.isConvertCandidate(tile))
                    tile
                else
                    if (x < 32) WATER_FLOW_LEFT else WATER_FLOW_RIGHT
            }
        }
    }

    fun waterifySakit(sakit: Stage) {
        // TODO: rcd edits
        for (scene in sakit.scenes) {
            scene.collision.setEach { tile ->
                if (RuinsWaterifier.isConvertCandidate(tile)) WATER_FLOW_DOWN else tile
            }
        }
    }

    fun waterifyEllmac(ellmac: Stage?) {
        // TODO: rcd edits
    }

    fun waterifyBahamut(bahamut: Stage?) {
        // TODO: rcd edits
    }

    fun waterifyViy(viy: Stage?) {
        // TODO: rcd edits
    }

    fun waterifyPalenque(palenque: Stage) {
        // plane ignores collision
        // TODO: rcd edits
        for (scene in palenque.scenes) {
            // make lemeza dab
            scene.collision.select(0, 0, 64, 45).fill(WATER_FLOW_LEFT)
            scene.collision.select(0, 44, 64, 4).fill(ICE)
        }
    }

    fun waterifyTiamat(tiamat: Stage) {
        // TODO: rcd edits
        for (scene in tiamat.scenes) {
            val coll = scene.collision
            if (coll.width().toInt() != 64 && coll.height().toInt() != 48) continue

            // Left platforms
            coll.select(2, 0, 16, 44).setEach { tile, x, y ->
                if (RuinsWaterifier.isConvertCandidate(tile))
                    if (x / 8 == 0) WATERFALL else LAVA
                else tile
            }

            // Center
            coll.select(18, 0, 28, 44).fill(LAVA_FLOW_DOWN)

            // Right platforms
            coll.select(46, 0, 16, 44).setEach { tile, x, y ->
                if (RuinsWaterifier.isConvertCandidate(tile))
                    if (x / 8 == 1) WATERFALL else LAVA
                else tile
            }

            // Triangle of confusion
            val triangle = scene.collision.select { x, y ->
                x in 19..45 && y > 24 && y < 44 && x > 31.5 - (y * 0.7 - 13) && x < 31.5 + (y * 0.7 - 13)
            }
            triangle.setEach { x, _ ->
                if (x < 32) LAVA_FLOW_LEFT else LAVA_FLOW_RIGHT
            }

            // Inverted triangle of pain
            coll.select(31, 22, 2, 2).fill(WATERFALL)
            //            scene.collision
//                    .select(28, 21, 8, 4)
//                    .select((x, y) -> x > y - 0.5f && x < 8 - y)
//                    .fill(CollisionType.WATERFALL);
        }
    }
}