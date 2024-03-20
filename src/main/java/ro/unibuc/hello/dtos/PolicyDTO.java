package ro.unibuc.hello.dtos;

import ro.unibuc.hello.entities.Policy;
import ro.unibuc.hello.entities.Statement;

import java.util.List;

public class PolicyDTO {
    private String id;
    private String name;
    private List<Statement> statements;

    public PolicyDTO() {}

    public PolicyDTO(String id, String name, List<Statement> statements){
        this.id = id;
        this.name = name;
        this.statements = statements;
    }

    public Policy toPolicy() {
        return new Policy(id, name, statements);
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
