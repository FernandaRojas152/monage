package edu.icesi.monage.model

object State {
    const val FAN = "FANTASTIC"
    const val HAP =  "HAPPY"
    const val NEU = "NEUTRAL"
    const val SAD = "SAD"
    const val DY = "DYING"

    fun calculateState(food:Int,hygiene:Int,funny:Int):String{
        var calculate = (food+hygiene+funny)/3
        var state = ""
        if (90 <= calculate && calculate<= 100){
            state = FAN
        }else if(60 <= calculate && calculate<= 89){
            state = HAP
        }else if(40 <= calculate && calculate<= 59){
            state = NEU
        }else if(20 <= calculate && calculate<= 39){
            state = SAD
        }else if (0 <= calculate && calculate<= 19){
            state = DY
        }
        return state
    }
}