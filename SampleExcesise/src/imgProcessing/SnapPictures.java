package imgProcessing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.Loader;

@SuppressWarnings("serial")
public class SnapPictures extends JFrame{

	private TakingSnapShotsOfWebCam pp;

	  public SnapPictures(){
	    super( "Snaps Pics (with JavaCV)" );
	    Container c = getContentPane();
	    c.setLayout( new BorderLayout() );  

	    //Preload the opencv_objdetect module to work around a known bug.
	    Loader.load(opencv_objdetect.class);

	    pp = new TakingSnapShotsOfWebCam();  
	    c.add(pp, BorderLayout.CENTER);

	    addKeyListener( new KeyAdapter() {
	      public void keyPressed(KeyEvent e)
	      { 
	        int keyCode = e.getKeyCode();
	        if ((keyCode == KeyEvent.VK_NUMPAD5) || (keyCode == KeyEvent.VK_ENTER) ||
	             (keyCode == KeyEvent.VK_SPACE))
	          // take a snap when press NUMPAD-5, enter, or space is pressed
	          pp.takeSnap();
	      }
	     });


	    addWindowListener( new WindowAdapter() {
	      public void windowClosing(WindowEvent e)
	      { pp.closeDown();    // stop snapping pics
	        System.exit(0);
	      }
	    });

	    setResizable(false);
	    pack();  
	    setLocationRelativeTo(null);
	    setVisible(true);
	  } // end of SnapPics()



	  // --------------------------------------------------

	  public static void main( String args[] )
	  {  new SnapPictures(); }
}
