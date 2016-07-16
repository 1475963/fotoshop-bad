package ui;

import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.io.File;
import editor.Command;
import task.Tasks;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import java.util.Arrays;
import internationalisation.Internationalisation;
import java.util.stream.*;
import javafx.scene.image.ImageView;
import image.ImageCache;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import javafx.geometry.Pos;

public class GUI implements UI {
    private static final int    WIDTH = 800;
    private static final int    HEIGHT = 600;
    private Stage   stage;
    private Pane    root;
    private Scene   mainScene;
    private Tasks   tasks;
    private Map<String, EventHandler<ActionEvent>>      buttonHandlers;
    private Map<String, ImageView>                      buttonViews;
    private List<ImageView>                             buttonImages;
    private List<EventHandler<ActionEvent>>             handlers;
    private ImageView                                   currentImage;
    private ListView<String>                            currentImageDetails;
    private ListView<ImageView>                         cacheContent;
    private List<String>            commandLine;
    private FileChooser             fileChooser;
    private TextInputDialog         putDialog;
    private ChoiceDialog<String>    getDialog;
    private Alert                   helpDialog;
    
    public GUI(Stage stage) {
        stage.setTitle("Fotoshop v1.0");
        this.buttonHandlers = new HashMap<>();
        this.buttonViews = new HashMap<>();
        this.handlers = new ArrayList<>();
        this.handlers.add(this::openHandler);
        this.handlers.add(this::saveHandler);
        this.handlers.add(this::lookHandler);
        this.handlers.add(this::monoHandler);
        this.handlers.add(this::rotHandler);
        this.handlers.add(this::helpHandler);
        this.handlers.add(this::quitHandler);
        this.handlers.add(this::scriptHandler);
        this.handlers.add(this::putHandler);
        this.handlers.add(this::getHandler);
        this.handlers.add(this::undoHandler);
        this.buttonImages = new ArrayList<>();
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/open.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/save.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/look.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/mono.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/rot.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/help.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/quit.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/script.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/put.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/get.png").toExternalForm())));
        this.buttonImages.add(new ImageView(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/undo.png").toExternalForm())));
        List<String> indexes = Arrays.asList(Internationalisation.getInstance().getResources().getString("defaultCommands").split(","));
        IntStream.range(0, indexes.size())
                 .forEach(i -> this.buttonHandlers.put(indexes.get(i), this.handlers.get(i)));
        IntStream.range(0, indexes.size())
                 .forEach(i -> this.buttonViews.put(indexes.get(i), this.buttonImages.get(i)));
        this.commandLine = new ArrayList<>();
        this.fileChooser = new FileChooser();
        this.putDialog = new TextInputDialog("toto");
        this.putDialog.setTitle("Put");
        this.putDialog.setHeaderText("Put dialog");
        this.putDialog.setContentText("Choose your version name: ");
        this.getDialog = new ChoiceDialog<>("a", "a");
        this.getDialog.setTitle("Get");
        this.getDialog.setHeaderText("Get dialog");
        this.getDialog.setContentText("Choose your version name: ");
        this.helpDialog = new Alert(AlertType.INFORMATION);
        this.helpDialog.setTitle("Help");
        this.helpDialog.setHeaderText(Internationalisation.getInstance().getResources().getString("helpMessage"));
        this.helpDialog.setContentText("");

        this.tasks = new Tasks();
        this.setStage(stage);
        
        this.setRoot(new BorderPane());
        this.setScene(new Scene(this.getRoot(), this.WIDTH, this.HEIGHT));
        this.getStage().setScene(this.getScene());
        
        this.buildCommandPane();
        this.buildFilterPane();
        this.buildImagePane();
        this.buildDetailPane();
        this.buildCachePane();
        this.getScene().getStylesheets().add(this.getClass().getResource("/ui/style/gui.css").toExternalForm());
        this.getStage().getIcons().add(new javafx.scene.image.Image(this.getClass().getResource("/ui/style/FSicon.png").toExternalForm()));
        this.getStage().show();
    }
    
    private void proxyCall(String index, Command command) {
        if (this.tasks.containsIndex(index)) {
            this.tasks.execute(index, command);
        }
    }
    
    private void buildCommand(String command, String... args) {
        this.commandLine.clear();
        this.commandLine.add(command);
        IntStream.range(0, args.length)
                 .forEach(i -> this.commandLine.add(args[i]));
    }
    
    private void updateImage() {
        if (ImageCache.getInstance().getCurrent() != null) {
            this.currentImage.setImage(SwingFXUtils.toFXImage(ImageCache.getInstance().getCurrent(), null));
            this.updateImageDetails();
        }
    }
    
    private void updateImageDetails() {
        this.currentImageDetails.getItems().clear();
        this.currentImageDetails.getItems().add("Cache index: " + ImageCache.getInstance().getIndex());
        IntStream.range(0, ImageCache.getInstance().getCurrent().sizeFilter())
                 .forEach(i -> this.currentImageDetails.getItems().add(ImageCache.getInstance().getCurrent().getFilter(i)));
    }
    
    private void updateCache() {
        this.cacheContent.getItems().clear();
        ImageCache.getInstance().lastVersions().stream().forEach(value -> populateCache(value));
        int i = 0;
        for (String key : ImageCache.getInstance().keys()) {
            if (key.equals(ImageCache.getInstance().getIndex()))
                break;
            i++;
        }
        this.cacheContent.getSelectionModel().select(i);
    }
    
    private void populateCache(image.Image image) {
        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(image, null));
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(this.cacheContent.widthProperty());
        imageView.fitHeightProperty().bind(this.cacheContent.heightProperty());
        this.cacheContent.getItems().add(imageView);
    }
    
    private void updates() {
        this.updateImage();
        this.updateCache();
    }
    
    private void addCommandButton(Pane panel, String command) {
        Button tmpBtn = new Button(command, this.buttonViews.get(command));
        
        tmpBtn.setOnAction(this.buttonHandlers.get(command));
        ((HBox) panel).setHgrow(tmpBtn, Priority.ALWAYS);
        tmpBtn.setMaxWidth(Double.MAX_VALUE);
        tmpBtn.setPrefHeight(this.getScene().getHeight() / 6);
        panel.getChildren().add(tmpBtn);
    }
    
    private void addFilterButton(Pane panel, String command) {
        Button tmpBtn = new Button(command, this.buttonViews.get(command));
        
        tmpBtn.setOnAction(this.buttonHandlers.get(command));
        ((VBox) panel).setVgrow(tmpBtn, Priority.ALWAYS);
        tmpBtn.setMaxHeight(Double.MAX_VALUE);
        tmpBtn.setPrefWidth(this.getScene().getWidth() / 4);
        panel.getChildren().add(tmpBtn);
    }
    
    /*
     * GUI builders
     */
    
    private void buildCommandPane() {
        HBox panel = new HBox();
        List<String> commands = this.tasks.getCommands();
        
        panel.setPrefHeight(this.getScene().getHeight() / 6);
        commands.stream().filter(command -> !Internationalisation.getInstance().getAdvancedCommands().contains(command))
                         .forEach(command -> this.addCommandButton(panel, command));
        panel.prefWidthProperty().bind(this.getRoot().prefWidthProperty());
        ((BorderPane) this.getRoot()).setTop(panel);
    }
    
    private void buildFilterPane() {
        VBox panel = new VBox();
        List<String> commands = this.tasks.getCommands();
        
        panel.setPrefWidth(this.getScene().getWidth() / 4);
        commands.stream().filter(command -> Internationalisation.getInstance().getAdvancedCommands().contains(command))
                         .forEach(command -> this.addFilterButton(panel, command));
        panel.prefHeightProperty().bind(this.getRoot().prefHeightProperty());
        ((BorderPane) this.getRoot()).setLeft(panel);
    }
    
    private void buildImagePane() {
        VBox panel = new VBox();
        this.currentImage = new ImageView();
        
        this.currentImage.setPreserveRatio(true);
        this.currentImage.fitWidthProperty().bind(panel.widthProperty());
        this.currentImage.fitHeightProperty().bind(panel.heightProperty());
        panel.setAlignment(Pos.CENTER);
        panel.getChildren().add(this.currentImage);
        panel.setMinWidth(this.getScene().getWidth() / 4);
        panel.setMinHeight(this.getScene().getHeight() / 6);
        panel.setPrefWidth(this.getScene().getWidth() / 2);
        panel.setPrefHeight(this.getScene().getHeight() / 2);
        ((BorderPane) this.getRoot()).setCenter(panel);
        ((BorderPane) this.getRoot()).getCenter().getStyleClass().add("border-pane-center");
    }
    
    private void buildDetailPane() {
        this.currentImageDetails = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        this.currentImageDetails.setItems(items);
        VBox panel = new VBox();
        panel.setVgrow(this.currentImageDetails, Priority.ALWAYS);
        this.currentImageDetails.setMaxHeight(Double.MAX_VALUE);
        this.currentImageDetails.setPrefWidth(this.getScene().getWidth() / 4);
        panel.getChildren().add(this.currentImageDetails);
        panel.prefHeightProperty().bind(this.getRoot().prefHeightProperty());
        ((BorderPane) this.getRoot()).setRight(panel);        
    }
    
    private void buildCachePane() {
        this.cacheContent = new ListView<>();
        this.cacheContent.setOrientation(Orientation.HORIZONTAL);
        ObservableList<ImageView> items = FXCollections.observableArrayList();
        this.cacheContent.setItems(items);
        HBox panel = new HBox();
        panel.prefWidthProperty().bind(this.getRoot().prefWidthProperty());
        panel.setHgrow(this.cacheContent, Priority.ALWAYS);
        this.cacheContent.setMaxWidth(Double.MAX_VALUE);
        this.cacheContent.setPrefHeight((2 * this.getScene().getHeight()) / 6);
        panel.getChildren().add(this.cacheContent);
        ((BorderPane) this.getRoot()).setBottom(panel);
    }
    
    /*
     * Event handlers
     */

    private void openHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        File file = this.fileChooser.showOpenDialog(this.getStage());
        
        if (file != null)
            this.buildCommand(index, file.getAbsolutePath());
        else
            this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void saveHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        File file = this.fileChooser.showSaveDialog(this.getStage());
        
        if (file != null)
            this.buildCommand(index, file.getAbsolutePath());
        else
            this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void lookHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        
        this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void monoHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        
        this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void rotHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        
        this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void helpHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        
        this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void quitHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        
        this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void scriptHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        File file = this.fileChooser.showOpenDialog(this.getStage());
        
        if (file != null)
            this.buildCommand(index, file.getName());
        else
            this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void putHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        
        Optional<String> result = this.putDialog.showAndWait();
        if (result.isPresent())
            this.buildCommand(index, result.get());
        else
            this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void getHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        
        this.getDialog.getItems().clear();
        this.getDialog.getItems().addAll(ImageCache.getInstance().keys());
        Optional<String> result = this.getDialog.showAndWait();
        if (result.isPresent())
            this.buildCommand(index, result.get());
        else
            this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    private void undoHandler(ActionEvent event) {
        String index = ((Button) event.getSource()).getText();
        
        this.buildCommand(index);
        this.proxyCall(index, new Command(this.commandLine));
    }
    
    /**
     * UI implementations
     */
    
    public void welcome() {
    }
    
    public void bye() {
    }
    
    public void unknown() {
    }
    
    public void imageException(String name) {
    }
    
    public void argsError(String msg) {
    }
    
    public void currentImageError(Command command) {
    }
    
    public void saveException(String msg) {
    }
    
    public void scriptException(String name) {
    }
    
    public void open(String name) {
        this.updates();
    }
    
    public void save(String name) {
        this.updates();
    }
    
    public void look(Command command) {
    }
    
    public void mono() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), this.currentImage);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
        
        FadeTransition resetTransition = new FadeTransition(Duration.millis(1000), this.currentImage);
        resetTransition.setFromValue(0.1);
        resetTransition.setToValue(1.0);
        resetTransition.setCycleCount(1);
        
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                updates();
                resetTransition.play();
            }
        });
    }
    
    public void rot() {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(2000), this.currentImage);
        rotateTransition.setByAngle(90f);
        rotateTransition.setCycleCount(1);
        rotateTransition.play();
        rotateTransition.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                currentImage.setRotate(0);
                updates();
            }
        });
    }
    
    public void help() {
        this.helpDialog.showAndWait();
    }
    
    public void quit() {
        Platform.exit();
    }
    
    public void script() {
        this.updates();
    }
    
    public void put() {
        this.updates();
    }
    
    public void get() {
        this.updates();
    }
    
    public void undo() {
        this.updates();
    }
    
    /**
     * Accessors and mutators
     */
    
    /**
     * 
     * @param stage 
     */
    private void setStage(Stage stage) {
        this.stage = stage;
    }
    
    private void setRoot(Pane panel) {
        this.root = panel;
    }
    
    private void setScene(Scene scene) {
        this.mainScene = scene;
    }
    
    private Stage getStage() {
        return (this.stage);
    }
    
    private Pane getRoot() {
        return (this.root);
    }
    
    private Scene getScene() {
        return (this.mainScene);
    }
}
