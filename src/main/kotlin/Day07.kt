class Day07 {


    interface Size {
        val size: Int
    }

    data class File(val path: String, val filename: String, override val size: Int) : Size

    data class Folder(
        val path: String,
        val name: String,
        val parentFolder: Folder?,
        var folders: MutableSet<Folder> = mutableSetOf(),
        var files: MutableSet<File> = mutableSetOf()
    ) : Size {
        override fun hashCode(): Int {
            return path.hashCode()
        }

        override val size: Int
            get() = (files + folders).sumOf { it.size }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Folder

            if (path != other.path) return false

            return true
        }

        fun printContent(level: Int = 1) {
            val spacer = " ".repeat(level) + "- "
            val spacerContent = " ".repeat(level + 1) + "- "
            println("${spacer}${name} (dir)")
            files.forEach { f -> println("${spacerContent}${f.filename} (file)") }
            folders.forEach { f -> f.printContent(level + 1) }
        }


        fun traverse(acc: List<Folder> = emptyList()): List<Folder> {
           return acc + this@Folder + folders.flatMap { f -> f.traverse() }
        }
    }

    fun solve() {

        val rootFolder = Folder(name = "root", parentFolder = null, path = "/")

        var currentFolder = rootFolder

        loadFile("Day07Input.txt")
            .readLines()
            .forEach { line ->
                if (line.startsWith("$ cd")) {
                    val (_, _, folderArg) = line.split(" ")

                    when (folderArg) {
                        "/" -> {
                            currentFolder = rootFolder
                        }

                        ".." -> {
                            val parentFolder = currentFolder.parentFolder ?: throw Error("Cannot go up")
                            currentFolder = parentFolder
                        }

                        else -> {
                            val folderName = folderArg
                            currentFolder = currentFolder.folders.find { it.name == folderName }
                                ?: throw Error("Cannot find folder $folderName")
                        }
                    }

                }

                if (line.startsWith("dir")) {
                    val (_, name) = line.split(" ")
                    currentFolder.folders.add(
                        Folder(
                            name = name,
                            parentFolder = currentFolder,
                            path = "${currentFolder.path}${if (currentFolder.path != "/") "/" else ""}$name"
                        )
                    )
                }

                val (firstPart, name) = line.split(" ")

                if (firstPart.first().isDigit()) {
                    val size = firstPart.toInt()
                    currentFolder.files.add(
                        File(
                            path = "${currentFolder.path}${if (currentFolder.path != "/") "/" else ""}$name",
                            filename = name, size = size
                        )
                    )
                }
            }

        rootFolder.printContent()

        println("Total size: ${rootFolder.size}")


        val sizeOfFoldersWithGivenSize = rootFolder
            .traverse()
            .filter { it.size <= 100_000 }
            .sumOf { it.size }

        println("Problem 1: Size of folders with given size: $sizeOfFoldersWithGivenSize")


        val hardDriveSize = 70_000_000
        val currentlyOccupied = rootFolder.size
        val currentFreeSpace = hardDriveSize - currentlyOccupied
        val freeSpaceNeeded = 30_000_000

        val needsToBeDeleted =  freeSpaceNeeded - currentFreeSpace

        rootFolder
            .traverse()
            .filter { it.size >= needsToBeDeleted }
            .minByOrNull { it.size }
            ?.let { println("Problem 2: Folder to be deleted: ${it.path} size: ${it.size}") }
    }
}


fun main() {

    Day07().solve()
}