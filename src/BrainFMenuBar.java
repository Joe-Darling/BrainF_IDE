import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BrainFMenuBar {

    private Stage stage;
    private CodeArea editor;
    private TextArea terminal;

    public BrainFMenuBar(Stage primaryStage, CodeArea textEditor, TextArea terminal){
        stage = primaryStage;
        editor = textEditor;
        this.terminal = terminal;
    }

    public MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();

        // Create MenuBar Content
        Menu file = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        open.setOnAction(event -> openFile());
        MenuItem save = new MenuItem("Save");
        file.getItems().addAll(newFile, open, save);

        Menu edit = new Menu("Edit");
        // Edit Menu Items

        Menu run = new Menu("Run");
        MenuItem runProgram = new MenuItem("Run");
        MenuItem debug = new MenuItem("Debug");
        run.getItems().addAll(runProgram, debug);

        // Add MenuBar Content to the MenuBar
        menuBar.getMenus().addAll(file, edit, run);
        return menuBar;
    }

    private void openFile(){
        // Check if save is needed first here

        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showOpenDialog(stage);
        int i;
        final int EOF = -1;

        if(f != null){
            // get file type here.
            try {
                InputStream iStream = new FileInputStream(f);
                String text = "";
                while((i = iStream.read()) != EOF){
                    char c = (char) i;
                    text += String.valueOf(c);
                }
                editor.clear();
                editor.appendText(text);

            }catch(IOException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
