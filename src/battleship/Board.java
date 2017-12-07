package battleship;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Board object representing a 
 * grid-like battlefield. Contains 
 * methods to validate Ship placement,
 * place Ships, and acquire coordinates.
 *
 * @author Matthew Collins
 * @author Jay Jay Jacelli 
 */
public class Board extends Parent {
    
	/**
     * Grid of Cells representing the displayed Board.
     */
	private VBox grid = new VBox();
	
	/**
	 * Signifies whether it is a computer or player Board.
	 */
    private boolean opponent = false;
    
    /**
     * Number of Ships left on Board.
     */
    public int ships = 5;

    /**
     * Constructor for Board object. Creates rows of Cells to be
     * placed in a VBox representing the grid itself. Also sets
     * a mouse click event for each Cell.
     * 
     * @param opponent true if computer Board, false if player Board
     * @param handler EventHandler to set mouse click event on Board
     */
    public Board(boolean opponent, EventHandler<? super MouseEvent> handler) {
        this.opponent = opponent;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell cell = new Cell(x, y, this);
                cell.setOnMouseClicked(handler);
                row.getChildren().add(cell);
            }
            grid.getChildren().add(row);
        }
        this.getChildren().add(grid);
    }

    /**
     * Places Ship object in certain Cells on Board
     * based on provided coordinates. Ship placement
     * is either vertical or horizontal, depending on
     * Ship object's orientation.
     * 
     * @param ship Ship object being placed
     * @param x x coordinate on Board to place ship
     * @param y y coordinate on Board to place ship
     */
    public void placeShip(Ship ship, int x, int y) {
    	int length = ship.length;
    	if (ship.vertical) {
           	for (int i = y; i < y + length; i++) {
           		Cell cell = getCell(x, i);
                cell.ship = ship;
                if (!opponent) {
                	cell.setFill(Color.GREY);
                    cell.setStroke(Color.GREEN);
                } 
            } 
    	}
        else {
           	for (int i = x; i < x + length; i++) {
           		Cell cell = getCell(i, y);
           		cell.ship = ship;
                if (!opponent) {
                	cell.setFill(Color.GREY);
                    cell.setStroke(Color.GREEN);
                }
           	}
        }
    }

    /**
     * Finds Cell in grid based 
     * on x and y coordinates.
     * 
     * @param x x coordinate of Cell
     * @param y y coordinate of Cell
     * @return Cell at coordinates (x,y)
     */
    public Cell getCell(int x, int y) {
        return (Cell)((HBox)grid.getChildren().get(y)).getChildren().get(x);
    }

    /**
     * Creates ArrayList of valid Cells 
     * surrounding Cell at specified coordinates.
     * 
     * @param x x coordinate of Cell
     * @param y y coordinate of Cell
     * @return ArrayList of valid surrounding Cells 
     */
    private Cell[] getAdjacent(int x, int y) {
        Point2D[] adjacentCoordinates = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };
        ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
        for (Point2D coordinate : adjacentCoordinates) {
        	if (validCoordinates(coordinate.getX(),coordinate.getY())) {
        		adjacentCells.add(getCell((int)coordinate.getX(), (int)coordinate.getY()));
            }
        }
        adjacentCells.trimToSize();
        return adjacentCells.toArray(new Cell[adjacentCells.size()]);
    }

    /**
     * Determines whether Ship placement is valid.
     * Checks coordinates based on Ship orientation
     * and length. Makes sure enough unoccupied 
     * space exists on Board for Ship to be placed.
     * 
     * @param ship Ship object being placed
     * @param x x coordinate at beginning of Ship
     * @param y y coordinate at beginning of Ship
     * @return true if placement is valid, false otherwise
     */
    public boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.length;
        if (ship.vertical) {
        	for (int i = y; i < y + length; i++) {
                if (!validCoordinates(x, i)) {
                    return false;
                }
                Cell potentialCell = getCell(x, i);
                if (potentialCell.ship != null) {
                    return false;
                }
                for (Cell adjacent : getAdjacent(x, i)) {
                    if (!validCoordinates(x, i)) {
                        return false;
                    }
                    if (adjacent.ship != null) {
                        return false;
                    }
                }
            }
        }
        else {
            for (int i = x; i < x + length; i++) {
                if (!validCoordinates(i, y)) {
                    return false;
                }
                Cell potentialCell = getCell(i, y);
                if (potentialCell.ship != null) {
                    return false;
            	}
                for (Cell adjacent : getAdjacent(i, y)) {
                    if (!validCoordinates(i, y)) {
                        return false;
                    }
                    if (adjacent.ship != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Validates specified coordinates are between 0 and 9.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return true if valid, false otherwise
     */
    private boolean validCoordinates(double x, double y) {
       if(x >= 0 && x < 10 && y >= 0 && y < 10) {
    	   return true;
       }
       return false;
    }
}
