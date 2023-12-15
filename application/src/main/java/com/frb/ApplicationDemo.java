package com.frb;

public class ApplicationDemo {
    private String name;

    public ApplicationDemo(String name) {
        this.name = name;
    }

    public String getNameWithDesc() {
        var domain = new DomainDemo("domainFromApp");
        return """
                nome: %s, description: %s
                """.formatted(this.name, domain.getDescription());
    }

    public String getName() {
        return name;
    }
}
