package imgProcessing;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.cvClearMemStorage;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_imgproc.CV_AA;
import static org.bytedeco.javacpp.opencv_imgproc.cvRectangle;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

public class DetecingFaceInWebCam {
	/**
	 * This class gives DetectFaceAndStoreTheVideo method which detects the presence of face in an Image and stores the video by 
	 * drawing a rectangle for face
	 */
	
	public static final String XML_FILE = "/home/chandansr/haarcascade_frontalface_default.xml";
	
	public static void main(String[] args){
		DetecingFaceInWebCam dFW = new DetecingFaceInWebCam();
		dFW.DetectFaceAndStoreTheVideo();
	}
	
	public void DetectFaceAndStoreTheVideo(){
		
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
		         IplImage IplImage = converter.convertToIplImage(grabbedFrame);
		         IplImage = detect(IplImage);
		         if(IplImage!=null){
		        	 grabbedFrame = converter.convert(IplImage);
		        	 recorder.record(grabbedFrame);
		         }
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
	
	public static IplImage detect(IplImage src){

		CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(XML_FILE));
		if(cascade.isNull()){
			System.out.println("No XML FILE");
			System.exit(0);
		}
		CvMemStorage storage = CvMemStorage.create();
		CvSeq sign = cvHaarDetectObjects(src,cascade,storage,1.5,3,CV_HAAR_DO_CANNY_PRUNING);

		cvClearMemStorage(storage);

		int total_Faces = sign.total();		

		for(int i = 0; i < total_Faces; i++){
			CvRect r = new CvRect(cvGetSeqElem(sign, i));
			cvRectangle (src,cvPoint(r.x(), r.y()),cvPoint(r.width() + r.x(), r.height() + r.y()),CvScalar.RED,2,CV_AA,0);
			r.close();
		}
		return src;
	}
}
