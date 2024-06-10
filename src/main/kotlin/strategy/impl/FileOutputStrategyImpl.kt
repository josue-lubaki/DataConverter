package strategy.impl

import org.w3c.dom.Document
import strategy.OutputStrategy
import strategy.OutputStrategy.Companion.writeDocumentToFile

/**
 * Author : Josue Lubaki
 * Version : 1.0
 * Date : 10 Juin 2024
 * */

class FileOutputStrategyImpl : OutputStrategy {
    override fun writeOutput(document: Document, resxFileName: String, outputDirectoryPath: String) {
        val outputFilePath = "$outputDirectoryPath/${getOutputFileName(resxFileName)}"
        writeDocumentToFile(document, outputFilePath)
        println("Le fichier XML a été généré avec succès: $outputFilePath")
    }

    /**
     * Génère le nom de fichier de sortie basé sur le nom de fichier d'entrée.
     *
     * @param inputFileName Nom du fichier d'entrée.
     * @return Nom du fichier de sortie.
     */
    private fun getOutputFileName(inputFileName: String): String {
        val parts = inputFileName.split('.')
        return if (parts.size > 2) {
            val languageCode = parts[parts.size - 2]
            "strings-$languageCode.xml"
        } else {
            "strings.xml"
        }
    }
}
