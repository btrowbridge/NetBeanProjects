package aSolution;

public class Cruiser extends Ship {
    public Cruiser(){
        super(3);
    }
    
    @Override
    public String getShiptype(){
        return "Cruiser";
    };
    @Override
    public String getShipCharacterPiece() {
        return ("C");
    }
}