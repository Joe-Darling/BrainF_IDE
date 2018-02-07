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

    // Fields
    private final String TITLE = " - BrainF IDE"; // the text for the display on top of the IDE window

    private Stage stage; // the main stage for the IDE
    private CodeArea editor; // the area in which the user codes in
    private Terminal terminal; // the textarea where the interpreter outputs information to
    private Boolean codeModified; // if editor has been modified
    private SavePrompt savePrompt; // the popup box that prompts the user to save when using a feature
    private File currentFile; // the current file the user is working in
    private Interpreter interpreter; // the interpreter for the language BrainF

    // Constructor
    public BrainFMenuBar(Stage primaryStage, CodeArea textEditor, Terminal terminal){
        stage = primaryStage;
        editor = textEditor;
        this.terminal = terminal;
        savePrompt = new SavePrompt(this, stage);
        codeModified = false;
    }

    // Methods

    public File getCurrentFile(){
        return currentFile;
    }

    public Interpreter getInterpreter(){
        return interpreter;
    }

    public void setCodeModified(Boolean bool){
        codeModified = bool;
    }

    /**
     * Sets the Title for the Scene on top of the window so the user knows which file
     * they are currently working in.
     */
    public void setMainSceneTitle(){
        stage.setTitle(getFileName() + TITLE);
    }

    /**
     * The main method for creating the menu bar for the IDE. It creates the MenuItems for
     * each tab and sets the OnAction events to occur when clicked.
     * @return the finalized menu bar
     */
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

    /**
     * runs a specific functionality for various MenuItems
     * @param type
     */
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

    /**
     * Used as an intermidiary between clicking on a tab and executing the proper functionality.
     * Checks to see if the user has modified the code before executing the desired action.
     * Prompts the user to save if they haven't.
     * @param type
     */
    public void menuTab(SavePrompt.MenuOptions type){
        if(!codeModified) {
            runFunctionality(type);
            return;
        }

        savePrompt.run(type);
    }

    /**
     * Creates a new file by wiping the text editor.
     */
    public void newFile(){
        editor.clear();
        currentFile = null;
        setMainSceneTitle();
        setCodeModified(false);
    }

    /**
     * Opens a file and appends the text inside of the text editor.
     */
    public void openFile(){

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        int i;
        final int EOF = -1;

        // checks to see if the file is null or not. if so, it will
        // append the code of editor.
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

        // sets the new current file
        currentFile = file;
        setMainSceneTitle();
        setCodeModified(false);
    }

    /**
     * The functionality for saving a file. If the file is null, it will prompt
     * the Save As method instead.
     * @param file
     */
    public void saveFile(File file){
        if(file == null){
            saveAsFile();
            return;
        }

        // writes the modified text to the current file.
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(editor.getText());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        setCodeModified(false);
    }

    /**
     * Saves the current text inside of the editor to a file.
     */
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

    /**
     * Interprets the code and outputs the results in the terminal window.
     */
    public void runFile(){
        terminal.clear();
        terminal.appendText(">>>\n");
        interpreter = new Interpreter(terminal, 1000, editor.getText());
        interpreter.run();
    }

    /**
     * Gets the filename for the currentFile. If the currentFile is null, it will output
     * as "Untitled".
     * @return
     */
    public String getFileName(){
        String fileName = "Untitled";
        if(currentFile != null){
            fileName = currentFile.getName();
        }

        return fileName;
    }
}
