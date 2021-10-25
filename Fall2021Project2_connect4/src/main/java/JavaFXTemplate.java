import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.util.HashMap;

public class JavaFXTemplate extends Application {

  private static GameLogic gameLogic;
  private GridPane gameboard;

  private BorderPane root;
  private MenuBar settings;
  
  private static ListView<String> whosmove;
  private static TextField turndisplay;

  private static HashMap<String, Scene> sceneMap;

  private static EventHandler<ActionEvent> reset;
  private EventHandler<ActionEvent> welcomeEvent;
  private EventHandler<ActionEvent> changeTheme;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

  public static void addturnDisp(String _turn, GameButton _piece){
    whosmove.getItems().add(_turn + " Player moved to (" + _piece.getXcord() + ", " + _piece.getYcord() + ")");
    turndisplay.setText("It is " + gameLogic.nextTurn() + "'s turn");
  }

  public static void removelast(){
    gameLogic.whosTurn();
    whosmove.getItems().remove(gameLogic.getTotalPieces()+1); // +1 offset the displayname
    turndisplay.setText("It is " + gameLogic.nextTurn() + "'s turn");
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
    new_game.setOnAction(reset);

    MenuItem howtoplay = new MenuItem("How to play");
    howtoplay.setOnAction(e -> {
      Popup_menu();
    });

    // theme menu
    MenuItem ogtheme = new MenuItem("Original Theme");
    ogtheme.setOnAction(e -> {
      OriginalTheme();
    });

    MenuItem ThemeOne = new MenuItem("Theme One");
    ThemeOne.setOnAction(e -> {
      ThemeOneScene();
    });

    MenuItem ThemeTwo = new MenuItem("Theme Two");
    ThemeTwo.setOnAction(e -> {
      ThemeTwoScene();
    });

    // Gameplay menu
    MenuItem reverse_mv = new MenuItem("reverse");
    reverse_mv.setOnAction(e -> {
      // reverse the player move to n times user clickes reverse
      // gameLogic.
      if(gameLogic.getTotalPieces() > 0){
        gameLogic.reversemove();
      }
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
    whosmove.getItems().add("Player Moves");

    turndisplay = new TextField(gameLogic.nextTurn());
    turndisplay.setEditable(false);


    settings.getMenus().addAll(options, gameplay, themes);

    OriginalTheme();
    Scene main_S= new Scene(root, 1750, 980);
    _pirmarystage.setResizable(true);
    return main_S ;

  }

  private void Popup_menu () {

    Stage pop_up = new Stage();
    pop_up.initModality(Modality.APPLICATION_MODAL);
    pop_up.setTitle("How to play");
    pop_up.setMinWidth(500);
    pop_up.setMinHeight(250);
    pop_up.setResizable(false);

    Label m_label = new Label();
    m_label.setText("Simply try to match four same color boxes in the same direction\nhorizontally, vertically or diagonally. \n" +
            "Blue (Player One) will always go first");
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

  }

  
  //
  // OriginalTheme
  //
  // Orignal theme attributes
  //
  private void OriginalTheme(){
      root.setStyle("-fx-background-color: #dcdcdc");

      HBox topsettings = new HBox(20, settings);


      VBox whosmoveDataBox = new VBox(20, whosmove);
      whosmoveDataBox.setStyle("-fx-font-size:16");
      whosmoveDataBox.setBlendMode(BlendMode.DARKEN);
      whosmoveDataBox.setAlignment(Pos.TOP_CENTER);

      HBox playersturn = new HBox(20, turndisplay);
      playersturn.setStyle("-fx-font-size:16");
      playersturn.setAlignment(Pos.BOTTOM_RIGHT);
      playersturn.setPadding(new Insets(10));

      // setting the whole scene
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
      root.setStyle("-fx-background-image: url(theme1.jpg)");
      HBox settingsThemeOne = new HBox(20, settings);

      VBox whose_move = new VBox(20, whosmove );
      whose_move.setPadding(new Insets(50));
      whose_move.setAlignment(Pos.CENTER);
      whose_move.setBlendMode(BlendMode.SCREEN);

      HBox player_turnis = new HBox(20, turndisplay);
      player_turnis.setStyle("-fx-font-size:16");
      player_turnis.setAlignment(Pos.CENTER);
      player_turnis.setPadding(new Insets(20));


      clearRoot();
      root.setCenter(gameboard);
      root.setLeft(settingsThemeOne);
      root.setRight(whose_move);
      root.setBottom(player_turnis);

  }

  //
  // themeTwo
  //
  // creates themeTwo
  //
  private void ThemeTwoScene(){
    root.setStyle("-fx-background-image: url(th2.jpg)");
    HBox settingsThemeTwo = new HBox(20, settings);
    settingsThemeTwo.setAlignment(Pos.TOP_RIGHT);

    VBox whose_move_2 = new VBox(20, whosmove );
    whose_move_2.setPadding(new Insets(50));
    whose_move_2.setAlignment(Pos.CENTER);
    whose_move_2.setBlendMode(BlendMode.SCREEN);

    HBox player_turnis_2 = new HBox(20, turndisplay);
    player_turnis_2.setStyle("-fx-font-size:16");
    player_turnis_2.setAlignment(Pos.CENTER);
    player_turnis_2.setPadding(new Insets(50));

    clearRoot();
    root.setCenter(gameboard);
    root.setRight(settingsThemeTwo);
    root.setLeft(whose_move_2);
    root.setBottom(player_turnis_2);
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

    welcomeEvent = new EventHandler<ActionEvent>(){

      @Override
      public void handle(ActionEvent event) {
        primaryStage.setScene(sceneMap.get("Game screen"));      
      }
  
    };

    sceneMap = new HashMap<String,Scene>();
    // this.game = createGameScene();
    // primaryStage.setScene(game);
    // primaryStage.show();

    sceneMap.put("Start screen", createStartScreen());
    sceneMap.put("Game screen", createGameScene(primaryStage));
    sceneMap.put("Winner screen", winnerWinnerChickenDinner());


    // primaryStage.setScene(sceneMap.get("Start Screen"));
    primaryStage.setScene(sceneMap.get("Start screen"));
    primaryStage.show();

		// Scene scene = new Scene(new VBox(), 700,700);
		// primaryStage.setScene(scene);
		// primaryStage.show();
	}

  private Scene createStartScreen( ){
    BorderPane welcomeRoot = new BorderPane();
    Image bckgrnd = new Image ("welcome.jpg");

    BackgroundSize bSize = new BackgroundSize(1920, 1080, true, true, true, false);

    welcomeRoot.setBackground(new Background(new BackgroundImage(bckgrnd,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bSize)));

    Button readyPlayerOne = new Button("READY PLAYER ONE");
    readyPlayerOne.setOnAction(welcomeEvent);
    readyPlayerOne.setStyle("-fx-font-family: sans-serif;-fx-font-size:24");
    VBox v1 = new VBox();
    v1.getChildren().add(readyPlayerOne);
    v1.setAlignment(Pos.BOTTOM_RIGHT);
    welcomeRoot.setBottom(v1);

    return new Scene(welcomeRoot, 1750, 980);

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

    Button exit = new Button("EXIT GAME");
    exit.setOnAction(e -> System.exit(0));

    winnerroot.setCenter(playagain);
    winnerroot.setBottom(playerWon);
    winnerroot.setLeft(exit);

    return new Scene(winnerroot, 1750, 980);
  }

}
