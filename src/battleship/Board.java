package battleship;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Board extends Parent {
    private VBox grid = new VBox();
    private boolean opponent = false;
    public int ships = 5;

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
        getChildren().add(grid);
    }

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

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)grid.getChildren().get(y)).getChildren().get(x);
    }

    private Cell[] getAdjacent(int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };
        List<Cell> adjacentCells = new ArrayList<Cell>();
        for (Point2D p : points) {
        	if (isValidPoint(p.getX(),p.getY())) {
        		adjacentCells.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }
        return adjacentCells.toArray(new Cell[0]);
    }

    public boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.length;
        if (ship.vertical) {
        	for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i)) {
                    return false;
                }
                Cell cell = getCell(x, i);
                if (cell.ship != null) {
                    return false;
                }
                for (Cell adjacent : getAdjacent(x, i)) {
                    if (!isValidPoint(x, i)) {
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
                if (!isValidPoint(i, y)) {
                    return false;
                }
                Cell cell = getCell(i, y);
                if (cell.ship != null) {
                    return false;
            	}
                for (Cell adjacent : getAdjacent(i, y)) {
                    if (!isValidPoint(i, y)) {
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

    private boolean isValidPoint(double x, double y) {
       if(x >= 0 && x < 10 && y >= 0 && y < 10) {
    	   return true;
       }
       return false;
    }
}
