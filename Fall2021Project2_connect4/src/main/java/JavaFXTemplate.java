import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu; 
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.HashMap;

public class JavaFXTemplate extends Application {

  private static GameLogic gameLogic;
  private static GameData gameData;
  private GridPane gameboard;

  private BorderPane root;
  private MenuBar settings;
  
  private static ListView<String> whosmove;
  private static TextField turndisplay;

  private static HashMap<String, Scene> sceneMap;

  private EventHandler<ActionEvent> reset;
  private EventHandler<ActionEvent> dispScene;
  private EventHandler<ActionEvent> changeTheme;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

  public static void addturnDisp(String _turn){
    whosmove.getItems().add(_turn);
    turndisplay.setText(gameLogic.nextTurn());
  }


  //
  // createGameScene (Original theme)
  //
  // creates everything so settings, gameboard and logic 
  // can be reused
  //
  public Scene createGameScene(Stage _pirmarystage){
    root = new BorderPane();
    
    gameLogic = new GameLogic();
    gameboard = gameLogic.creategameboard(_pirmarystage);

    settings = new MenuBar(); 

    Menu options = new Menu("Options");
    Menu gameplay = new Menu("Game play");
    Menu themes = new Menu("Themes");

    //
    // MenuItems
    //
    MenuItem howtoplay = new MenuItem("How to play");
    howtoplay.setOnAction(e -> {
      TextField howtoplaytext = new TextField("this should display how to play eventually");
      howtoplaytext.setEditable(false);
    });

    MenuItem ogtheme = new MenuItem("Original Theme");
    ogtheme.setOnAction(e -> {
      // this.game = ThemeOneScene();
      OriginalTheme();
    });

    MenuItem ThemeOne = new MenuItem("Theme One");
    ThemeOne.setOnAction(e -> {
      // this.game = ThemeOneScene();
      ThemeOneScene();
    });

    MenuItem ThemeTwo = new MenuItem("Theme Two");
    ThemeTwo.setOnAction(e -> {
      // this.game = ThemeOneScene();
      ThemeTwoScene();
    });
    //
    //*************************
    //

    //
    // adding MenuItems to Menu
    //
    gameplay.getItems().add(howtoplay);
    
    themes.getItems().add(ogtheme);
    themes.getItems().add(ThemeOne);
    themes.getItems().add(ThemeTwo);

    //
    //*************************
    //

    whosmove = new ListView<String>();
    whosmove.getItems().add("NEW GAME!");

    turndisplay = new TextField(gameLogic.nextTurn());
    turndisplay.setEditable(false);


    settings.getMenus().addAll(options, gameplay, themes);

    OriginalTheme();

    return new Scene(root, 3000, 3000);

  }

  
  //
  // OriginalTheme
  //
  // Orignal theme attributes
  //
  private void OriginalTheme(){

    HBox topsettings = new HBox(20, settings);
    VBox whosmoveDataBox = new VBox(20, whosmove);
    HBox playersturn = new HBox(20, turndisplay);


    root.setLeft(playersturn);
    root.setBottom(whosmoveDataBox);
    root.setCenter(gameboard);
    root.setTop(topsettings);
  }

  private void clearRoot(){
    root.setCenter(null);
    root.setLeft(null);
    root.setRight(null);
    root.setTop(null);
    root.setBottom(null);
  }

  //
  // themeOne
  // 
  // creates themeone
  //
  private void ThemeOneScene(){

    VBox settingsThemeOne = new VBox(20, settings);

    clearRoot();
    root.setCenter(gameboard);
    root.setLeft(settingsThemeOne);

  }

  //
  // themeTwo
  //
  // creates themeTwo
  //
  private void ThemeTwoScene(){

    VBox settingsThemeTwo = new VBox(20, settings);

    clearRoot();
    root.setCenter(gameboard);
    root.setRight(settingsThemeTwo);
  }


	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		primaryStage.setTitle("Welcome to Connect Four!");

		sceneMap = new HashMap<String,Scene>();
    // this.game = createGameScene();
    // primaryStage.setScene(game);
    // primaryStage.show();

    // sceneMap.put("Start screen", createstartScreen());
    sceneMap.put("Game screen", createGameScene(primaryStage));
    sceneMap.put("Winner screen", winnerWinnerChickenDinner());


    // primaryStage.setScene(sceneMap.get("Start Screen"));
    primaryStage.setScene(sceneMap.get("Game screen"));
    primaryStage.show();

		// Scene scene = new Scene(new VBox(), 700,700);
		// primaryStage.setScene(scene);
		// primaryStage.show();
	}

  //
  // SCENES BELONG HERE
  //
  public static Scene winnerWinnerChickenDinner(){
    BorderPane winnerroot = new BorderPane();
    Button playagain = new Button("Play again");

    winnerroot.setCenter(playagain);

    return new Scene(winnerroot, 3000, 3000);
  }

}
