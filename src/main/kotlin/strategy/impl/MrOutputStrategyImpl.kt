package strategy.impl

import org.w3c.dom.Document
import strategy.OutputStrategy
import strategy.OutputStrategy.Companion.writeDocumentToFile
import java.io.File

/**
 * Author : Josue Lubaki
 * Version : 1.0
 * Date : 10 Juin 2024
 * */

class MrOutputStrategyImpl : OutputStrategy {
    override fun writeOutput(document: Document, resxFileName: String, outputDirectoryPath: String) {
        val languageCode = getLanguageCode(resxFileName)
        val languageDirectory = "$outputDirectoryPath/$languageCode"
        createDirectory(languageDirectory)
        val outputFilePath = "$languageDirectory/strings.xml"
        writeDocumentToFile(document, outputFilePath)
        println("Le fichier XML a été généré avec succès: $outputFilePath")
    }

    /**
     * Extrait le code de langue du nom de fichier d'entrée.
     *
     * @param inputFileName Nom du fichier d'entrée.
     * @return Code de langue extrait.
     */
    private fun getLanguageCode(inputFileName: String): String {
        val parts = inputFileName.split('.')
        return if (parts.size > 2) {
            parts[parts.size - 2]
        } else {
            "base"
        }
    }

    /**
     * Crée un répertoire s'il n'existe pas.
     *
     * @param path Chemin du répertoire à créer.
     */
    private fun createDirectory(path: String) {
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

}
