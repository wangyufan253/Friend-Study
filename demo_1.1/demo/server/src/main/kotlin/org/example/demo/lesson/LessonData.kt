package org.example.demo.lesson

import kotlinx.serialization.Serializable
import org.example.demo.course.Course
import org.example.demo.course.CourseTable
import org.example.demo.course.StudentCourseTable
import org.example.demo.student.StudentEntity
import org.example.demo.student.StudentTable
import org.example.demo.teacher.TeacherTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

@Serializable
data class Lesson(
    val id : Int,
    val name: String,
    val description: String,
    val creator: String
)

object LessonTable : IntIdTable() {
    val name = varchar("name", 64)
    val description = varchar("description", 256)
    val creator = reference("creator", TeacherTable.name, onDelete = ReferenceOption.CASCADE).index()
}

class LessonEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LessonEntity>(LessonTable)
    var name by LessonTable.name
    var description by LessonTable.description
    var creator by LessonTable.creator
    var students by StudentEntity via StudentLessonTable
    fun toModel() = Course(
        id.value, name, description, creator
    )
}

object StudentLessonTable : Table() {
    val student = reference("student", StudentTable, onDelete = ReferenceOption.CASCADE)
    val lesson = reference("lesson", LessonTable, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(student, lesson)
}