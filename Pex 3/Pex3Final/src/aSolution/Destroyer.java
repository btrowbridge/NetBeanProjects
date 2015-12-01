
package aSolution;


public class Destroyer extends Ship {
    public Destroyer(){
        super(2);
    }
    
    @Override
    public String getShiptype(){
        return "Destroyer";
    };
    @Override
    public String getShipCharacterPiece() {
        return ("D");
    }
    
}