package com.sk.resumemaker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class PersonalInfoEntity :Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
    @ColumnInfo(name = "firstName") var firstName: String?=null
    @ColumnInfo(name = "lastName") var lastName: String?=null
    @ColumnInfo(name = "email") var email: String?=null
    @ColumnInfo(name = "phone") var phone: String?=null
    @ColumnInfo(name = "address") var address: String?=null
    @ColumnInfo(name = "nationality") var nationality: String?=null
    @ColumnInfo(name = "gender") var gender: String?=null
    @ColumnInfo(name = "image")  var image: String?=null
}