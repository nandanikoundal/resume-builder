package com.sk.resumemaker.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sk.resumemaker.entities.*

@Database(entities =[QualificationEntity::class, ExperienceEntity::class, CurricularEntity::class, PersonalInfoEntity::class,SkillEntity::class,ReferenceEntity::class], version = 1)
abstract class ResumeDatabase:RoomDatabase() {
    abstract fun resumeDao(): ResumeDao

    companion object {
        var resumeDatabase: ResumeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): ResumeDatabase {
            if (resumeDatabase == null) {
                resumeDatabase = Room.databaseBuilder(
                    context,
                    ResumeDatabase::class.java, "resume"
                ).build()
            }
            return resumeDatabase!!
        }
    }
}