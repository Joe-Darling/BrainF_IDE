/**
 * Created by Joe on 1/24/2018.
 * This class is returned by an execution of one command. It contains updated data of the pointer and character data.
 */
public class ExecutionResult {
    private boolean codeBroken;
    private int pointer;
    private int charReader;
    private String message;

    public ExecutionResult(int pointer, int charReader, boolean codeBroken, String message){
        this.pointer = pointer;
        this.charReader = charReader;
        this.codeBroken = codeBroken;
        this.message = message;
    }

    public int getPointer(){
        return pointer;
    }

    public int getCharReader(){
        return charReader;
    }

    public boolean codeBroken(){
        return codeBroken;
    }

    public String getMessage(){
        return message;
    }
}
