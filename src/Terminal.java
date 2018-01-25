import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

/**
 * Created by Joe on 1/24/2018.
 */
public class Terminal extends TextArea {
    @Override
    public void replaceText(int start, int end, String text) {
        String current = getText();
        // only insert if no new lines after insert position:
        if (! current.substring(start).contains("\n")) {
            super.replaceText(start, end, text);
        }
    }
    @Override
    public void replaceSelection(String text) {
        String current = getText();
        int selectionStart = getSelection().getStart();
        if (! current.substring(selectionStart).contains("\n")) {
            super.replaceSelection(text);
        }
    }
}
