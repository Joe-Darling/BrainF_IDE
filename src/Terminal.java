import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

/**
 * Created by Joe on 1/24/2018.
 */
public class Terminal extends TextArea {

    enum Mode{EDIT, STATIC}

    private Mode currMode;
    private int charsPrintedThisLine;

    public Terminal(){
        currMode = Mode.STATIC;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        String current = getText();
        // only insert if no new lines after insert position:
        if (! current.substring(start).contains("\n") && currMode == Mode.EDIT) {
            super.replaceText(start, end, text);
        }
    }
    @Override
    public void replaceSelection(String text) {
        String current = getText();
        int selectionStart = getSelection().getStart();
        if (! current.substring(selectionStart).contains("\n") && currMode == Mode.EDIT) {
            super.replaceSelection(text);
        }
    }

    @Override
    public void appendText(String text){
        currMode = Mode.EDIT;
        for(char c : text.toCharArray()){
            super.appendText(String.valueOf(c));
            charsPrintedThisLine++;
            if(charsPrintedThisLine > 35){
                super.appendText("\n");
                charsPrintedThisLine = 0;
            }
        }

        currMode = Mode.STATIC;
    }

    public Mode getCurrMode(){
        return currMode;
    }

    public void setCurrMode(Mode newMode){
        currMode = newMode;
    }
}
