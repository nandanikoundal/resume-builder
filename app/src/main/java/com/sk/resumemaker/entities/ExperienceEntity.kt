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
class ExperienceEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "job_title") var jobtitle: String?=null
    @ColumnInfo(name = "company_name") var companyname: String?=null
    @ColumnInfo(name = "country_name") var countryname: String?=null
    @ColumnInfo(name = "details") var details: String?=null
    @ColumnInfo(name = "pId") var pid:Int?=0

}