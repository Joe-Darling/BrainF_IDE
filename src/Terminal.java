import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

/**
 * Created by Joe on 1/24/2018.
 */
public class Terminal extends TextArea {

    enum Mode{EDIT, STATIC}

    private Mode currMode;
    private int charsPrintedThisLine; // The number of characters on the bottom most line of the terminal

    public Terminal(){
        currMode = Mode.STATIC;
    }

    public Mode getCurrMode(){
        return currMode;
    }

    public void setCurrMode(Mode newMode){
        currMode = newMode;
    }

    /**
     * This method overrides the replaceText method and only allows users to type text if they are on the bottom
     * most row or the terminal and the terminal is in edit more.
     * @param start Start index of replacement
     * @param end End index of replacement
     * @param text Text to place
     */
    @Override
    public void replaceText(int start, int end, String text) {
        String current = getText();
        // only insert if no new lines after insert position:
        if (! current.substring(start).contains("\n") && currMode == Mode.EDIT) {
            super.replaceText(start, end, text);
        }
    }

    /**
     * This method overrides the replaceSelection method and only allows users to paste text if they are on the bottom
     * most row or the terminal and the terminal is in edit more.
     * @param text the text to paste
     */
    @Override
    public void replaceSelection(String text) {
        String current = getText();
        int selectionStart = getSelection().getStart();
        if (! current.substring(selectionStart).contains("\n") && currMode == Mode.EDIT) {
            super.replaceSelection(text);
        }
    }

    /**
     * This method overrides the append text method and writes to the terminal. After writing it ensures that
     * the terminal is back in static mode.
     * @param text The text to add to the terminal
     */
    @Override
    public void appendText(String text){
        currMode = Mode.EDIT; // This line may be unnecessary
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
}
