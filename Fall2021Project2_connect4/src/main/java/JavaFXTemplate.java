import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
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

  private static EventHandler<ActionEvent> reset;
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
    // Options Menu
    MenuItem close_app = new MenuItem("exit");
    close_app.setOnAction(e -> {
      System.exit(0);
    });

    MenuItem new_game = new MenuItem("New Game");
    new_game.setOnAction(e -> {
      //TODO Restart the game
    });

    // TODO Pop up window, I can clean this up to make it look pretty later ( add a class? )
    MenuItem howtoplay = new MenuItem("How to play");
    howtoplay.setOnAction(e -> {
      TextField howtoplaytext = new TextField("this should display how to play eventually");
      howtoplaytext.setEditable(false);
      Stage pop_up = new Stage();
      pop_up.initModality(Modality.APPLICATION_MODAL);
      pop_up.setTitle("How to play");
      pop_up.setMinWidth(500);
      pop_up.setMinHeight(250);
      pop_up.setResizable(false);

      Label m_label = new Label();
      m_label.setText("Simply try to match four same color boxes in the same direction\nhorizontally, vertically or diagonally.");
      m_label.setStyle("-fx-font-size:14");
      m_label.setAlignment(Pos.CENTER);
      Button close_btn = new Button("ok");
      close_btn.setStyle("-fx-font-size:14");
      close_btn.setOnAction(f -> {
        pop_up.close();
      });

      VBox spot_close_btn = new VBox(10);
      spot_close_btn.getChildren().addAll(m_label, close_btn);
      spot_close_btn.setAlignment(Pos.CENTER);

      Scene pop_scene = new Scene(spot_close_btn);
      pop_up.setScene(pop_scene);
      pop_up.show();

    });

    // theme menu
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

    // Gameplay menu
    MenuItem reverse_mv = new MenuItem("reverse");
    reverse_mv.setOnAction(e -> {
      // reverse the player move to n times user clickes reverse
    });

    //
    //*************************
    //

    //
    // adding MenuItems to Menu
    //

    options.getItems().add(howtoplay);
    options.getItems().add(new_game);
    options.getItems().add(close_app);
    
    gameplay.getItems().add(reverse_mv);

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

    return new Scene(root, 1920, 980);

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

    reset = new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event) {
        Scene game = createGameScene(primaryStage);
        primaryStage.setScene(game);
        primaryStage.show();
      }

    };

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
    TextField playerWon = new TextField(gameLogic.getPlayerWon());
    playerWon.setEditable(false);



    Button playagain = new Button("Play again");
    playagain.setOnAction(reset); 

    winnerroot.setCenter(playagain);
    winnerroot.setBottom(playerWon);

    return new Scene(winnerroot, 1920, 980);
  }

}
