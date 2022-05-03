package flashcards

object Log {
    var logs = mutableListOf<String>()
    fun makeLog(a: String) {
        logs.add(a)
    }
}