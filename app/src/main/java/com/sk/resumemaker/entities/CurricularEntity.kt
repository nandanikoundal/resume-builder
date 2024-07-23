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
class CurricularEntity  : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "title") var title: String?=null
    @ColumnInfo(name = "content") var content: String?=null
    @ColumnInfo(name = "pId") var pid:Int?=0

}