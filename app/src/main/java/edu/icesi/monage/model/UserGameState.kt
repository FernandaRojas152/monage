package edu.icesi.monage.model

class UserGameState( var user: User, var state: State) {


    //Funciones Piso
    var hungryFloor:Int = 35
    var funFloor:Int = 20
    var tirednessFloor:Int = 15
    var cleanFloor:Int = 30

    //Pesos
    val hungryWeigth:Double = 0.5
    val funWeight:Double = 0.1
    val tirednessWeight:Double = 0.2
    val cleanWeight:Double = 0.2

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
        var totalTiredness = calculateTired(user.tiredness)
        var totalClean = calculateHigiene(user.hygiene)


        total = totalHungry+totalFun+totalTiredness+totalClean
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
            user.money -= random(100,500)
        }
        return result;

    }
    fun calculateTired(tiredness:Int):Int{
        var result : Int = (tiredness*tirednessWeight).toInt()
        if(result<tirednessFloor){
            user.energy -= random(1,4)

        }
        return result
    }

    fun starvingLuck(){
        if (starvingDays ==1) {
            starvingDays = 0
        }else {

            starvingDays = starvingDays - random(1, starvingDays)

        }
        
    }
    fun random(from: Int, to: Int) = (Math.random() * (to - from) + from).toInt()






}