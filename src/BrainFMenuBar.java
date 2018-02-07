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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.*;

public class BrainFMenuBar {

    private final String TITLE = " - BrainF IDE";

    private Stage stage;
    private CodeArea editor;
    private Terminal terminal;
    private Boolean codeModified; // if editor has been modified
    private SavePrompt savePrompt;
    private File currentFile;
    private Interpreter interpreter;

    public BrainFMenuBar(Stage primaryStage, CodeArea textEditor, Terminal terminal){
        stage = primaryStage;
        editor = textEditor;
        this.terminal = terminal;
        savePrompt = new SavePrompt(this, stage);
        codeModified = false;
    }

    public File getCurrentFile(){
        return currentFile;
    }

    public void setCodeModified(Boolean bool){
        codeModified = bool;
    }

    public void setMainSceneTitle(){
        stage.setTitle(getFileName() + TITLE);
    }

    public Interpreter getInterpreter(){
        return interpreter;
    }

    public MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();

        // FILE
        Menu file = new Menu("File");
        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(event -> menuTab(SavePrompt.MenuOptions.FILE));
        MenuItem open = new MenuItem("Open");
        open.setOnAction(event -> menuTab(SavePrompt.MenuOptions.OPEN));
        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> saveFile(currentFile));
        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction(event -> saveAsFile());
        file.getItems().addAll(newFile, open, save, saveAs);

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

    public void runFunctionality(SavePrompt.MenuOptions type){
        switch (type) {
            case FILE:
                newFile();
                break;
            case OPEN:
                openFile();
                break;
            default:
                return;
        }
    }

    public void menuTab(SavePrompt.MenuOptions type){
        if(!codeModified) {
            runFunctionality(type);
            return;
        }

        savePrompt.run(type);
    }

    public void newFile(){
        editor.clear();
        currentFile = null;
        setMainSceneTitle();
        setCodeModified(false);
    }

    public void openFile(){

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

        currentFile = file;
        setMainSceneTitle();
        setCodeModified(false);
    }

    public void saveFile(File file){
        if(file == null){
            saveAsFile();
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(editor.getText());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        setCodeModified(false);
    }

    public void saveAsFile(){
        // if file is untitled, then save the file

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BrainF (*.bs)", "*.bs");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(stage);

        if(file != null){
            saveFile(file);
        }

        currentFile = file;
        setMainSceneTitle();
    }

    public void runFile(){
        terminal.clear();
        terminal.appendText(">>>\n");
        interpreter = new Interpreter(terminal, 1000, editor.getText());
        interpreter.run();
    }

    public String getFileName(){
        String fileName = "Untitled";
        if(currentFile != null){
            fileName = currentFile.getName();
        }

        return fileName;
    }
}
