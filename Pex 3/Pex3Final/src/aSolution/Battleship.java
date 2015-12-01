package aSolution;



public class Battleship extends Ship {
    

    
    public Battleship(){
        super(4);
        
    }
    
    @Override
    public String getShiptype(){
        return "Battleship";
    };
    @Override
    public String getShipCharacterPiece() {
        return ("B");
    }
}