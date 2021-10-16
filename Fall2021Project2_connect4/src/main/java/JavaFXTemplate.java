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
import javafx.scene.layout.VBox;

public class JavaFXTemplate extends Application {

  private GameButton piece;
  private GameLogic gameLogic;
  private GridPane gameboard;

  private BorderPane root;
  private Scene ogtheme;
  private Scene themeOne;
  private Scene themeTwo;
  private MenuBar settings;
  private Scene game;
  

  private EventHandler<ActionEvent> reset;
  private static EventHandler<ActionEvent> disableButton;
  private EventHandler<ActionEvent> dispScene;
  private EventHandler<ActionEvent> changeTheme;

  //
  // this is a test
  // 
  public static EventHandler<ActionEvent> disable(){
    disableButton = new EventHandler<ActionEvent>(){

      @Override
      public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        GameButton b1 = (GameButton) event.getSource();
        b1.setDisable(true);
      }
    };

    return disableButton;
  }


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


  //
  // createGameScene (Original theme)
  //
  // creates the scene needed for the game?
  //
  public Scene createGameScene(){
    root = new BorderPane();
    
    gameLogic = new GameLogic();
    gameboard = gameLogic.creategameboard();

    settings = new MenuBar(); 

    Menu options = new Menu("Options");
    Menu gameplay = new Menu("Game play");
    Menu themes = new Menu("Themes");

    MenuItem howtoplay = new MenuItem("How to play");
    howtoplay.setOnAction(e -> {
      TextField howtoplaytext = new TextField("this should display how to play eventually");
      howtoplaytext.setEditable(false);
    });

    gameplay.getItems().add(howtoplay);

    settings.getMenus().addAll(options, gameplay, themes);

    HBox topsettings = new HBox(20, settings);

    root.setCenter(gameboard);
    root.setTop(topsettings);

    return new Scene(root, 3000, 3000);

  }


  // returns gameLogic ????
  //
  public GameLogic getGameLogic(){
    return gameLogic;
  }


  //
  // themeOne
  // 
  // creates themeone
  //
  public Scene ThemeOne(){

    return null; 
  }

  //
  // themeTwo
  //
  // creates themeTwo
  //
  public Scene themeTwo(){
    return null;
  }


	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		primaryStage.setTitle("Welcome to Connect Four!");
		
		
    this.game = createGameScene();
    primaryStage.setScene(game);
    primaryStage.show();
		// Scene scene = new Scene(new VBox(), 700,700);
		// primaryStage.setScene(scene);
		// primaryStage.show();
	}

}
