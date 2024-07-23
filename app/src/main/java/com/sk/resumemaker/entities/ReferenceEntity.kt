package com.sk.resumemaker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(foreignKeys = [
    ForeignKey(entity = PersonalInfoEntity::class,
        parentColumns = ["id"],
        childColumns = ["pId"],
        onDelete = ForeignKey.CASCADE
    )])
class ReferenceEntity : Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "Referee_Name") var referee:String?=null
    @ColumnInfo(name = "Job_Title") var job:String?=null
    @ColumnInfo(name = "Company_Name") var company:String?=null
    @ColumnInfo(name = "Email") var email:String?=null
    @ColumnInfo(name = "pId") var pid:Int?=0

}