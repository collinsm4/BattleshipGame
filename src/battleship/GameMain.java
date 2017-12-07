package battleship;
import java.io.File;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Main game class. Implements JavaFX
 * application with two event-driven Boards.
 * Mouse events determine player shots while
 * also triggering computer to pick random
 * coordinates to shoot player Board.
 * 
 * @author Matthew Collins
 * @author Jay Jay Jacelli
 *
 */
public class GameMain extends Application {
    
	/**
     * Signifies whether game has begun.
     * false if Boards being setup.
     * true otherwise.
     */
	private boolean hasStarted = false;
	
	/**
	 * Board objects for both player and computer opponent.
	 */
    private Board opponentBoard, playerBoard;
    
    /**
     * Number of ships left to place. Equal to 
     * the length of ship as it is being placed.
     */
    private int shipsLeft = 5;
    
    /**
     * Determines whether it is 
     * computer's turn to make a move.
     */
    private boolean enemyMove = false;
    
    /**
     * Random object for determining randomized 
     * coordinates for computer opponent's shots.
     */
    private Random random = new Random();

    private VBox createBoards() {
    	playerBoard = new Board(false, e -> {
        	if(!hasStarted) {            
	            Cell cell = (Cell) e.getSource();
        		boolean vertical = e.getButton() == MouseButton.PRIMARY;
	            Ship currentShip = new Ship(shipsLeft,vertical);
        		if(playerBoard.canPlaceShip(currentShip, cell.x, cell.y)) {
	            	playerBoard.placeShip(currentShip, cell.x, cell.y);
	            	shipsLeft--;
	            	if(shipsLeft == 0) {
	            		setOpponentShips();
	            	}
	            }
        	}
        });
    	
    	opponentBoard = new Board(true, e -> {
            if(hasStarted) {
	            Cell cell = (Cell) e.getSource();
	            if(!cell.wasShot) {
	            	enemyMove = !cell.shoot();
		            if(!enemyMove) {
		                Media sound = new Media(new File("Explosion.mp3").toURI().toString());
		                MediaPlayer mediaPlayer = new MediaPlayer(sound);
		                mediaPlayer.play();
		            }
		            if (opponentBoard.ships == 0) {
		            	ImageView win = new ImageView("http://www.feistees.com/wp-content/uploads/2012/03/ysnkm.jpg");	            	
		            	BorderPane endPage = new BorderPane();
			            endPage.setPrefSize(300, 200);
			            endPage.setCenter(win);
		                Scene gameOver = new Scene(endPage);
			            Stage endStage = new Stage();
			            endStage.setResizable(false);
			            endStage.setTitle("Congradulations! You Win!");
			            endStage.setScene(gameOver);
			            endStage.show();
		            }
		            else if (enemyMove) {
		                enemyShoot();
		            }
	            }
            }
        });

        Label opponentLabel = new Label("Opponent Board:");
        Label playerLabel = new Label("Player Board:");
        VBox game = new VBox(10, opponentLabel,opponentBoard,playerLabel,playerBoard);
        game.setAlignment(Pos.CENTER);
        return game;
    }

    private void enemyShoot() {
    	while (enemyMove) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot) {
            	continue;
            }
            enemyMove = cell.shoot();
            if(enemyMove) {
            	Media sound = new Media(new File("Explosion.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }
            if (playerBoard.ships == 0) {
            	ImageView lose = new ImageView("https://st2.depositphotos.com/1575949/8005/v/950/depositphotos_80056196-stock-illustration-you-lose-red-stamp-text.jpg");
            	BorderPane endPage = new BorderPane();
	            endPage.setCenter(lose);
                Scene gameOver = new Scene(endPage);
	            Stage endStage = new Stage();
	            endStage.setResizable(false);
	            endStage.setTitle("Sorry");
	            endStage.setScene(gameOver);
	            endStage.show();
            }
        }
    }

    /**
     * After player Board set, randomly
     * sets opponenet's Board. Checks that
     * placement is valid. Once all ships 
     * placed, game is set into motion.
     */
    private void setOpponentShips() {
        shipsLeft = 5;
        while (shipsLeft > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            boolean vertical = random.nextDouble() < 0.5;
            Ship currentShip = new Ship(shipsLeft,vertical);
            if(opponentBoard.canPlaceShip(currentShip, x, y)) {
            	opponentBoard.placeShip(currentShip, x, y);
            	shipsLeft--;
            }
        }
        hasStarted = true;
    }

    /**
     * Start method initiates primary stage
     * with scene of two Boards stacked vertically.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
      	Scene scene = new Scene(createBoards());
      	primaryStage.setTitle("Prepare for Battle!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Main method, launches application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
