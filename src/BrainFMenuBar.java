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

import java.io.*;

public class BrainFMenuBar {

    private Stage stage;
    private CodeArea editor;
    private Terminal terminal;
    private Boolean codeModified; // if editor has been modified

    public BrainFMenuBar(Stage primaryStage, CodeArea textEditor, Terminal terminal){
        stage = primaryStage;
        editor = textEditor;
        this.terminal = terminal;
    }

    public Boolean getCodeModified(){
        return codeModified;
    }

    public void setCodeModified(Boolean bool){
        codeModified = bool;
    }

    public MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();

        // FILE
        Menu file = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(event -> newFile());
        MenuItem open = new MenuItem("Open");
        open.setOnAction(event -> openFile());
        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> saveFile());
        file.getItems().addAll(newFile, open, save);

        // EDIT
        Menu edit = new Menu("Edit");

        // RUN
        Menu run = new Menu("Run");
        MenuItem runProgram = new MenuItem("Run");
        runProgram.setOnAction(event -> runFile());
        MenuItem debug = new MenuItem("Debug");
        run.getItems().addAll(runProgram, debug);

        // Add MenuBar Content to the MenuBar
        menuBar.getMenus().addAll(file, edit, run);
        return menuBar;
    }

    // Change name later
    private void codeCheck(){
        // check codeModified
            // if text is modified, ask if they would like to save
            //
    }

    private void newFile(){
        //codeCheck()
        editor.clear();
    }

    private void openFile(){
        //codeCheck()

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        int i;
        final int EOF = -1;

        if(file != null){
            // get file type here.
            try {
                InputStream iStream = new FileInputStream(file);
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

    private void saveFile(){
        // if file is untitled, then save the file

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BrainF (*.bs)", "*.bs");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if(file != null){
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(editor.getText());
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void runFile(){
        terminal.clear();
        terminal.appendText(">>>\n");
        Interpreter interpreter = new Interpreter(terminal, Integer.MAX_VALUE, editor.getText());
        interpreter.run();
    }
}
