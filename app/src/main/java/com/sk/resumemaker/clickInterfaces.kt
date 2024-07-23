package com.sk.resumemaker

import com.sk.resumemaker.entities.*

interface QualificationClicked{
    fun OnEditQualificationClicked(qualificationEntity: QualificationEntity, position:Int)
    fun OnDeleteQualificationClicked(qualificationEntity: QualificationEntity, position:Int)
}
interface CurricularClicked{
    fun OnEditCurricularClicked(curricularEntity: CurricularEntity, position:Int)
    fun OnDeleteCurricularClicked(curricularEntity: CurricularEntity, position:Int)
}
interface ExperienceClicked{
    fun OnEditExperienceClicked(experienceEntity: ExperienceEntity, position : Int)
    fun OnDeleteExperienceClicked(experienceEntity: ExperienceEntity, position : Int)
}
interface ReferenceClicked{
    fun OnEditReferenceClicked(referenceEntity: ReferenceEntity, position : Int)
    fun OnDeleteReferenceClicked(referenceEntity: ReferenceEntity, position : Int)
}
interface SkillClicked{
    fun OnEditSkillClicked(skillEntity: SkillEntity, position : Int)
    fun OnDeleteSkillClicked(skillEntity: SkillEntity, position : Int)
}
interface ResumeListClicked{
    fun OnViewClicked(resumes: DisplayResumes, position : Int)
    fun OnPrintClicked(resumes: DisplayResumes, position : Int)
    fun OnDeleteClicked(resumes: DisplayResumes, position : Int)
}
