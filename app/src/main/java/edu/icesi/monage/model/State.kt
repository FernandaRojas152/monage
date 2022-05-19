package edu.icesi.monage.model

object State {
    const val FAN = "FANTASTIC"
    const val HAP =  "HAPPY"
    const val NEU = "NEUTRAL"
    const val SAD = "SAD"
    const val DY = "DYING"

    fun calculateState(total:Int):String{
        var state = ""
        if (90 <= total && total<= 100){
            state = FAN
        }else if(60 <= total && total<= 89){
            state = HAP
        }else if(40 <= total && total<= 59){
            state = NEU
        }else if(20 <= total && total<= 39){
            state = SAD
        }else if (0 <= total && total<= 19){
            state = DY
        }
        return state
    }
}