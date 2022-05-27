package edu.icesi.monage.model

data class User(
    val id:String ="",
    var username:String="",
    var name:String="",
    var email:String="",
    var score:Int=0,
    var money:Int=0,
    var energy:Int=0,
    var food:Int=0,
    var hygiene:Int=0,
    var funny:Int=0,
    var state:String = State.NEU,
    var moneyInv:Int=0,
    var utilidad:Int=0
)
