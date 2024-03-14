package ro.unibuc.hello.data;
import org.springframework.data.annotation.Id;
import ro.unibuc.hello.dto.Statement;

import java.util.Arrays;
import java.util.List;

public class PolicyEntity {
    @Id
    public String policyId;
    public String policyName;
    public List<Statement> statements;

    public PolicyEntity() {}

    public PolicyEntity(String id, String name, List<Statement> statements){
        this.policyId = id;
        this.policyName = name;
        this.statements = statements;
    }

    @Override
    public String toString() {
        return String.format(
            "Policy[id=%s, name='%s', statements='%s']",
            policyId, policyName,
                statements.stream()
                        .map(Statement::toString)
                        .reduce("", (a, b) -> a + ", " + b));
    }
}
