package ro.unibuc.hello.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ro.unibuc.hello.dto.SubjectGradeDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CatalogEntity {

    @Id
    private String id;
    @DBRef
    private StudentEntity student;
    private List<SubjectGradeDto> grades;

    public CatalogEntity() {
        grades = new ArrayList<>();
    }

    /**
     * Method used to add grade to existing catalog
     * @param subjectGradeDto - SubjectGrade object that defines the grade added (teacher and grade value)
     */
    public void addGrade(SubjectGradeDto subjectGradeDto) {
        grades.add(subjectGradeDto);
    }
}
