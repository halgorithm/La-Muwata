package lmr.muwata

import java.nio.file.Path

object Main {
    private val inputGameDir: Path = Path.of("E:\\Games\\La-Mulana 1.0\\")
    private val outputGameDir: Path = Path.of("E:\\Games\\La-Mulana 1.0 WATER\\")

    @JvmStatic
    fun main(args: Array<String>) {
        println("Input La-Mulana directory: $inputGameDir")
        println("Output La-Mulana directory: $outputGameDir")

        println("Waterifying all zones...")
        val waterifier = RuinsWaterifier(inputGameDir, outputGameDir)
        waterifier.waterifyEverything()

        println("All zones waterified!")
    }
}