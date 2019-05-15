package topic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Topic {

    private String name;
    private String level;
    private String path;

    @Override
    public String toString() {
        return "" + name + " " + level;
    }


}
