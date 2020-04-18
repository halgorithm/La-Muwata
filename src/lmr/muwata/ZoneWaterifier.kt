package lmr.muwata

import lmr.msd.models.Stage

abstract class ZoneWaterifier {
    companion object {
        fun waterify(zone: Stage?) {
            // Implemented by children
        }
    }
}