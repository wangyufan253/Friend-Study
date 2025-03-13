package org.example.demo.lesson

import org.example.demo.student.StudentEntity
import org.example.demo.student.StudentTable
import org.example.demo.utils.dbQuery
import org.jetbrains.exposed.sql.insert

class LessonDaoImpl {
    suspend fun createLesson(name: String, description: String, creator: String): Int = dbQuery {
        val lesson = LessonEntity.new {
            this.name = name
            this.description = description
            this.creator = creator
        }
        return@dbQuery lesson.id.value
    }
    suspend fun getLessonsOfStudent(student: String, from: Int, num: Int) = dbQuery {
        StudentEntity.find { StudentTable.name eq student }
            .firstOrNull()!!
            .lessons
            .reversed()
            .drop(from)
            .take(num)
            .map(LessonEntity::toModel)
    }
    suspend fun getLessonsOfTeacher(teacher: String, from: Int, num: Int) = dbQuery {
        LessonEntity.find { LessonTable.creator eq teacher }
            .reversed()
            .drop(from)
            .take(num)
            .map(LessonEntity::toModel)
    }
    suspend fun getStudentsOfLesson(lesson: Int, from: Int, num: Int) = dbQuery {
        LessonEntity.findById(lesson)!!.students
            .drop(from)
            .take(num)
            .map(StudentEntity::toModel)
    }
    suspend fun joinLesson(student: String, lesson: Int) = dbQuery {
        StudentLessonTable.insert {
            it[this.student] = StudentEntity.find { StudentTable.name eq student }.first().id.value
            it[this.lesson] = lesson
        }
    }
}

val lessonDao = LessonDaoImpl()