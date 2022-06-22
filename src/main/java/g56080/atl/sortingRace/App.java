package g56080.atl.sortingRace;

import java.io.IOException;

import g56080.atl.sortingRace.config.ConfigManager;
import g56080.atl.sortingRace.controller.Controller;
import g56080.atl.sortingRace.jdbc.DBManager;
import g56080.atl.sortingRace.model.AppModel;
import g56080.atl.sortingRace.model.Model;
import g56080.atl.sortingRace.model.Util;
import g56080.atl.sortingRace.view.FxmlController;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

public class App extends Application{
    
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void init(){
        ConfigManager.instance().load(false);
        DBManager.instance().connect();
    }

    @Override
    public void stop(){
        DBManager.instance().close();
    }

    @Override
    public void start(Stage mainStage) throws IOException{
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/GraphScene.fxml"));
        Model model = new AppModel();
        Controller controller = new Controller(model);
        VBox root = null;
        Scene scene = null;

        loader.setController(new FxmlController(model, controller));
        root = loader.load();
        scene = new Scene(root);

        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}

