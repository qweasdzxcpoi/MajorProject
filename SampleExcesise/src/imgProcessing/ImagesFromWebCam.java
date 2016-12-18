package imgProcessing;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

public class ImagesFromWebCam extends Thread {
	public static void main(String args[]){
		IplImage img = null;
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
		try {
			grabber.start();
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count = 0;
		long from = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		while(true){
			try{
				img = converter.convertToIplImage(grabber.grab());
				if(end-from >= 2000 && end-from <= 2200)
				{
					System.out.println(end-from);
					from = end;
					cvSaveImage("non"+count+".png", img);
					count++;
				}
				end = System.currentTimeMillis();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
}
