package com.daemawiki.internal.core.domain.model.value.user;

import com.daemawiki.internal.core.domain.model.primitive.user.student.AcademicYear;
import com.daemawiki.internal.core.domain.model.primitive.user.student.ClassNumber;
import com.daemawiki.internal.core.domain.model.primitive.user.student.StudentGrade;
import com.daemawiki.internal.core.domain.model.primitive.user.student.StudentNumber;
import com.daemawiki.internal.core.domain.model.value.ValueObject;

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
