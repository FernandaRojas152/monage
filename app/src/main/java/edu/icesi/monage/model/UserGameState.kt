package edu.icesi.monage.model

class UserGameState(state:State,user:User) {


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
        
        




        if(starvingDays==0){
            //Si llega a -1 ya está muerto
            total = -1
            return total
        }



        return 0;


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