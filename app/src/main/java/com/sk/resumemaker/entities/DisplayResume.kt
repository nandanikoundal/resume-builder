package com.sk.resumemaker.entities

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

class DisplayResumes:Serializable {
    @Embedded
    var personalInfoEntity: PersonalInfoEntity? = null

    @Relation(parentColumn = "id", entityColumn = "pId")
    var curricularEntity: List<CurricularEntity>? = null

    @Relation(parentColumn = "id", entityColumn = "pId")
    var experienceEntity: List<ExperienceEntity>? = null

    @Relation(parentColumn = "id", entityColumn = "pId")
    var qualificationEntity:  List<QualificationEntity>? = null

    @Relation(parentColumn = "id", entityColumn = "pId")
    var referenceEntity:  List<ReferenceEntity>? = null

    @Relation(parentColumn = "id", entityColumn = "pId")
    var skillEntity:  List<SkillEntity>? = null

}