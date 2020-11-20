package laba2

import kotlin.math.pow

var power: Int = 0
var polynomial: String = ""

fun main() {
    println("Введите полином...")
    polynomial = readLine()!!
    println("Введите степень...")
    power = readLine()!!.toInt()
    var alphas: Array<Alpha> = equationReduction(createFields())
    alphas = setBinaryAlpha(alphas)
    for (element in alphas) {
        println(element)
    }
    var s: Int
    var a: Int
    var b: Int
    var c: String

    while (true) {
        println("Выберите действие:\n1. Сложение \n2. Умножение \n3. Деление\n4. Логарифм\n5. Анти-логарифм\n6. Выход")
        s = readLine()!!.toInt()
        when (s) {
            1 -> {
                println("Введите первый номер Alpha...")
                a = readLine()!!.toInt()
                println("Введите второй номер Alpha...")
                b = readLine()!!.toInt()
                println(alphas[findInAlphas(alphas, binaryEquationMultiplication(alphas[a + 1], alphas[b + 1]))])
            }
            2 -> {
                println("Введите первый номер Alpha...")
                a = readLine()!!.toInt()
                println("Введите второй номер Alpha...")
                b = readLine()!!.toInt()
                if (a + b >= 2.0.pow(power.toDouble()).toInt() - 1) {
                    println(alphas[a + b + 2 - 2.0.pow(power.toDouble()).toInt()])
                } else {
                    println(alphas[a + 1 + b])
                }
            }
            3 -> {
                println("Введите первый номер Alpha...")
                a = readLine()!!.toInt()
                println("Введите второй номер Alpha...")
                b = readLine()!!.toInt()
                if (a - b <= -1) {
                    println(alphas[a - b + 2.0.pow(power.toDouble()).toInt()])
                } else {
                    println(alphas[a + 1 - b])
                }
            }
            4 -> {
                println("Введите поле...")
                c = readLine()!!
                println("Альфа: ${alphas[findInAlphas(c, alphas)].getPosition()}")
            }
            5 -> {
                println("Введите степень Alpha...")
                a = readLine()!!.toInt()
                println("Полином: ${alphas[findInAlphas(a, alphas)].getEquation()} - ${alphas[findInAlphas(a, alphas)].getBinaryAlpha().contentToString()}")
            }
            6 -> {
                break
            }
        }
    }
}

fun createFields(): Array<Alpha> {
    val list: Array<Alpha> = Array((2.0.pow(power.toDouble())).toInt()) { i -> Alpha("$i") }
    list[0] = Alpha("-")
    list[0].setPosition(-1)
    list[0].setBinaryAlpha(Array(power) { 0 })
    val f: Int = (2.0.pow(power.toDouble())).toInt() - 1
    for (i in 1..f) {
        if (i <= power) {
            list[i] = Alpha("x^${i - 1}")
            list[i].setPosition(i - 1)
        }
    }
    list[power + 1] = Alpha("x^1+x^0")
    list[power + 1].setPosition(power)

    for (i in power + 2..f) {
        list[i] = Alpha(polynomialProduct(list[i - 1].getEquation(), list[2].getEquation()))
        list[i].setPosition(i - 1)
    }
    return list
}

fun polynomialProduct(first: String, second: String): String {
    var word = ""
    val mas1: List<String> = first.split("+")
    val mas2: List<String> = second.split("+")
    for (i in mas1.indices) {
        for (j in mas2.indices) {
            word += equationMultiplication(mas1[i], mas2[j]) + "+"
        }
    }
    return word.dropLast(1)
}

fun equationMultiplication(first: String, second: String): String {
    var word: String = first.dropLast(1) + (first.substring(2).toInt() + second.substring(2).toInt())
    if (word.substring(2).toInt() >= power) {
        word = if (word.substring(2).toInt() == power) {
            "x^1+x^0"
        } else {
            polynomialProduct("x^${word.substring(2).toInt() - power}", "x^1+x^0")
        }
    }
    return word
}

fun equationReduction(array: Array<Alpha>): Array<Alpha> {          // Упрощение полинома
    var mas: Array<Int> = Array(power) { 0 }
    var wordMas: List<String>
    var word = ""
    for (i in 1 until array.size) {
        wordMas = array[i].getEquation().split("+")
        for (j in wordMas.indices) {
            mas[wordMas[j].substring(2).toInt()]++
        }
        for (j in mas.indices) {
            if (mas[j] != 0 && mas[j] % 2 != 0) {
                word += "x^$j+"
            }
        }
        array[i].setEquation(word.dropLast(1))
        word = ""
        mas = Array(power) { 0 }
    }
    return array
}

fun setBinaryAlpha(array: Array<Alpha>): Array<Alpha> {         // Установка бинарной части
    var mas: Array<Int>? = Array(power) { 0 }
    var wordMas: List<String>
    for (i in 1 until array.size) {
        wordMas = array[i].getEquation().split("+")
        for (j in wordMas.indices) {
            mas!![wordMas[j].substring(2).toInt()] = 1
        }
        array[i].setBinaryAlpha(mas?.reversedArray())
        mas = Array(power) { 0 }
    }
    return array
}

fun binaryEquationMultiplication(alpha1: Alpha, alpha2: Alpha): Array<Int> {           // Сложение двух Alpha
    val array: Array<Int> = Array(power) { 0 }
    for (i in 0 until power) {
        array[i] = (alpha1.getBinaryAlpha()!![i] + alpha2.getBinaryAlpha()!![i]) % 2
    }
    return array
}

fun findInAlphas(array: Array<Alpha>, mas: Array<Int>?): Int {
    var counter = 0
    for (i in array.indices) {
        for (j in array[i].getBinaryAlpha()!!.indices) {
            if (array[i].getBinaryAlpha()!![j] == mas!![j]) {
                counter++
            } else {
                break
            }
            if (counter == power) {
                return i
            }
        }
        counter = 0
    }
    return 0
}

fun findInAlphas(word: String, array: Array<Alpha>): Int {
    for(i in array.indices) {
        if(word == array[i].getEquation()) {
            return i
        }
    }
    return 0
}

fun findInAlphas(number: Int, array: Array<Alpha>): Int {
    for(i in array.indices) {
        if(number == array[i].getPosition()) {
            return i
        }
    }
    return 0
}

