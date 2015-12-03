package aSolution;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.security.SecureRandom;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bradley Trowbridge
 */
public class PlayerOcean extends Ocean {
    
    private Ocean gameBoard;
    
    public PlayerOcean (int scale, Ocean board){
        super(scale);
        gameBoard = board;
    }

    @Override
    public void placeTilesOnPanel(){
        
        panel.setSize(100*scale,100*scale);
        panel.setBackground(Color.BLUE);
        
                
        Insets margin = new Insets(0,0,0,0);
        for(int i = 0; i<11;i++){
            for(int j = 0; j < 11; j++){
                JButton tile = new JButton ();
                tile.setMargin(margin);
                tile.setSize(64,64);
                
                if(i == 0 && j == 0){
                    tile.setText("Board");
                }
                else if (i == 0 || j ==0){
                    if (j != 0){
                    tile.setText("*"+j+"*");
                    }
                    else{
                    tile.setText("*"+i+"*");
                    }
                }
                else{ 
                    if(ships[i-1][j-1].getLabelTextAt(i-1, j-1) == "X"){
                        tile.setIcon(EXPLOSION);
                    }
                    else if(ships[i-1][j-1].getLabelTextAt(i-1, j-1) == "^"){
                        tile.setIcon(SPLASH);
                    }
                    else{

                        switch(ships[i - 1][j - 1].getShipCharacterPiece()){
                            case"B":
                                tile.setIcon(BATTLESHIP);
                                break;
                            case"C":
                                tile.setIcon(CRUISER);
                                break;
                            case"D":
                                tile.setIcon(DESTROYER);
                                break;
                            case"S":
                                tile.setIcon(SUBMARINE);
                                break;
                            case".":
                                tile.setIcon(EMPTYSEA);
                                break;
                           
                        }
                    }
                }
                panel.add(tile);
            }
        }
        panel.setVisible(true);
    }
    
    public void EnemyFire(){
        SecureRandom rand = new SecureRandom();
        int row,col;
        Ship ship;

        boolean mustHit = rand.nextBoolean();//randomly must hit
        if (mustHit){
            do{
                row = rand.nextInt(10);
                col = rand.nextInt(10);

                ship = getShipAtPosition(row, col);
            }while(ship.getLabelTextAt(row,col) == "^" || ship.getLabelTextAt(row,col) == "X" && !isOccupied(row,col)); // make sure computer is not repetative and hits
        }
        else{
            do{
                row = rand.nextInt(10);
                col = rand.nextInt(10);

                ship = getShipAtPosition(row, col);
            }while(ship.getLabelTextAt(row,col) == "^" || ship.getLabelTextAt(row,col) == "X"); // make sure computer is not repetative
        }
        
        
        //Will return true when the ship is hit
        if (ship.shootAt(row, col)) {
          
            hitCount++;

            if (ship.isSunk()) {
                JOptionPane.showMessageDialog(null, "Your" + ship.getShiptype() + " sank.");
            } else {
                //JOptionPane.showMessageDialog(null, "Your opponent hit.");
            }
        } else {
        //JOptionPane.showMessageDialog(null, "Your opponent missed.");
        }
            //print();
            updateGUI();
    }
    
        @Override
        public void startGame(){
            initializeBoard();
            placeAllShipsRandomly();
            print();
            shotsFired = 0;
            hitCount = 0;
            shipsSunk = 0;
            
            startGUI("Your Fleet");
            frame.setLocation(1000,0);
            
        }
}
