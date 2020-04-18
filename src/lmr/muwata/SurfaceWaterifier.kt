package lmr.muwata

import lmr.msd.models.Stage

object SurfaceWaterifier {
    fun waterify(surface: Stage) {
        val cave = surface.scenes[11]
        cave.collision.setEach { tile, _, y ->
            if (y < 13) tile else RuinsWaterifier.invertWetness(tile)
        }

        val waterfallTop = surface.scenes[6]
        waterfallTop.collision.setEach { tile, _, y ->
            if (y < 42) tile else RuinsWaterifier.invertWetness(tile)
        }

        for (i in 0..1)
            RuinsWaterifier.waterifyScene(surface.scenes[7 + i]) // rest of waterfall canyon
    }
}