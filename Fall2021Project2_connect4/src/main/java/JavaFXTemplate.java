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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.util.HashMap;

public class JavaFXTemplate extends Application {
  // private data members for the board, gridpane, menubar and misc scene elements
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

  // Updates user inputted action outputs the co-ordinates selected by the user
  // and informs the players whose turn is next
  public static void addturnDisp(String _turn, GameButton _piece){
    whosmove.getItems().add(_turn + " Player moved to (" + _piece.getXcord() + ", " + _piece.getYcord() + ")");
    turndisplay.setText("It is " + gameLogic.nextTurn() + "'s turn");
  }

  // helper method for reserve, updates whose turn it is after reverse has been clicked
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
    // init root node
    root = new BorderPane();

    // creating the inital game board
    gameLogic = new GameLogic();
    gameboard = gameLogic.creategameboard(_pirmarystage);

    // init menu bar
    settings = new MenuBar();

    // menu bar items
    Menu options = new Menu("Options");
    Menu gameplay = new Menu("Game play");
    Menu themes = new Menu("Themes");

    //
    // MenuItems
    //
    // Options Menu
    // Howtoplay : under Options Menu with the event handler to open a pop up menu to inform users how to play
    MenuItem howtoplay = new MenuItem("How to play");
    howtoplay.setOnAction(e -> {
      Popup_menu();
    });

    // New_game : under Options Menu with the event handler keep listening so that when user hits "New game"
    // restarts the board to inital gameboard
    MenuItem new_game = new MenuItem("New Game");
    new_game.setOnAction(reset);

    // Exit : Under Options Menu with the event handler to keep listening so that when user hits "exit"
    // closes the whole GUI
    MenuItem close_app = new MenuItem("exit");
    close_app.setOnAction(e -> {
      System.exit(0);
    });


    // Themes menu
    // Original theme : Under Themes, basic grey background, Menu bar, V&H Boxes with information about whose turn it is
    // and what's the recent most move made by the user
    MenuItem ogtheme = new MenuItem("Original Theme");
    ogtheme.setOnAction(e -> {
      OriginalTheme();
    });

    // ThemeOne : Under Themes, shifted the previous locations of the V&H boxes to the new location for better
    // visualization and feel for the User. Also has a background Image of space
    MenuItem ThemeOne = new MenuItem("Theme One");
    ThemeOne.setOnAction(e -> {
      ThemeOneScene();
    });

    // ThemeTwo : Under Themes Menu, new look to the UI with a pleasing background image for the User
    MenuItem ThemeTwo = new MenuItem("Theme Two");
    ThemeTwo.setOnAction(e -> {
      ThemeTwoScene();
    });

    // Gameplay menu
    // Reverse : Under GamePlay menu, allows user to go back/reverse their earlier moves
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
    // additions of all the sub items of "Options" to it
    options.getItems().add(howtoplay);
    options.getItems().add(new_game);
    options.getItems().add(close_app);

    // adding the reverse to the gameplay menu as a sub item
    gameplay.getItems().add(reverse_mv);

    // adding all the themes to the themes sub
    themes.getItems().add(ogtheme);
    themes.getItems().add(ThemeOne);
    themes.getItems().add(ThemeTwo);

    //
    //*************************
    //
    // Informs who is the user/player move
    whosmove = new ListView<String>();
    whosmove.getItems().add("Player Moves");

    // Textfield for the player move
    turndisplay = new TextField(gameLogic.nextTurn());
    turndisplay.setEditable(false);

    // adding all the menu items
    settings.getMenus().addAll(options, gameplay, themes);

    // initally starting the game with the original theme
    OriginalTheme();
    // setting the scene with the parent node and a display size of 1750x980, also the GUI window is resizeable
    Scene main_S= new Scene(root, 1920, 980);
    _pirmarystage.setResizable(true);

    return main_S ;

  }

  // pop up menu implementation used for "How to Play" to inform user rules
  private void Popup_menu () {
    // setting the stage for the pop up
    Stage pop_up = new Stage();
    // when the pop up menu is open making the background of the GUI non-intractable
    // so that the players can't play the game until they are done reading the instructions
    pop_up.initModality(Modality.APPLICATION_MODAL);
    pop_up.setTitle("How to play");
    pop_up.setMinWidth(500); // pop up menu width
    pop_up.setMinHeight(250); // pop up menu height
    pop_up.setResizable(false); // setting it to non resizeable

    // pop up menu text - instructions for the game
    Label m_label = new Label();
    m_label.setText("Simply try to match four same color boxes in the same direction\nhorizontally, vertically or diagonally. \n" +
            "Blue (Player One) will always go first");
    // basic formatting of the text like font size, position etc
    m_label.setStyle("-fx-font-size:14");
    m_label.setAlignment(Pos.CENTER);
    // adding a "ok" button so that the user can click it and the pop up menu closes as soon as they are done reading it
    Button close_btn = new Button("ok");
    close_btn.setStyle("-fx-font-size:14");
    close_btn.setOnAction(f -> {
      pop_up.close();
    });

    // finally adding to a V box so better positioning
    VBox spot_close_btn = new VBox(10);
    spot_close_btn.getChildren().addAll(m_label, close_btn);
    spot_close_btn.setAlignment(Pos.CENTER);

    // outputting the scene
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
    // inital background of the original theme : grey bckgrnd
    root.setStyle("-fx-background-color: #dcdcdc");

    // menu bar
    HBox topsettings = new HBox(20, settings);

    // Informs user about the most recent move with x & y coordinates
    VBox whosmoveDataBox = new VBox(20, whosmove);
    whosmoveDataBox.setStyle("-fx-font-size:16");
    whosmoveDataBox.setBlendMode(BlendMode.DARKEN);
    whosmoveDataBox.setAlignment(Pos.TOP_CENTER);

    // Infroms user whose current turn is
    HBox playersturn = new HBox(20, turndisplay);
    playersturn.setStyle("-fx-font-size:16");
    playersturn.setAlignment(Pos.BOTTOM_RIGHT);
    playersturn.setPadding(new Insets(10));

    // setting the whole scene with different positions
    root.setLeft(playersturn);
    root.setBottom(whosmoveDataBox);
    root.setCenter(gameboard);
    root.setTop(topsettings);

  }

  // helper func to clear the screen so that new themes can modify the screen accordingly
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
    // init background image for theme 1 : jpg is in Resources folder
    root.setStyle("-fx-background-image: url(theme1.jpg)");

    // setting up menu bar
    HBox settingsThemeOne = new HBox(20, settings);

    // setting up whose turn it was with x & y coordinates
    VBox whose_move = new VBox(20, whosmove );
    whose_move.setPadding(new Insets(50));
    whose_move.setAlignment(Pos.CENTER);
    whose_move.setBlendMode(BlendMode.SCREEN);

    // setting up the box for whose current is
    HBox player_turnis = new HBox(20, turndisplay);
    player_turnis.setStyle("-fx-font-size:16");
    player_turnis.setAlignment(Pos.CENTER);
    player_turnis.setPadding(new Insets(20));

    // clearing the screen to setup new locations of the boxes
    clearRoot();

    // new positions for the boxes
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
    // init background image for theme 1 : jpg is in Resources folder
    root.setStyle("-fx-background-image: url(th2.jpg)");

    // menu bar setup
    HBox settingsThemeTwo = new HBox(20, settings);
    settingsThemeTwo.setAlignment(Pos.TOP_RIGHT); // aligning it

    //vbox for players moves
    VBox whose_move_2 = new VBox(20, whosmove );
    whose_move_2.setPadding(new Insets(50));
    whose_move_2.setAlignment(Pos.CENTER);
    whose_move_2.setBlendMode(BlendMode.SCREEN);

    // Hbox for current player info
    HBox player_turnis_2 = new HBox(20, turndisplay);
    player_turnis_2.setStyle("-fx-font-size:16");
    player_turnis_2.setAlignment(Pos.CENTER);
    // setting it not all the way to the top of the screen
    player_turnis_2.setPadding(new Insets(50));

    // clearing up the old screen to setup a new one with different layout and positions
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
        // init message of the UI
		primaryStage.setTitle("Welcome to Connect Four!");

    reset = new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event) {
        Scene game = createGameScene(primaryStage); // create the game
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

    // calling all the init methods to get started on the board etc
    sceneMap = new HashMap<String,Scene>();

    sceneMap.put("Start screen", createStartScreen());
    sceneMap.put("Game screen", createGameScene(primaryStage));
    sceneMap.put("Winner screen", winnerWinnerChickenDinner());
    // start init screen
    primaryStage.setScene(sceneMap.get("Start screen"));
    primaryStage.show();

	}

  private Scene createStartScreen( ){
    // initial welcome screen with a bckgrnd image
    BorderPane welcomeRoot = new BorderPane();
    welcomeRoot.setStyle("-fx-background-image: url(welcome.jpg)");

    // start button
    Button readyPlayerOne = new Button("READY PLAYER ONE");
    readyPlayerOne.setOnAction(welcomeEvent);
    readyPlayerOne.setStyle("-fx-font-family: sans-serif;-fx-font-size:24");
    VBox v1 = new VBox();
    v1.getChildren().add(readyPlayerOne);
    v1.setAlignment(Pos.BOTTOM_RIGHT);
    welcomeRoot.setBottom(v1);
    // welcome scene return
    return new Scene(welcomeRoot, 1920, 980);
  }

  //
  // SCENES BELONG HERE
  // final end screen
  public static Scene winnerWinnerChickenDinner(){
    // final screen after anyone of the player wins
    BorderPane winnerroot = new BorderPane();

    if ( gameLogic.getPlayerWon() == "B" ) {
      winnerroot.setStyle("-fx-background-image: url(blue_won.png)");
    }
    else{
      winnerroot.setStyle("-fx-background-image: url(red_won.jpg)");
    }

    // if the user hits play agian reset the game board to start a new game
    Button playagain = new Button("Play again");
    playagain.setOnAction(reset);
    playagain.setStyle("-fx-font-size: 24");

    // Exit button
    Button exit = new Button("EXIT GAME");
    exit.setStyle("-fx-font-size: 24");
    exit.setOnAction(e -> System.exit(0));

    HBox end_game_menu = new HBox();
    end_game_menu.getChildren().addAll(playagain, exit);
    end_game_menu.setAlignment(Pos.CENTER);
    end_game_menu.setPadding(new Insets(10));
    end_game_menu.setSpacing(10);
    // placing the buttons on to the screen
      winnerroot.setBottom(end_game_menu);

    //returning the final scene
    return new Scene(winnerroot, 1920, 980);
  }

}
