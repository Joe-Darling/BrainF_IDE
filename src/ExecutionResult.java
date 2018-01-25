/**
 * Created by Joe on 1/24/2018.
 * This class is returned by an execution of one command. It contains updated data of the pointer and character data.
 */
public class ExecutionResult {
    private boolean codeBroken;
    private int pointer;
    private int charReader;
    private String failureMessage;

    public ExecutionResult(int pointer, int charReader, boolean codeBroken, String failureMessage){
        this.pointer = pointer;
        this.charReader = charReader;
        this.codeBroken = codeBroken;
        this.failureMessage = failureMessage;
    }

    public int getPointer(){
        return pointer;
    }

    public int getCharReader(){
        return charReader;
    }

    public boolean isCodeBroken(){
        return codeBroken;
    }

    public String getFailureMessage(){
        return failureMessage;
    }
}
