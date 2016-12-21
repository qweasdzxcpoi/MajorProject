package imgProcessing;

import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class DetecingFaceInImage{

	public static final String XML_FILE = "/home/chandansr/haarcascade_frontalface_default.xml";

	public static void main(String[] args){

		IplImage img = cvLoadImage("/home/chandansr/Pictures/hell.png");
		if(img == null){
			System.out.println("No Image");
			System.exit(0);
		}
		detect(img);
	}

	public static void detect(IplImage src){

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
		cvShowImage("Result", src);
		cvWaitKey(0);

	}			
}