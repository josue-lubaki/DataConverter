package strategy

import org.w3c.dom.Document
import java.io.File
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Author : Josue Lubaki
 * Version : 1.0
 * Date : 10 Juin 2024
 * */

interface OutputStrategy {
    fun writeOutput(document: Document, resxFileName: String, outputDirectoryPath: String)

    companion object {
        fun writeDocumentToFile(document: Document, filePath: String) {
            val transformerFactory = TransformerFactory.newInstance()
            val transformer = transformerFactory.newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            val source = DOMSource(document)
            val result = StreamResult(File(filePath))
            transformer.transform(source, result)
        }
    }
}

