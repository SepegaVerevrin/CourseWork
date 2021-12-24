package com.example.c4

import android.widget.Toast
import java.lang.Integer.parseInt
import kotlin.math.pow

class Translator {

    fun floatTranslator(number:String, inputNumSystem:Int, outputNumSystem:Int):String {

        var res = ""
        val numberSplit = number.split(".").toList()
        if ((numberSplit[0].all { it.code-48 < inputNumSystem } && inputNumSystem in 1..10 && outputNumSystem in 1..36)||(numberSplit[0].all { it.code-65+10 < inputNumSystem || it.code-97+10 < inputNumSystem } && inputNumSystem in 11..36 && outputNumSystem in 1..36)) {
            var newNumber = numberSplit[0] //
            when (inputNumSystem) {
                10 -> {
                    res += newNumber.toInt().toString(outputNumSystem)
                }
                else -> {

                    var tmp = newNumber.map { if (it.code in '0'.code..'9'.code) it.code-48 else  if (it.code in 'A'.code..'Z'.code) it.code-65+10 else it.code-97+10 }

                    var tmp2=0
                    tmp.
                    reversed().
                    forEachIndexed { index, it ->
                        tmp2 += it*(inputNumSystem.toDouble().pow(index).toInt())
                    }.toString()
                    res += tmp2.toString(outputNumSystem)
                }

            }
        }
        res+="."
        if ((numberSplit[1].all { it.code-48 < inputNumSystem } && inputNumSystem in 1..10 && outputNumSystem in 1..36)||(numberSplit[1].all { it.code-65+10 < inputNumSystem || it.code-97+10 < inputNumSystem } && inputNumSystem in 11..36 && outputNumSystem in 1..36)) {
            when (inputNumSystem) {
                10 -> {
                    val length = 10.toDouble().pow(numberSplit[1].length).toInt()
                    val newNumber = numberSplit[1].toInt() //

                    var localRes = ""

                    generateSequence(Triple(0 ,newNumber,1)) {
                        Triple(
                            (it.second * outputNumSystem) / length,
                            (it.second * outputNumSystem) % length,
                            it.third+1
                        )
                    }.takeWhile { it.third<16 &&( it.second != 0 || it.first != 0 )}.forEach { localRes += it.first }

                    for (i in 1..localRes.length - 1) res += localRes[i]
                }
                else -> {
                    val number = numberSplit[1] //
                    var tmp =
                        number.map { if (it.code in '0'.code..'9'.code) it.code - 48 else if (it.code in 'A'.code..'Z'.code) it.code - 65 + 10 else it.code - 97 + 10 }

                    var tmp2 = 0
                    tmp.forEachIndexed { index, it ->
                        tmp2 += it * (inputNumSystem.toDouble().pow(-(index+1)).toInt())
                    }.toString()


                    val length = 10.toDouble().pow(numberSplit[1].length).toInt()
                    val newNumber = tmp2 //

                    var localRes = ""

                    generateSequence(Triple(0 ,newNumber,1)) {
                        Triple(
                            (it.second * outputNumSystem) / length,
                            (it.second * outputNumSystem) % length,
                            it.third+1
                        )
                    }.takeWhile { it.third<16 &&( it.second != 0 || it.first != 0 )}.forEach { localRes += it.first }

                    for (i in 1..localRes.length - 1) res += localRes[i]
                }

            }
        }
        return res // (:
    }

    fun integerTranslator(number:String, inputNumSystem:Int, outputNumSystem:Int):String {

        var res = ""
        if ((number.all { it.code-48 < inputNumSystem } && inputNumSystem in 1..10 && outputNumSystem in 1..36)||(number.all { it.code-65+10 < inputNumSystem || it.code-97+10 < inputNumSystem } && inputNumSystem in 11..36 && outputNumSystem in 1..36)) {
            var newNumber = number //
            when (inputNumSystem) {
                10 -> {
                    res = newNumber.toInt().toString(outputNumSystem)
                }
                else -> {

                    var tmp = newNumber.map { if (it.code in '0'.code..'9'.code) it.code-48 else  if (it.code in 'A'.code..'Z'.code) it.code-65+10 else it.code-97+10 }

                    var tmp2=0
                    tmp.
                    reversed().
                    forEachIndexed { index, it ->
                        tmp2 += it*(inputNumSystem.toDouble().pow(index).toInt())
                    }.toString()
                    res = tmp2.toString(outputNumSystem)
                }

            }
        }
        return res
    }
}