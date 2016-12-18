package imgProcessing;

import org.bytedeco.javacv.CanvasFrame;  
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;  
import org.bytedeco.javacpp.opencv_core.*;

public class AccessingVideos {
	public static void main(String[] args){
		try{
			//Trying to access webcam video
			OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
			//Starting the webcam
			grabber.start();
			//Creating the object converter for converting the Frame Image to IplImage
			OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();
			//Trying to grab a frame from the webcam 
			Frame grabbedFrame = grabber.grab();
			//Converting the Image from Frame format to IplImage
			IplImage grabbedImage = converter.convertToIplImage(grabbedFrame);
			//Adding the title for the webcam video
			CanvasFrame canvasFrame = new CanvasFrame("Video with JavaCV");
			//Setting the width and height for the video to be displayed
			canvasFrame.setCanvasSize(grabbedImage.width(), grabbedImage.height());
			//Setting the default frame rate for the Video
			grabber.setFrameRate(grabber.getFrameRate());
			//Specifying the storage path for the video
			FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("/home/chandansr/Videos/mytestvideo.mp4", grabber.getImageWidth(), grabber.getImageHeight());
			//Specifying the format of the video
			recorder.setFormat("mp4");  
			//Specifying the frame rate of the video
		    recorder.setFrameRate(30); 
		    //Specifying the Bit rate of the video
		    recorder.setVideoBitrate(10 * 1024 * 1024);
		    //starting to record the video of the webcam
		    recorder.start();
		    
		    //Displaying and storing each frame from the webcam until the webcam is on
		    while (canvasFrame.isVisible() && (grabbedImage = converter.convertToIplImage(grabber.grab())) != null) {  
		         canvasFrame.showImage(grabbedFrame);  
		         recorder.record(grabbedFrame);  
		    }
		    //Stopping the storage of the video
		    recorder.stop();  
		    //Stopping the webcam 
		    grabber.stop();
		    //Closing the window which was displaying the image
		    canvasFrame.dispose();  
		}
		catch (Exception e) {
			System.out.println("Error while processing video");
			// TODO: handle exception
		}
	}
}
