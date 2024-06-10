import org.w3c.dom.Document
import org.w3c.dom.Element
import strategy.OutputStrategy
import strategy.StrategyType
import strategy.impl.FileOutputStrategyImpl
import strategy.impl.MrOutputStrategyImpl
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Author : Josue Lubaki
 * Version : 1.0
 * Date : 10 Juin 2024
 * */

fun main() {
    val inputDirectoryPath = "src/main/resources/resx_files"
    val outputDirectoryPath = "src/main/resources/results"
    val strategyType = StrategyType.MR // Change this to "FILE" or "MR" based on your requirement

    createOutputDirectory(outputDirectoryPath)
    val resxFiles = getResxFiles(inputDirectoryPath)

    val outputStrategy = getOutputStrategy(strategyType)

    if (resxFiles.isNullOrEmpty()) {
        println("Aucun fichier .resx trouvé dans le répertoire: $inputDirectoryPath")
    } else {
        for (resxFile in resxFiles) {
            processResxFile(resxFile, outputDirectoryPath, outputStrategy)
        }
    }
}

/**
 * Renvoie l'implémentation de la stratégie de sortie en fonction du type spécifié.
 *
 * @param strategyType Type de stratégie (StrategyType.FILE ou StrategyType.MR).
 * @return Implémentation de OutputStrategy correspondant au type spécifié.
 */
fun getOutputStrategy(strategyType: StrategyType): OutputStrategy {
    return when (strategyType) {
        StrategyType.MR -> MrOutputStrategyImpl()
        else -> FileOutputStrategyImpl()
    }
}

/**
 * Crée le répertoire de sortie s'il n'existe pas.
 *
 * @param path Chemin du répertoire de sortie.
 */
fun createOutputDirectory(path: String) {
    val outputDirectory = File(path)
    if (!outputDirectory.exists()) {
        outputDirectory.mkdirs()
    }
}

/**
 * Liste tous les fichiers .resx dans le répertoire spécifié.
 *
 * @param directoryPath Chemin du répertoire contenant les fichiers .resx.
 * @return Un tableau de fichiers .resx trouvés dans le répertoire.
 */
fun getResxFiles(directoryPath: String): Array<File>? {
    val directory = File(directoryPath)
    return directory.listFiles { _, name -> name.endsWith(".resx") }
}

/**
 * Traite un fichier .resx spécifique pour le convertir en fichier XML de ressources Android.
 *
 * @param resxFile Fichier .resx à traiter.
 * @param outputDirectoryPath Chemin du répertoire de sortie pour enregistrer le fichier XML converti.
 * @param outputStrategy Stratégie de sortie pour enregistrer le fichier XML converti.
 */
fun processResxFile(resxFile: File, outputDirectoryPath: String, outputStrategy: OutputStrategy) {
    val documentBuilderFactory = DocumentBuilderFactory.newInstance()
    val documentBuilder = documentBuilderFactory.newDocumentBuilder()
    val inputDocument: Document = documentBuilder.parse(resxFile)

    val outputDocument = createOutputDocument(documentBuilder)
    populateOutputDocument(inputDocument, outputDocument)

    outputStrategy.writeOutput(outputDocument, resxFile.name, outputDirectoryPath)
}

/**
 * Crée un document XML de sortie avec une balise racine <resources>.
 *
 * @param documentBuilder Constructeur de document XML.
 * @return Document XML de sortie avec une balise racine <resources>.
 */
fun createOutputDocument(documentBuilder: javax.xml.parsers.DocumentBuilder): Document {
    val outputDocument = documentBuilder.newDocument()
    val resourcesElement = outputDocument.createElement("resources")
    outputDocument.appendChild(resourcesElement)
    return outputDocument
}

/**
 * Remplit le document XML de sortie avec les éléments <string> basés sur les données du fichier d'entrée.
 *
 * @param inputDocument Document XML d'entrée (fichier .resx).
 * @param outputDocument Document XML de sortie à remplir.
 */
fun populateOutputDocument(inputDocument: Document, outputDocument: Document) {
    val dataNodes = inputDocument.getElementsByTagName("data")
    val resourcesElement = outputDocument.documentElement

    for (i in 0..<dataNodes.length) {
        val dataElement = dataNodes.item(i) as Element
        val name = dataElement.getAttribute("name").lowercase().replace('_', '_')
        val valueNode = dataElement.getElementsByTagName("value").item(0)
        val value = valueNode.textContent.replace("'", "\\'")

        val stringElement = outputDocument.createElement("string")
        stringElement.setAttribute("name", name)
        stringElement.textContent = value
        resourcesElement.appendChild(stringElement)
    }
}

