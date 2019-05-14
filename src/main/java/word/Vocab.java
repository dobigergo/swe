package word;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

public class Vocab {

    private static Logger logger = LoggerFactory.getLogger(Vocab.class);
    /**
     * The list of the given {@link Word}
     */
    private List<Word> vocab;

    public Vocab(String filename) throws UnsupportedEncodingException {
        Type type = new TypeToken<List<Word>>(){}.getType();
        Reader reader = new InputStreamReader(Vocab.class.getResourceAsStream(filename), "UTF-8");
        Gson gson = new GsonBuilder().create();
        vocab = gson.fromJson(reader,type);
        logger.info("A json betöltése sikeres volt");
    }

    /**
     *
     * @return the list of the given {@link Word}
     */

    public  List<Word> getAll(){
        return vocab;
    }

    /**
     *
     * @return the number of the given {@link Word}
     */
    public int cnt(){
       return (int) getAll().stream().count();
    }

}
