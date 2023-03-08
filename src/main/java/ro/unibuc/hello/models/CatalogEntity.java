package ro.unibuc.hello.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.dto.SubjectGrade;

import java.util.ArrayList;
import java.util.List;

public class CatalogEntity {

    @Id
    public String id;
    @DBRef
    public StudentEntity student;
    public List<SubjectGrade> grades;

    public CatalogEntity() {
        grades = new ArrayList<>();
    }

    /**
     * Method used to add grade to existing catalog
     * @param subjectGrade - SubjectGrade object that defines the grade added (teacher and grade value)
     */
    public void addGrade(SubjectGrade subjectGrade) {
        grades.add(subjectGrade);
    }
}
