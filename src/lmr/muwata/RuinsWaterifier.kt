package lmr.muwata

import lmr.msd.io.MsdParse
import lmr.msd.io.MsdSerialize
import lmr.msd.models.Collision
import lmr.msd.models.CollisionType.*
import lmr.msd.models.Scene
import lmr.msd.models.Stage
import java.nio.file.Path

class RuinsWaterifier(inputGameDir: Path, outputGameDir: Path) {
    private val inputMapsDir = getMapsDir(inputGameDir)
    private val outputMapsDir = getMapsDir(outputGameDir)

    companion object {
        private val WATER_TO_AIR = mapOf(
            WATER to EMPTY,
            WATER_LADDER_LEFT to LADDER_LEFT,
            WATER_LADDER_LEFT_CENTER to LADDER_LEFT_CENTER,
            WATER_LADDER_RIGHT_CENTER to LADDER_RIGHT_CENTER,
            WATER_LADDER_RIGHT to LADDER_RIGHT
        )

        // FIXME: AIR_TO_WATER should be WATER_TO_AIR inverted plus YOWIE_TILE to WATER

        private val AIR_TO_WATER = {
            val map = WATER_TO_AIR.entries.associateBy({ it.value }) { it.key }.toMutableMap()
            map[YOWIE_TILE] = WATER
            map.toMap()
        }.invoke()

        private val INVERT_WETNESS = WATER_TO_AIR + AIR_TO_WATER

        private val ZONES_TO_PROCESS = mutableListOf<Int>()
        private val BOSSES_TO_PROCESS = mutableListOf<Int>()

        fun waterifyAllScenes(zone: Stage) {
            for (scene in zone.scenes) waterifyScene(scene)
        }

        fun waterifyScene(scene: Scene) {
            val coll = scene.collision
            coll.setEach { tile, x, y ->
                val walkable = isWalkTile(coll, x.toInt(), y.toInt()) // FIXME: detects 45deg slopes as walkable
                if (!walkable) return@setEach moistenUp(tile)

                if (tile == WATERFALL) return@setEach WATER_FLOW_DOWN

                val isLeftCorner = (
                    isSolid(coll.getTile(x - 2, y.toInt()))
                    && !isSolid(coll.getTile(x - 1, y.toInt()))
                )
                val isRightCorner = (
                    isSolid(coll.getTile(x + 2, y.toInt()))
                    && !isSolid(coll.getTile(x + 1, y.toInt()))
                )
                if (isLeftCorner || isRightCorner) dryOff(tile)
                else moistenUp(tile)
            }
        }

        fun getMapsDir(gameDir: Path): Path {
            return Path.of(String.format("%s/data/mapdata", gameDir))
        }

        fun getMapPath(msdsDir: Path, prefix: String, id: Int): Path {
            return Path.of(String.format("%s/$prefix%02d.msd", msdsDir, id))
        }

        fun isWet(value: Byte): Boolean {
            return WATER_TO_AIR.containsKey(value)
        }

        fun isDry(value: Byte): Boolean {
            return AIR_TO_WATER.containsKey(value)
        }

        fun isConvertCandidate(collision: Collision, x: Int, y: Int): Boolean {
            return INVERT_WETNESS.containsKey(collision.getTile(x, y))
        }

        fun isConvertCandidate(value: Byte): Boolean {
            return INVERT_WETNESS.containsKey(value)
        }

        fun isWalkTile(coll: Collision, x: Int, y: Int): Boolean {
            return (
                !isSolid(coll.getTile(x, y))
                && !isSolid(coll.getTile(x, y + 1))
                && isSolid(coll.getTile(x, y + 2))
            )
        }

        fun moistenUp(value: Byte): Byte {
            return AIR_TO_WATER.getOrDefault(value, value)
        }

        fun dryOff(value: Byte): Byte {
            return WATER_TO_AIR.getOrDefault(value, value)
        }

        fun invertWetness(value: Byte): Byte {
            return INVERT_WETNESS.getOrDefault(value, value)
        }

        init {
//            ZONES_TO_PROCESS.add(17)
//            BOSSES_TO_PROCESS.add(7)
        }
    }

    fun waterifyEverything() {
        waterifyZones()
        waterifyBosses()
    }

    fun waterifyZones() {
        for (zoneId in 0..25) {
//            if (!ZONES_TO_PROCESS.contains(zoneId)) continue

            val inputMapPath = getMapPath(inputMapsDir, "map", zoneId)
            println("Parsing zone $zoneId...")
            val zone = MsdParse.parse(inputMapPath)
            println("Waterifying zone $zoneId...")

            when (zoneId) {
                0 -> GuidanceWaterifier.waterify(zone)
                1 -> SurfaceWaterifier.waterify(zone)
                3 -> SunWaterifier.waterify(zone)
                4 -> SpringWaterifier.waterify(zone)
                5 -> InfernoWaterifier.waterify(zone)
                7 -> TwinWaterifier.waterify(zone)
                10 -> IllusionWaterifier.waterify(zone)
                11 -> GraveyardWaterifier.waterify(zone)
                12 -> MoonlightWaterifier.waterify(zone)
                16 -> BirthLWaterifier.waterify(zone)
                17 -> DimensionalWaterifier.waterify(zone)
                else -> waterifyAllScenes(zone)
            }

            println("Writing zone $zoneId file...")
            val outputMapPath = getMapPath(outputMapsDir, "map", zoneId)
            MsdSerialize.serialize(zone, outputMapPath)
        }
    }

    fun waterifyBosses() {
        for (bossId in 0..8) {
//            if (!BOSSES_TO_PROCESS.contains(bossId)) continue

            val inputMapPath = getMapPath(inputMapsDir, "boss", bossId)

            println("Parsing boss $bossId...")
            val stage = MsdParse.parse(inputMapPath)

            println("Waterifying boss $bossId...")
            when (bossId) {
                0 -> BossesWaterifier.waterifyAmphisbaena(stage)
                1 -> BossesWaterifier.waterifySakit(stage)
                2 -> BossesWaterifier.waterifyEllmac(stage)
                3 -> BossesWaterifier.waterifyBahamut(stage)
                4 -> BossesWaterifier.waterifyViy(stage)
                5 -> BossesWaterifier.waterifyPalenque(stage)
                7 -> BossesWaterifier.waterifyTiamat(stage)
                else -> waterifyAllScenes(stage)
            }

            println("Writing boss $bossId file...")
            val outputMapPath = getMapPath(outputMapsDir, "boss", bossId)
            MsdSerialize.serialize(stage, outputMapPath)
        }
    }
}