package aSolution;

public class EmptySea extends Ship {
    public EmptySea(){
        super(1);
    }
    
    @Override
    public String getShiptype(){
        return "EmptySea";
    };
	
    @Override
    public boolean shootAt(int row, int column){
        hit[0] = true;
        return false;
    };
    
    @Override
    public boolean isSunk(){
        return false;
    };

    @Override
    public String getLabelTextAt(int row, int column){
        if(hit[0]){
            return "^";
        }
        return ".";
    };
    
    public String getShipCharacterPiece() {
        return (".");
    }
}