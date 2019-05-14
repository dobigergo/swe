package topic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class TopicLoader {

    private List<Topic> topics;
    private static Logger logger = LoggerFactory.getLogger(TopicLoader.class);

    public TopicLoader() throws UnsupportedEncodingException {
        Type type = new TypeToken<List<Topic>>(){}.getType();
        Reader reader = new InputStreamReader(TopicLoader.class.getResourceAsStream("/names.json"), "UTF-8");
        Gson gson = new GsonBuilder().create();
        topics = gson.fromJson(reader,type);
        logger.info("A json betöltése sikeres volt");
    }

    public List<Topic> getTopics() {
        return topics;
    }

}
