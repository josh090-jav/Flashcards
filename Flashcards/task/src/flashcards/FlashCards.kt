package flashcards

import java.io.File
import kotlin.random.Random

class FlashCards {
    fun add(a: String, b: String, que: MutableMap<String,String>) {

        que[a] = b
        println("The pair (\"$a\":\"$b\") has been added.")
        Log.makeLog("The pair (\"$a\":\"$b\") has been added.")
    }

    fun remove(a: String, que: MutableMap<String,String>) {
        if(a in que.keys) {
            que.remove(a)
            println("The card has been removed.")
            Log.makeLog("The card has been removed.")
        } else {
            println("Can't remove \"$a\": there is no such card.")
            Log.makeLog("Can't remove \"$a\": there is no such card.")
        }
    }

    fun importFile(a: String, que: MutableMap<String,String>) {
        val files = File(a)
        if (files.exists()) {
            val lines = files.readLines()
            for (line in lines) {
                val (x, y) = line.split(Regex("[:\\s+]"))
                que[x] = y
            }
            println("${lines.size + 1} cards have been loaded.")
            Log.makeLog("${lines.size + 1} cards have been loaded.")
        } else {
            println("File not found.")
            Log.makeLog("File not found.")
        }
    }

    fun exportFile(a: String, que: MutableMap<String,String>) {
        for ((key, value) in que) {
            File(a).appendText("$key:$value")
        }
        println("${que.size + 1} cards have been saved.")
        Log.makeLog("${que.size + 1} cards have been saved.")
    }

    fun ask(que: MutableMap<String,String>) {
        val list = mutableListOf<String>()
        var count = 0
        for ((key,_) in que) {
            list.add(key)
        }
        val rand = Random.nextInt()
        println("Print the definition of \"${que[list[rand]]}:")
        Log.makeLog("Print the definition of \"${que[list[rand]]}:")
        val ans = readln()
        Log.makeLog(ans)
        if (que[list[rand]] == ans) {
            println("Correct!")
            Log.makeLog("Correct!")
        } else if(ans in que.values){
            var rKey = ""
            for ((key, value) in que) {
                if (ans == value) {
                    rKey = key
                    break
                }
            }

            println("Wrong. The right answer is \"${que[list[rand]]}\", but your definition " +
                    "is correct for \"$rKey\".")
            Log.makeLog("Wrong. The right answer is \"${que[list[rand]]}\", but your definition " +
                    "is correct for \"$rKey\".")
        } else {
            println("Wrong. The right answer is \"${que[list[rand]]}\".")
            Log.makeLog("Wrong. The right answer is \"${que[list[rand]]}\".")
        }
    }

    fun log (a: String) {
        Log.makeLog("The log has been saved.")
        for (w in Log.logs){
            File(a).appendText("$w\n")
        }
        println("The log has been saved.")
    }

    fun exit () {
        println("Bye bye!")
        Log.makeLog("Bye bye!")
    }

    fun hardest() {

    }
}