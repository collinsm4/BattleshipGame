package battleship;

import javafx.scene.Parent;

/**
 * Ship object representing a Cell
 * on a Board. Has unique length and 
 * orientation, either vertical or
 * horizontal. Life is as long as its
 * amount of occupied spaces on Board.
 * 
 * @author Matthew Collins
 * @author Jay Jay Jacelli
 */
public class Ship extends Parent {
    
	/**
     * Amount of spaces occupied on Board.
     */
	public int length;
	
	/**
	 * Orientation. Horizontal if false.
	 */
    public boolean vertical = true;
    
    /**
     * Amount of health left.
     */
    public int life;

    /**
     * Constructor to create Ship object.
     * Sets the length, orientation, and 
     * life of the ship.
     * 
     * @param length Amount of spaces occupied on Board
     * @param vertical Orientation, true if vertical, false if horizontal
     */
    public Ship(int length, boolean vertical) {
        this.length = length;
        this.vertical = vertical;
        life = length;
    }

    /**
     * Removes life if Ship is hit.
     */
    public void hit() {
        life--;
    }
}
