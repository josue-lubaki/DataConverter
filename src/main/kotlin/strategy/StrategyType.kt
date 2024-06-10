package strategy

/**
 * Author : Josue Lubaki
 * Version : 1.0
 * Date : 10 Juin 2024
 * */

enum class StrategyType {

    /**
     * Utilise la stratégie "FILE" pour créer un fichier XML de sortie pour chaque fichier .resx.
     * Par exemple, si le fichier d'entrée est "Localization.fr.resx", la sortie sera "strings-fr.xml".
     */
    FILE,

    /**
     * Utilise la stratégie "MR" (Multi-Repertoire) pour créer des fichiers XML de sortie dans des répertoires distincts
     * basés sur le code de langue extrait des noms de fichier .resx.
     * Par exemple, si le fichier d'entrée est "Localization.fr.resx", la sortie sera le fichier "strings.xml"
     * placé dans le répertoire "fr".
     */
    MR
}
