package edu.icesi.monage.model

data class User(
    val id:String,
    var username:String,
    var name:String,
    var email:String,
    var score:Int,
    var money:Int,
    var energy:Int,
    var food:Int,
    var hygiene:Int,
    var funny:Int,
    var state:String = State.NEU
)