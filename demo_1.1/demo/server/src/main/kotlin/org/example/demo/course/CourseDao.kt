package org.example.demo.course

import org.example.demo.student.StudentEntity
import org.example.demo.student.StudentTable
import org.example.demo.student.studentDao
import org.example.demo.utils.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert

class CourseDaoImpl {
    suspend fun createCourse(name: String, description: String, creator: String): Int = dbQuery {
        val course = CourseEntity.new {
            this.name = name
            this.description = description
            this.creator = creator
        }
        return@dbQuery course.id.value
    }
    suspend fun getCoursesOfStudent(student: String, from: Int, num: Int) = dbQuery {
        StudentEntity.find{ StudentTable.name eq student }
            .firstOrNull()!!
            .courses
            .reversed()
            .drop(from)
            .take(num)
            .map(CourseEntity::toModel)
    }
    suspend fun getCoursesOfTeacher(teacher: String, from: Int, num: Int) = dbQuery {
        CourseEntity.find { CourseTable.creator eq teacher }
            .reversed()
            .drop(from)
            .take(num)
            .map(CourseEntity::toModel)
    }
    suspend fun getStudentsOfCourse(course: Int, from: Int, num: Int) = dbQuery {
        CourseEntity.findById(course)!!.students
            .drop(from)
            .take(num)
            .map(StudentEntity::toModel)
    }
    suspend fun joinCourse(student: String, course: Int) = dbQuery {
        StudentCourseTable.insert {
            it[this.student] = StudentEntity.find { StudentTable.name eq student }.first().id.value
            it[this.course] = course
        }
    }
}

val courseDao = CourseDaoImpl()