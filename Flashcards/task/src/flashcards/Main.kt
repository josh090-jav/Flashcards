package flashcards

import kotlin.random.Random
import java.io.File
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val que = mutableMapOf<String, String>()
    val stat = mutableMapOf<String, Int>()

    if(args.isNotEmpty()) {
        if(args.contains("-import")) {
            importFile(args[args.indexOf("import") + 1], que, stat)
        } else que.clear()
        if(args.contains("-export")) {
            exportFile(args[args.indexOf("export") + 1], que, stat)
        }
    }

//    var count = mutableListOf(0)
    // println("Input the number of cards:")
    // val n = readLine()!!.toInt()
    // var count = 0

    while(true) {
        println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        Log.makeLog("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        val one = readLine()!!
        Log.makeLog(one)
        when (one.lowercase(Locale.getDefault())) {
            "add" -> {
                println("The card:")
                Log.makeLog("The card:")
                val t = readLine()!!
                Log.makeLog(t)
                if (t in que.keys) {
                    println("The card \"$t\" already exists.")
                    Log.makeLog("The card \"$t\" already exists.")
                } else {
                    println("The definition of the card:")
                    Log.makeLog("The definition of the card:")
                    val d = readLine()!!
                    Log.makeLog(d)
                    if (d in que.values) {
                        println("The definition \"$d\" already exists.")
                        Log.makeLog("The definition \"$d\" already exists.")
                    } else {
                        add(t, d, que)
                    }
                }
            }
            "remove" -> {
                println("Which card:")
                Log.makeLog("Which card:")
                remove(readLine()!!, que)
            }
            "import" -> {
                println("File name:")
                Log.makeLog("File name:")
                importFile(readLine()!!, que, stat)
            }
            "export" -> {
                println("File name")
                Log.makeLog("File name")
                exportFile(readLine()!!, que, stat)
            }
            "ask" -> {
                println("How many times to ask?")
                Log.makeLog("How many times to ask?")
                repeat(readLine()!!.toInt()) { ask(que, stat) }
            }
            "log" -> {
                println("File name")
                Log.makeLog("File name")
                log(readln())
            }
            "exit" -> {
                if(args.isNotEmpty()) {
                    if(args.contains("-export")) exportFile(args[args.indexOf("-export") + 1], que, stat)
                    exit()
                } else exit()
                break
            }
            "hardest card" -> {
                hardest(stat)
            }
            "reset stats" -> {
                reset(stat)
            }
        }

    }
    /*
    repeat(n) {
        println("card #${++count}:")
        var k = readLine()!! // the term
        while (true) {
            if (k in que.keys) {
                println("The term \"$k\" already exists. Try again:")
            } else break
            k = readLine()!!
        }

        println("The definition for card #$count:")
        var v = readLine()!! // the definition
        while (true) {
            if (v in que.values) {
                println("The definition \"$v\" already exists. Try again:")
            } else break
            v = readLine()!!
        }
        que[k] = v
    }
    for ((key, value) in que) {
        println("Print the definition of \"$key\":")
        var ans = readLine()!!
        if (ans == value) {
            println("Correct!")
        } else if(ans in que.values) {
            var rKey = ""
            for ((key, value) in que) {
                if (ans == value) {
                    rKey = key
                    break
                }
            }
            println("Wrong. The right answer is \"$value\", but your definition is correct for \"$rKey\".")
        } else {
            println("Wrong. The right answer is \"$value\".")
        }
    }
    */

}
fun add(a: String, b: String, que: MutableMap<String,String>) {

    que[a] = b
    println("The pair (\"$a\":\"$b\") has been added.")
    Log.makeLog("The pair (\"$a\":\"$b\") has been added.")
}

fun remove(a: String, que: MutableMap<String,String>) {
    Log.makeLog(a)
    if(a in que.keys) {
        que.remove(a)
        println("The card has been removed.")
        Log.makeLog("The card has been removed.")
    } else {
        println("Can't remove \"$a\": there is no such card.")
        Log.makeLog("Can't remove \"$a\": there is no such card.")
    }
}

fun importFile(a: String, que: MutableMap<String,String>, err: MutableMap<String,Int>) {
    Log.makeLog(a)
    val files = File(a)
    var num = 0
    if (files.exists()) {
//        val lines = files.readLines()
        files.forEachLine {
            val (a, b, c) = it.split(":")
            que[a] = b
            if (c.toInt() != 0) {
                err[a] = c.toInt()
            }
            ++num
        }
//        println("This is stat now: $err")
        if (num > 0) {
            println("$num cards have been loaded.")
        }
//        println("$num cards have been loaded.")
        Log.makeLog("$num cards have been loaded.")
    } else {
        println("File not found.")
        Log.makeLog("File not found.")
    }
}

fun exportFile(a: String, que: MutableMap<String,String>, err: MutableMap<String,Int>) {
    Log.makeLog(a)
    val exFile = File(a)
    if (que.isNotEmpty()) {
        if (exFile.exists()) {
            for ((key, value) in que) {
                if (key in err) {
                    exFile.writeText("$key:$value:${err[key]}\n")
//                println("Inside stat: key $key, val ${err[key]}")
                } else {
                    exFile.writeText("$key:$value:0\n")
                }
            }
        } else {
            for ((key, value) in que) {
                if (key in err) {
                    exFile.appendText("$key:$value:${err[key]}\n")
//                println("Inside stat: key $key, val ${err[key]}")
                } else {
                    exFile.appendText("$key:$value:0\n")
                }
            }
        }
    }

    val num = if (que.isEmpty()) 1 else que.size
    println("$num cards have been saved.")
    Log.makeLog("$num cards have been saved.")
}

fun ask(que: MutableMap<String,String>, stat: MutableMap<String,Int>) {
    val list = mutableListOf<String>()
    for ((key,_) in que) {
        list.add(key)
    }
    val rand = Random.nextInt(list.size)
    println("Print the definition of \"${list[rand]}\":")
    Log.makeLog("Print the definition of \"${list[rand]}\":")
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
//         stat[rKey] = ++count[0]
        if (stat[rKey] != null) {
            stat[rKey] = stat.getValue(rKey) + 1
        } else {
            stat[rKey] = 1
        }

        println("Wrong. The right answer is \"${que[list[rand]]}\", but your definition " +
                "is correct for \"$rKey\".")
        Log.makeLog("Wrong. The right answer is \"${que[list[rand]]}\", but your definition " +
                "is correct for \"$rKey\".")
    } else {
        println("Wrong. The right answer is \"${que[list[rand]]}\".")
        Log.makeLog("Wrong. The right answer is \"${que[list[rand]]}\".")
//        count
//        stat[list[rand]] = ++count[0]
        if (stat[list[rand]] != null) {
            stat[list[rand]] = stat.getValue(list[rand]) + 1
        } else {
            stat[list[rand]] = 1
        }

    }
}

fun log (a: String) {
    Log.makeLog(a)
    Log.makeLog("The log has been saved.")
    for (w in Log.logs){
        File(a).appendText("$w\n")
    }
    println("The log has been saved.")
}

fun exit () {
    println("Bye bye!")
    Log.makeLog("Bye bye!")
    exitProcess(0)
}

fun hardest(stat: MutableMap<String,Int>) {
    val list = mutableListOf<Int>() //number of time failed for each key
    val keys = mutableListOf<String>() //keys failed
//    println(stat)
    for ((key, value) in stat) {
        list.add(value)
        keys.add(key)
    }
    val max = list.maxOrNull()
    var total = 0
    for (a in list) {
        if (a == max) total += a
    }
    if (stat.isEmpty()) {
        println("There are no cards with errors.")
        Log.makeLog("There are no cards with errors.")
    } else {
        if (total > max!!) {
            println("The hardest cards are \"${keys.joinToString("\", \"")}\". You have " +
                    "$total errors answering them.")
            Log.makeLog("The hardest cards are \"${keys.joinToString("\", \"")}\". You have " +
                    "$total errors answering them.")
        } else {
            println("The hardest card is \"${keys[list.indexOf(max)]}\". You have " +
                    "$total errors answering it.")
            Log.makeLog("The hardest card is \"${keys[list.indexOf(max)]}\". You have " +
                    "$total errors answering it.")
        }
    }
}

fun reset (stat: MutableMap<String,Int>) {
    stat.clear()
    println("Card statistics have been reset.")
    Log.makeLog("Card statistics have been reset.")
}
