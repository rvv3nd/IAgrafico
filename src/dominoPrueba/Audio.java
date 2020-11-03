package dominoPrueba;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio{
	Clip clip;
	AudioInputStream audioInputStream; 
	static String fileAudioPath;
	
	public Audio(String path) 
	        throws UnsupportedAudioFileException, 
	        IOException, LineUnavailableException  
	    { 
			fileAudioPath = path;
	        // create AudioInputStream object 
	        audioInputStream = AudioSystem.getAudioInputStream(new File(fileAudioPath).getAbsoluteFile()); 
	          
	        // create clip reference 
	        clip = AudioSystem.getClip(); 
	          
	        // open audioInputStream to the clip 
	        clip.open(audioInputStream);
	        clip.loop(1);
	       }
	 public void play()  
	    { 
	        //start the clip
		 try {
	        clip.start();
		 }catch(Exception e) {
			 System.out.println("Error reproducionedo");
		 }try {
	        clip.setMicrosecondPosition(0);
		 }catch(Exception e) {
			 System.out.println("Error restart");
		 }
	    }

} 