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
class SkillEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "skill_name") var skillname :String?=null
    @ColumnInfo(name = "description") var description:String?=null
    @ColumnInfo(name = "pId") var pid:Int?=0

}