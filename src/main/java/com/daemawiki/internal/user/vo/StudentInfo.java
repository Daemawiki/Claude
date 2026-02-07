package com.daemawiki.internal.user.vo;

import com.daemawiki.internal.user.primitive.student.AcademicYear;
import com.daemawiki.internal.user.primitive.student.ClassNumber;
import com.daemawiki.internal.user.primitive.student.StudentGrade;
import com.daemawiki.internal.user.primitive.student.StudentNumber;
import com.daemawiki.internal.common.value.ValueObject;

public record StudentInfo(
        AcademicYear academicYear,
        StudentGrade studentGrade,
        ClassNumber classNumber,
        StudentNumber studentNumber
) implements ValueObject {

    public static StudentInfo create(
            final AcademicYear academicYear,
            final StudentGrade studentGrade,
            final ClassNumber classNumber,
            final StudentNumber studentNumber
    ) {
        return new StudentInfo(academicYear, studentGrade, classNumber, studentNumber);
    }

}
