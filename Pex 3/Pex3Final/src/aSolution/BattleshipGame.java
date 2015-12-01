package aSolution;

import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;



public class BattleshipGame {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {      
	Ocean board = new Ocean(4);
        
        playMusic();
        board.startGame();
    }
    public static void playMusic(){
        AudioPlayer BGP = AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;
        
        ContinuousAudioDataStream loop = null;
        
        
            try{
            BGM = new AudioStream(new FileInputStream("/resource/audio/NightRaid-1.wav"));
            MD = BGM.getData();
            loop = new ContinuousAudioDataStream(MD);
            }
            catch(IOException e){
                System.err.println("No file found: " + e.getMessage());
            }
            BGP.start(loop);
        
    }
    
}