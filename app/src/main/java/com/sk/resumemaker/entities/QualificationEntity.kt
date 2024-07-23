package com.sk.resumemaker.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import java.io.Serializable

@Entity(foreignKeys = [
    ForeignKey(entity = PersonalInfoEntity::class,
        parentColumns = ["id"],
        childColumns = ["pId"],
        onDelete = ForeignKey.CASCADE
    )])
class QualificationEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "course_degree") var course:String?=null
    @ColumnInfo(name = "school_university") var school:String?=null
    @ColumnInfo(name = "grade_score") var grade:String?=null
    @ColumnInfo(name = "year") var year:String?=null
    @ColumnInfo(name = "pId") var pid:Int?=0
}