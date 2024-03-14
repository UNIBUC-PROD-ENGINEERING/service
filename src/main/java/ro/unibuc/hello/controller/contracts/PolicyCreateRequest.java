package ro.unibuc.hello.controller.contracts;


import ro.unibuc.hello.dto.Statement;

import java.util.List;

public class PolicyCreateRequest  {
    private String name;
    private List<Statement> statements;

    public PolicyCreateRequest() {
    }

    public PolicyCreateRequest(String name, List<Statement> statements) {
        this.name = name;
        this.statements = statements;
    }

    public String name() {
        return name;
    }

    public List<Statement> statements() {
        return statements;
    }
}
