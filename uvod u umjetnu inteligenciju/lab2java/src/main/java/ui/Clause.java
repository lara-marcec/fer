package ui;

import java.util.List;
import java.util.Objects;

public class Clause
{
    private int index;
    private List<Literal> literalList;

    public Clause(int index, List<Literal> literalList) {
        this.index = index;
        this.literalList = literalList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(index).append(". ");

        for(Literal lit : this.literalList)
        {
            sb = lit.getValue() ? sb.append(lit.getName()) : sb.append("~").append(lit.getName());
            sb.append(" v ");
        }
        if(sb.length() >= 3)
        {
            sb.delete(sb.length()-3, sb.length());
        }


        return sb.toString();
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public List<Literal> getliteralList()
    {
        return literalList;
    }

    public void setliteralList(List<Literal> literalList)
    {
        this.literalList = literalList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clause clause = (Clause) o;
        return Objects.equals(literalList, clause.literalList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(literalList);
    }
}
