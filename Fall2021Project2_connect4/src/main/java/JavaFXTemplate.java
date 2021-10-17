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

  private GameLogic gameLogic;
  private GridPane gameboard;

  private BorderPane root;
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
  // creates everything so settings, gameboard and logic 
  // can be reused
  //
  public Scene createGameScene(){
    root = new BorderPane();
    
    gameLogic = new GameLogic();
    gameboard = gameLogic.creategameboard();

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

    root.setCenter(gameboard);
    root.setTop(topsettings);
  }

  private void clearroot(){
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

    clearroot();
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

    clearroot();
    root.setCenter(gameboard);
    root.setLeft(settingsThemeTwo);
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
