import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joe on 1/24/2018.
 */
public class Interpreter {

    private final String INDEX_OUT_OF_BOUNDS = "You indexed out of bounds.";
    private final String VALUE_CAP_EXCEEDED = "You have exceeded the value cap for a cell. You can change this in the" +
            "settings";

    private TextArea output;
    private int valueCap;

    public Interpreter(TextArea output, int valueCap){
        this.output = output;
        this.valueCap = valueCap;
    }

    public int interpret(String code){
        // First we strip all non-legal characters.
        code = parser(code);

        List<Integer> loops = new ArrayList<>();
        List<Character> array = new ArrayList<>(Collections.singletonList((char) 0));
        int pointer = 0;
        int charReader = 0;
        char c;

        while(charReader < code.length()){
            c = code.charAt(charReader);

        }
        return 0;
    }


    private ExecutionResult executeCommand(char command, List<Character> array, int pointer, List<Integer> loops,
                                           String code, int charPointer){
        boolean codeBroken = false;
        String failMessage = "";

        switch(command){
            case '<':
                pointer -= 1;
                if(pointer < 0){
                    codeBroken = true;
                    failMessage = INDEX_OUT_OF_BOUNDS;
                }
                break;
            case '>':
                pointer += 1;
                if(array.size() - 1 < pointer){
                    array.add((char)0);
                }
                break;
            case '+':
                array.set(pointer, (char)(array.get(pointer) + 1));
                if(array.get(pointer) > valueCap){
                    codeBroken = true;
                    failMessage = VALUE_CAP_EXCEEDED;
                }
                break;
            case '-':
                array.set(pointer, (char)(array.get(pointer) + 1));
                if(array.get(pointer) < -valueCap){
                    codeBroken = true;
                    failMessage = VALUE_CAP_EXCEEDED;
                }
                break;
            case '.':
                output.appendText(array.get(pointer).toString());
                break;
            case ',':
                // TODO: Implement me lul
                break;
            case '[':
                if(array.get(pointer) != 0){
                    loops.add(charPointer);
                }
                else{
                    while(code.charAt(charPointer) == ']'){
                        charPointer++;
                    }
                }
                break;
            case ']':
                if(array.get(pointer) != 0){
                    charPointer = loops.get(loops.size() - 1);
                }
                else{
                    loops.remove(loops.get(loops.size()-1));
                }
                break;
        }

        return new ExecutionResult(pointer, charPointer, codeBroken, failMessage);
    }

    /**
     * This method parses the code and removes all characters that aren't recognized.
     *
     * @param code The un-modified code
     * @return The parsed resulting code
     */
    private String parser(String code){
        ArrayList<Character> chars = new ArrayList<Character>(Arrays.asList('<', '>', '+', '-', '.', ',', '[', ']'));
        String result = "";
        for(char c : code.toCharArray()){
            if(!chars.contains(c)){
                result += c;
            }
        }

        return result;
    }

}
