package aSolution;

import javax.swing.JLabel;


public abstract class Ship {

    protected int bowRow;
    protected int bowColumn;
    protected int length;
    protected boolean horizontal;
    protected boolean[] hit;
   
    public Ship(int length){
        this.length = length;
        hit = new boolean[length];
        for (boolean h : hit)
            h = false;
    }
    public int getLength() {
        return length;
    }

    public int getBowRow() {
        return bowRow;
    }

    public int getBowColumn() {
        return bowColumn;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setBowRow(int row) {
        bowRow = row;
    }

    public void setBowColumn(int column) {
        bowColumn = column;
    }

    public void setHorizontal(boolean isHorizontal) {
        horizontal = isHorizontal;
    }

    public abstract String getShiptype();
   
    
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        int rowItr = row; // row iterator
        int columnItr = column; //column itr
        
        //for the length of the ship
        for (int i = 0; i < length; i++){
           
            //check the row and column as well as the surrounding spots for " " or empty sea
            for (int j = -1; j <=1; j++){
                for(int k = -1; k<=1; k++){
                if( rowItr + j > 9 || columnItr + k > 9 || rowItr + j < 0 || columnItr + k < 0 ) // avoids out of bounds
                    continue;
                else if(ocean.isOccupied(rowItr+j,columnItr+k)) // if not empty
                    return false;
                }
            }
            //if horizontal increment the column iterator
            if(horizontal){
                columnItr++;    
            }
            else{// if not increment the row iterator
                rowItr++;
            }
            if(columnItr > 9 || rowItr > 9){//if we have breached the board return false
                return false;
            }    
        }
        
        return true;
    }

    public void placeShipAt(int row, int column, boolean isHorizontal, Ocean ocean) {
       int rowItr = row;
       int columnItr = column;
       
       for(int i = 0; i < length; i++){
            ocean.getShipArray()[rowItr][columnItr]  = this;
            
            if(horizontal){//if horizontal increment the column
                columnItr++;    
            }
            else{// if not increment the row iterator
                rowItr++;
            }
       }
    }

    public boolean shootAt(int row, int column) {
        if (horizontal) {
            //Checks to see if shot was a hit
            if (row == bowRow && column >= bowColumn && column <= (bowColumn + length)) {
                //Updates hit array
                hit[column - bowColumn] = true;
                return true;
            }
        } else {
            //Checks to see if shot was a hit
            if (row >= bowRow && row <= (bowRow + length) && column == bowColumn) {
                //Updates hit array
                hit[row - bowRow] = true;
                return true;
            }
        }

        return false;
    }

    public boolean isSunk() {
        int hitCount = 0;

        //Checks every square of the ship
        for (int i = 0; i < length; i++) {
            if (hit[i]) {
                hitCount++;
            }
        }
        //If the hit count equals the length of the ship then the Ship has been sunk
        return (hitCount == length);
    }

    public String getLabelTextAt(int row, int column) {
        if (horizontal) {
            if (!hit[column - bowColumn]) {
                return this.getShipCharacterPiece(); //Ship is sunk	
            }
        } else {
            if (!hit[row - bowRow]) {
                return this.getShipCharacterPiece(); //Ship is sunk
            }
        }
        
        return "X"; //Ship is afloat 
    }

    public String getShipCharacterPiece() {
        return (".");
    }
}
