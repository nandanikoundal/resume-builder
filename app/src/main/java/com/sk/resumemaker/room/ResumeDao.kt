package com.sk.resumemaker.room

import androidx.room.*
import com.sk.resumemaker.entities.*
import kotlinx.coroutines.selects.select


@Dao
interface ResumeDao {
    @Insert
    fun insertPersonalEntity(personalInfoEntity: PersonalInfoEntity): Long
    @Insert
    fun insertqualification(qualificationEntity: QualificationEntity)
    @Insert
    fun insertexperience(experienceEntity: ExperienceEntity)
    @Insert
    fun insertskills(skillEntity: SkillEntity)
    @Insert
    fun insertcurricular(curricularEntity: CurricularEntity)
    @Insert
    fun insertreference( referenceEntity: ReferenceEntity)

    @Delete
    fun deletePersonalEntity( personalInfoEntity: PersonalInfoEntity)
    @Delete
    fun deletequalification( qualificationEntity: QualificationEntity)
    @Delete
    fun deleteexperience( experienceEntity: ExperienceEntity)
    @Delete
    fun deleteskills( skillEntity: SkillEntity)
    @Delete
    fun deletecurricular(  curricularEntity: CurricularEntity)
    @Delete
    fun deletereference( referenceEntity: ReferenceEntity)

    @Update
    fun updatePersonalEntity(personalInfoEntity: PersonalInfoEntity)
    @Update
    fun updateQualification(qualificationEntity: QualificationEntity)
    @Update
    fun updateexperience(experienceEntity: ExperienceEntity)
    @Update
    fun updateskills(skillEntity: SkillEntity)
    @Update
    fun updatecurricular(curricularEntity: CurricularEntity)
    @Update
    fun updatereference( referenceEntity: ReferenceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllQualifications(qualificationEntity: List<QualificationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllCurricular(curricularEntity: List<CurricularEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllExperience(experienceEntity: List<ExperienceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAllReferences(referenceEntity: List<ReferenceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
     fun insertAllSkills(skillEntity: List<SkillEntity>)

    @Query("SELECT * FROM PersonalInfoEntity")
    fun getResumeList(): List<DisplayResumes>

    @Delete
    fun deleteResume(vararg personalInfoEntity: PersonalInfoEntity)



}