package ui;

import java.util.Objects;

public class Literal {
    private boolean value;
    private String name;
    Literal(boolean value, String name)
    {
        this.value = value;
        this.name = name;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Literal getNegate() {
        return new Literal(!this.value, this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Literal literal = (Literal) o;
        return value == literal.value && Objects.equals(name, literal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }

    @Override
    public String toString() {

        return (value ? "" : "~") + name;
    }
}
