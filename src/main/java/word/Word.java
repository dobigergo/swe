package word;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Word {

    /**
     * the meaning of the hingarian words in english
     */
    private String eng;
    /**
     * the meanings of the english word in hungarian
     */
    private List<String> hun;
    /**
     * the given answer to the hungarian words meanings in english
     */
    private String answer;

    public Word(String eng, List<String> hun) {
        this.eng = eng;
        this.hun = hun;
    }

    /**
     *
     * @param charIndexToRemove the index of the char in the {@code answer} which you want to remove
     * @return returns the {@code answer} String without the char at {@code charIndexToRemove}
     */
    public String removeCharAt(int charIndexToRemove ){
        return answer.substring(0,charIndexToRemove)+answer.substring(charIndexToRemove+1);
    }

    /**
     *
     * @param indexToAddChar the index of the char in the {@code eng} String which you want to add to answer
     * @param charToAdd the char in {@code eng} at {@code indexToAddChar} to add to answer
     * @return answer with the specified char added
     */
    public String addCharAt(int indexToAddChar,char charToAdd){
        return answer.substring(0,indexToAddChar)+charToAdd+answer.substring(indexToAddChar);
    }

    /**
     *
     * @param answer the answer to the meaning of the {@code hun} word in english
     * @return the number of points depending on the similarity of the answer and the original english word
     */
    public int checkPoint(String answer){
        this.answer = answer;
        int min = Math.min(eng.length(),answer.length());
        if(eng.equals(answer))
            return 2;
        else if(errorCnt(min) < 3)
            return 1;
        return 0;
    }

    /**
     *
     * @param min the minimum of the lengths of the (@code answer) String and the (@code eng) String
     * @return the number of difference between the (@code) and the (@answer)
     */

    public int errorCnt(int min){
        int errorCnt = 0;
        char[] answerChars=answer.toCharArray();
        char[] engWordChars=eng.toCharArray();
        for(int charIndex = 0; charIndex<min ; charIndex++){
            if(errorCnt==3)
                return 3+Math.abs(answerChars.length-engWordChars.length);
            if(answerChars[charIndex] != engWordChars[charIndex]) {
                errorCnt++;
                if (answerChars.length < engWordChars.length) {
                    answerChars = addCharAt(charIndex, engWordChars[charIndex]).toCharArray();
                    charIndex--;
                    min=answerChars.length;
                } else if (answerChars.length > engWordChars.length) {
                    answerChars = removeCharAt(charIndex).toCharArray();
                    charIndex--;
                }
            }
            }
        return errorCnt+Math.abs(answerChars.length-engWordChars.length);
    }


}
