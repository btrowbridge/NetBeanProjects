package aSolution;

import java.awt.Color;
import java.awt.GridLayout;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.security.SecureRandom;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Ocean {
    protected int shipsSunk;
    protected int scale;
    protected Ship[][] ships = new Ship[10][10];
    protected Ship[] fleet = new Ship[10];
    public int shotsFired;
    public int hitCount;
    
    private boolean cheating = false;
    
    private PlayerOcean myFleet;
    
    //Images
    protected final ImageIcon SUBMARINE = new ImageIcon(new ImageIcon(getClass().getClassLoader()
                                        .getResource("resource/images/Submarine.jpg")).getImage().getScaledInstance(64, 64, SCALE_SMOOTH));
    protected final ImageIcon CRUISER = new ImageIcon(new ImageIcon(getClass().getClassLoader()
                                        .getResource("resource/images/Cruiser.jpg")).getImage().getScaledInstance(64, 64, SCALE_SMOOTH));
    protected final ImageIcon DESTROYER = new ImageIcon(new ImageIcon(getClass().getClassLoader()
                                        .getResource("resource/images/Destroyer.jpg")).getImage().getScaledInstance(64, 64, SCALE_SMOOTH));
    protected final ImageIcon BATTLESHIP = new ImageIcon(new ImageIcon(getClass().getClassLoader()
                                        .getResource("resource/images/Battleship.jpg")).getImage().getScaledInstance(64, 64, SCALE_SMOOTH));
    protected final ImageIcon EMPTYSEA = new ImageIcon(new ImageIcon(getClass().getClassLoader()
                                        .getResource("resource/images/EmptySea.jpg")).getImage().getScaledInstance(64, 64, SCALE_SMOOTH));
    protected final ImageIcon EXPLOSION = new ImageIcon(new ImageIcon(getClass().getClassLoader()
                                        .getResource("resource/images/Explosion.jpg")).getImage().getScaledInstance(64, 64, SCALE_SMOOTH));
    protected final ImageIcon SPLASH = new ImageIcon(new ImageIcon(getClass().getClassLoader()
                                        .getResource("resource/images/Splash.jpg")).getImage().getScaledInstance(64, 64, SCALE_SMOOTH));
    
    //GUI
    protected JFrame frame;
    protected JPanel panel;
    
    
    public void startGUI(String title){
        frame = new JFrame(title);
        
        
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        
        panel = new JPanel(new GridLayout(0,11));
        placeTilesOnPanel();
        panel.setVisible(true);
        
        frame.add(panel);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
      
        frame.setVisible(true);
        
    }
    public void updateGUI() {
        
        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        //startGUI();
        panel.removeAll();
        placeTilesOnPanel();
        
        panel.revalidate();
        panel.repaint();
    }
    public void placeTilesOnPanel(){
        
        
        panel.setSize(100*scale,100*scale);
        panel.setBackground(Color.BLUE);
        
                
        Insets margin = new Insets(0,0,0,0);
        for(int i = 0; i<10;i++){
            for(int j = 0; j < 11; j++){
                JButton tile = new JButton ();
                tile.setMargin(margin);
                tile.setSize(64,64);
                
                if(i == 0 && j == 0){
                    tile.setText("Score: " + hitCount);
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
                        tile.addActionListener(new shipEvent(i-1,j-1));
                        String charPiece;
                        if(cheating)
                            charPiece = ships[i - 1][j - 1].getShipCharacterPiece();
                        else{
                            charPiece = ".";
                        }
                        switch(charPiece){
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
    
    public class shipEvent implements ActionListener{
    
        int row;
        int column;
        public shipEvent(int r, int c){
            row = r;
            column = c;
        }
        @Override
        public void actionPerformed(ActionEvent e ){

            Ship ship = getShipAtPosition(row, column);
            shotsFired++;

            //Will return true when the ship is hit
            if (ship.shootAt(row, column)) {
            //               update(); //Updates the labels on the board
            hitCount++;

            if (ship.isSunk()) {
                JOptionPane.showMessageDialog(null, "You sank a " + ship.getShiptype() + ".");
            } else {
                //JOptionPane.showMessageDialog(null, "Your shot was a hit.");
            }
            } else {
                //JOptionPane.showMessageDialog(null, "Your shot was a miss.");
            }
            //print();
            myFleet.EnemyFire();
            updateGUI();
           if(isGameOver() || myFleet.isGameOver())
           {
               gameOverScreen();
               frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
               myFleet.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
           }
        }
        
    }
    
    
    
    public Ocean(int scale) {
        initializeBoard();
        this.scale = scale;
        
    }

    public final void initializeBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                ships[row][col] = new EmptySea();
            }
        }
    }



    public void placeAllShipsRandomly() {
        init_fleet();
        SecureRandom rand = new SecureRandom();
        int row = rand.nextInt(10);
        int column = rand.nextInt(10);
        boolean horizontal = rand.nextBoolean();
        
        for(Ship ship : fleet){
            
            while(isOccupied(row,column) || !ship.okToPlaceShipAt(row, column, horizontal, this)){
                row = rand.nextInt(10);
                column = rand.nextInt(10);
                horizontal = rand.nextBoolean();
            }
            ship.setBowColumn(column);
            ship.setBowRow(row);
            ship.setHorizontal(horizontal);
            ship.placeShipAt(row, column, horizontal, this);
            
        }
        
    }

    public Ship getShipAtPosition(int row, int col) {
        return ships[row][col];
    }

    public void putShipAtPosition(int row, int col, Ship ship) {
        ships[row][col] = ship;
    }

    public boolean isOccupied(int row, int column) {
        //Out of bounds
        if (row < 0 || row > 9 || column < 0 || column > 9) {
            return false;
        } //EmptySea means square is not occupied
        else if (ships[row][column].getShiptype().equals("EmptySea")) {
            return false;
        } else {
            return true;
        }
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getShipsSunk() {
        return shipsSunk;
    }

    public boolean isGameOver() {
        return (hitCount == 20);
    }

    public Ship[][] getShipArray() {
        return ships;
        
    }

    public void init_fleet() {
        fleet[0] = new Battleship();
        fleet[1] = new Cruiser();
        fleet[2] = new Cruiser();
        fleet[3] = new Destroyer();
        fleet[4] = new Destroyer();
        fleet[5] = new Destroyer();
        fleet[6] = new Submarine();
        fleet[7] = new Submarine();
        fleet[8] = new Submarine();
        fleet[9] = new Submarine();
    }

    public Ship[] getFleet() {
        return fleet;
    }

    public void print() {

        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (row == 0 && col == 0) {
                    System.out.print("\t");
                } //First row contains numbers to aid the player
                else if (row == 0) {
                    System.out.print(col - 1);
                    System.out.print("\t");
                } //First column contains numbers to aid the player
                else if (col == 0) {
                    System.out.print(row - 1);
                    System.out.print("\t");
                } else {
                    System.out.print(ships[row - 1][col - 1].getLabelTextAt(row - 1, col - 1) + "\t");
                }
            }
            System.out.print("\t");
            System.out.print("\n\n\n");

        }
        
    }

    public void gameOverScreen() {
        int shots = this.getShotsFired();
        int hits = this.getHitCount();
        
        if(myFleet.isGameOver()){
            JOptionPane.showMessageDialog(null, "Your ships have been sunk. \n Out of the " + shots + " shots you took, " + hits + " were hits.");
        }
        else{
            JOptionPane.showMessageDialog(null, "Out of the " + shots + " shots you took, " + hits + " were hits.");
        }
    }



    public void startGame() {
        initializeBoard();
        placeAllShipsRandomly();
        print();
        shotsFired = 0;
        hitCount = 0;
        shipsSunk = 0;
        startGUI("Enemy Fleet");
        myFleet = new PlayerOcean(scale,this);
        myFleet.startGame();
        initCheatCodes();
    }
    public void initCheatCodes(){
        JFrame cheat = new JFrame("Cheater");
        cheat.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JButton button = new JButton("Toggle Cheating");
        button.addActionListener(new ShowAll());
        
        JPanel p = new JPanel();
        p.setSize(200,150);
        p.add(button);
        
        cheat.add(p);
        cheat.setSize(400,150);
        cheat.setLocationRelativeTo(null);
        cheat.setVisible(true);
    }
    public class ShowAll implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e) {
            if(cheating){
                cheating = false;
            }
            else{
                cheating = true;
            }
            updateGUI();
        }
    }
}
