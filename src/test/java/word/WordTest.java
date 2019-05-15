package word;


import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

public class WordTest {

    private  Word word;

    @BeforeEach
    public  void setWord(){
        word = new Word("eye-popping", Arrays.asList("szemet gyönyörködtető"),"eyepopping");
    }

    @AfterEach
    public void tearDown(){
        word = null;
    }

    @Test
    void addCharAt(){
        assertEquals("eyepeopping",word.addCharAt(4,'e'));
        assertEquals("eyepoppinge",word.addCharAt(10,'e'));
        assertEquals("aeyepopping",word.addCharAt(0,'a'));
        assertEquals("enyepopping",word.addCharAt(1,'n'));
    }

    @Test
    void removeCharAt() {
        assertEquals("eyeopping",word.removeCharAt(3));
        assertEquals("yepopping",word.removeCharAt(0));
        assertEquals("eyepoppin",word.removeCharAt(9));
    }

    @Test
    void errorCnt(){
        assertEquals(1,word.errorCnt(word.getAnswer().length()));
    }

    @Test
    void check() {
        assertEquals(1,word.checkPoint("eyepopping"));
        assertEquals(2,word.checkPoint("eye-popping"));
    }
}