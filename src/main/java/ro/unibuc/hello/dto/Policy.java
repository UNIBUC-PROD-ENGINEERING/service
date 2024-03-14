package ro.unibuc.hello.dto;

import java.util.List;

public class Policy {
    private String id;
    private String name;
    private List<Statement> statements;

    public Policy() {}

    public Policy(String id, String name, List<Statement> statements){
        this.id = id;
        this.name = name;
        this.statements = statements;
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
