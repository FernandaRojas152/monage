package edu.icesi.monage.model

class UserGameState( var user: User) {


    //Funciones Piso
    var hungryFloor:Int = 35
    var funFloor:Int = 25
    var cleanFloor:Int = 40

    //Pesos
    val hungryWeigth:Double = 0.5
    val funWeight:Double = 0.2
    val cleanWeight:Double = 0.3

    //Dias con hambre
    var starvingDays:Int = 4

    fun totalCalculation():Int{

        var total:Int = 0
        starvingLuck()
        if(starvingDays==0){
            //Si llega a -1 ya está muerto
            total = -1
            return total
        }
        var totalHungry = calculateHunger(user.food)
        var totalFun = calculateFun(user.funny)
        var totalClean = calculateHigiene(user.hygiene)

        total = totalHungry+totalFun+totalClean
        if(total < 0) total = 0

        return total ;
    }

    fun calculateHunger(food: Int): Int {
        var result:Int = (food*hungryWeigth).toInt()
        if(result < hungryFloor){
            starvingLuck()
        }
        else if(result>50){
            if(starvingDays<4){
                starvingDays++
            }
        }
        return result
    }

    fun calculateFun(funStat:Int):Int{
        var result:Int = (funStat*funWeight).toInt()
        if(result<funFloor){
            result = -15
        }
        return result;
    }

    fun calculateHigiene(higiene:Int):Int{

        var result : Int = (higiene*cleanWeight).toInt()
        if(result<funFloor){
            var toSustract = random(100,500)
            if(user.money-toSustract <= 0){
                user.money = 0

            }else{
                user.money -= toSustract
            }

        }
        return result;

    }

    fun starvingLuck(){
        if (starvingDays ==1) {
            starvingDays = 0
        }else {
            starvingDays = starvingDays - 1
        }
        
    }
    fun random(from: Int, to: Int) = (Math.random() * (to - from) + from).toInt()
}