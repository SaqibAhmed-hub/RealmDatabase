package com.example.realmdatabase

import io.realm.RealmObject

open class DataModel: RealmObject(){
    var ID = 0
    var name :String? = null
    var email : String? = null
}