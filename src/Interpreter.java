import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joe on 1/24/2018.
 */
public class Interpreter {

    private final String OKAY_STATUS = "OK";
    private final String PROMPT_INPUT = "IN";
    private final String INDEX_OUT_OF_BOUNDS = "You indexed out of bounds.";
    private final String VALUE_CAP_EXCEEDED = "You have exceeded the value cap for a cell. You can change this in the" +
            "settings";

    private Terminal terminal;
    private int valueCap;

    private int pointer;
    private int charReader;
    private char currCommand;
    List<Integer> loops;
    List<Character> array = new ArrayList<>(Collections.singletonList((char)0));
    String code;

    public Interpreter(Terminal terminal, int valueCap, String code){
        this.terminal = terminal;
        this.valueCap = valueCap;

        pointer = 0;
        charReader = 0;
        loops = new ArrayList<>();

        // We run the code through the parser to strip all non legal characters first
        this.code = parser(code);
    }

    public ExecutionResult run(){
        while(charReader < code.length()){
            currCommand = code.charAt(charReader);
            ExecutionResult result = executeCommand(currCommand);
            if(result.codeBroken() || result.getMessage().equals(PROMPT_INPUT)) {
                return result;
            }
            charReader++;
        }
        return new ExecutionResult(pointer, charReader, false, OKAY_STATUS);
    }

    public void getInput(){
        char c = terminal.getText().charAt(terminal.getText().length() - 1);
        array.set(pointer, c);
        charReader++;
        run();
    }

    private ExecutionResult executeCommand(char command){
        boolean codeBroken = false;
        String message = "";

        switch(command){
            case '<':
                pointer -= 1;
                if(pointer < 0){
                    codeBroken = true;
                    message = INDEX_OUT_OF_BOUNDS;
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
                    message = VALUE_CAP_EXCEEDED;
                }
                break;
            case '-':
                array.set(pointer, (char)(array.get(pointer) - 1));
                if(array.get(pointer) < -valueCap){
                    codeBroken = true;
                    message = VALUE_CAP_EXCEEDED;
                }
                break;
            case '.':
                terminal.appendText(array.get(pointer).toString());
                break;
            case ',':
                message = PROMPT_INPUT;
                break;
            case '[':
                if(array.get(pointer) != 0){
                    loops.add(charReader);
                }
                else{
                    while(code.charAt(charReader) == ']'){
                        charReader++;
                    }
                }
                break;
            case ']':
                if(array.get(pointer) != 0){
                    charReader = loops.get(loops.size() - 1);
                }
                else{
                    loops.remove(loops.get(loops.size()-1));
                }
                break;
        }

        return new ExecutionResult(pointer, charReader, codeBroken, message);
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
            if(chars.contains(c)){
                result += c;
            }
        }

        return result;
    }
}
