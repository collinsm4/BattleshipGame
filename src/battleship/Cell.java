package battleship;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Cell object representing a rectangle
 * on a supplied Board. Contains unique
 * x and y coordinates. Can contain ship
 * and can be shot.
 * 
 * @author Matthew Collins
 * @author Jay Jay Jacelli
 */
public class Cell extends Rectangle {
    
	/**
     * x and y coordinates of the Cell    
     */
	public int x, y;
    
	/**
     * Denotes whether Cell has already been hit
     */
	public boolean wasShot = false;
        
	/**
	 * Ship field may either contain a Ship or remain null
	 */
	public Ship ship = null;
    
	/**
	 * Board that Cell exists on
	 */
	private Board board;

	/**
	 * Constructor to create Cell object.
	 * Sets x and y coordinates and establishes
	 * associated Board. Colors Cell with 
     * light blue fill and black outline.   
     * 
     * @param x x coordinate
     * @param y y coodinate
     * @param board Board that the cell is associated with
     */
	public Cell(int x, int y, Board board) {
    	 super(30, 30);
         this.x = x;
         this.y = y;
         this.board = board;
         setFill(Color.DARKBLUE);
         setStroke(Color.GREEN);
     }

     /**
      * Shoots Cell, changes state to shot.
      * Recolors Cell to either red, if hit,
      * or black if miss. Removes hit Ship
      * from Board if life is zero.
      * 
      * @return true if hit, false if miss
      */
     public boolean shoot() {
    	 wasShot = true;
         if (ship != null) {
             ship.hit();
             setFill(Color.RED);
             if (ship.life == 0) {
                 board.ships--;
             }
             return true;
         }
         setFill(Color.BLACK);
         return false;
     }
}
