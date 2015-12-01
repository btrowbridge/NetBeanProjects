package aSolution;

public class Submarine extends Ship {
    public Submarine(){
        super(1);
    }
    
    @Override
    public String getShiptype(){
        return "Submarine";
    };
    
    public String getShipCharacterPiece() {
        return ("S");
    }
}