import java.math.BigInteger
import java.math.BigDecimal
import java.math.RoundingMode

val TWO: BigInteger = BigInteger.TWO
val FIVE: Int = 5
val BIGTWO: BigDecimal = BigDecimal(2)

fun main() {
    var input = ""
    while (input != "/exit") {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
        try {
            var (a, b) = readLine()!!.split(' ').map { it.toInt() }
            while (input != "/exit") {
                print("Enter number in base $a to convert to base $b (To go back type /back) ")
                input = readLine().toString()
                if (input == "/back") {
                    println()
                    break
                }
                else {
                    if ( b != 10) println("Conversion result: ${toNewBase(toDecimal(toNum(input), a), b)}")
                    else {
                        var str = ""
                        var schet1 = 0
                        var schet2 = 0
                        for (i in toDecimal(toNum(input), a)) {
                            str += i
                            if (i == ".") schet2++
                            if (schet2 == 1) schet1++
                            if (schet1 == 6) break
                        }
                        println("Conversion result: $str")
                    }
                    println()
                }
            }
        } catch (e: NumberFormatException) {
            input = "/exit"
        }
    }
}


fun toNum(input: String): MutableList<String> {
    val ip = mutableListOf<String>()
    for (i in input.indices) {
        var chet = 9
        for (j in 'a' .. 'z') {
            chet++
            if (input[i] == j) {
                ip.add(chet.toString())
            }
        }
        if (input[i] == '.') {
            ip.add(".")
        }
        if (input[i] in '0'..'9') ip.add(input[i].toString())
    }
    return ip
}

fun toDecimal(list: MutableList<String>, a: Int): MutableList<String> {
    if (list.contains(".")) {
        var input = BigDecimal("0")

        var integerPart = -1
        for (i in 0 until list.size) {
            if (list[i] == ".") break
            integerPart++
        }

        var fractionalPart = 0
        var test = 0
        for (i in 0 until list.size) {
            if (test == 1) fractionalPart++
            if (list[i] == ".") test++
        }

        if (a != 10) {
            for (i in 0 until list.size) {
                if (integerPart > fractionalPart || list[i] != ".") {
                    val ip = list[i].toDouble() * Math.pow(a.toDouble(), integerPart.toDouble())
                    input += ip.toBigDecimal()
                    integerPart--
                }
            }

            var listNew = mutableListOf<String>()

            for (i in input.toString()) {
                listNew.add(i.toString())
            }
            return listNew
        } else {
            return list
        }
    } else {
        var input = BigInteger("0")
        if (a != 10) {
            for (i in 0 until list.size) {
                input += list[i].toBigInteger() * a.toBigInteger().pow(list.size - i - 1)
            }
            var listNew = mutableListOf<String>()
            for (i in input.toString()) {
                listNew.add(i.toString())
            }
            return  listNew
        } else return list

    }

}

fun toNewBase(list: MutableList<String>, base: Int): String {
    if (list.contains(".")) {
        var strFirst = ""
        for (i in 0 until  list.size) {
            if (list[i] == ".") break else strFirst += list[i]
        }

        var strSecond = ""
        for (i in list.size - 1 downTo 0) {
            if (list[i] == ".") break else strSecond += list[i]
        }
        strSecond = strSecond.reversed()

        var newStrFirst = ""
        var bigInteger = BigInteger(strFirst)
        do {
            val newNum = (bigInteger % base.toBigInteger()).toInt()
            if (newNum in 0..9) newStrFirst += newNum.toString()
            else if (newNum in 10..36) {
                var schet1 = 10
                for (i in 'a'..'z') {
                    if (newNum == schet1) {
                        newStrFirst += i
                    }
                    schet1++
                }
            }
            bigInteger /= base.toBigInteger()
        } while (bigInteger != BigInteger("0"))

        var newStrSecond = ""
        var stepen = 0.0
        var num: String = ""
        var bigDecimal = BigDecimal("0.$strSecond")
        do {
            stepen += 1.0
            bigDecimal *= base.toBigDecimal()
            if (bigDecimal > BigDecimal(0)) {
                for (i in bigDecimal.toString()) {
                        if (i == '.') break else num += i
                }
                var chet = 10
                if (num.toInt() in 0..9) {
                    newStrSecond += num
                } else if (num.toInt() in 10..36) {
                    for (j in 'a'..'z') {
                        if (num.toInt() == chet) {
                            newStrSecond += j
                        }
                        chet++
                    }
                }
                num = ""
                val it = bigDecimal.toInt()
                bigDecimal -= it.toBigDecimal()
            } else newStrSecond += "0"
        } while (stepen <= 4.0)

        var correctSecond = ""
        for (i in 0 until 5) {
            correctSecond += newStrSecond[i]
        }
        println(correctSecond)

        return ("${newStrFirst.reversed()}.$correctSecond")
    } else {
        var str = ""
        for (i in list) {
            str += i
        }
        var ip = str.toBigInteger()
        list.clear()
        do {
            list.add((ip % base.toBigInteger()).toString())
            ip /= base.toBigInteger()
        } while (ip >= base.toBigInteger())

        if (ip == BigInteger.ZERO) else list += ip.toString()

        var newList = mutableListOf<String>()
        var chet = 10
        for (i in list) {
            if (i.toInt() in 0..9) {
                newList.add(i)
            } else if (i.toInt() in 10..36) {
                for (j in 'a'..'z') {
                    if (i.toInt() == chet) {
                        newList.add(j.toString())
                    }
                    chet++
                }
                chet = 10
            }
        }

        str = ""
        for (i in newList.size - 1 downTo 0) {
            str += newList[i]
        }
        str.reversed()
        return str
    }
}