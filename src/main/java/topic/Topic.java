package topic;

public class Topic {

    private String name;
    private String level;

    @Override
    public String toString() {
        return "" + name + " " + level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
