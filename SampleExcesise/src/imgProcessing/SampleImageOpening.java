package imgProcessing;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import static  org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacpp.opencv_core.*;

public class SampleImageOpening {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length != 1){
			System.out.println("Usage: run ShowImage <Input-file>");
			return;
		}
		
		System.out.println("Loading image from "+ args[0]+" ...");
		IplImage img = cvLoadImage(args[0]);
		System.out.println("Size of image: ("+img.width()+", "+img.height()+")");
		
		CanvasFrame canvas = new CanvasFrame(args[0]);
		canvas.setDefaultCloseOperation(CanvasFrame.DO_NOTHING_ON_CLOSE);
		OpenCVFrameConverter.ToIplImage converter=new OpenCVFrameConverter.ToIplImage();
		canvas.showImage(converter.convert(img));
		
		try{
			
			canvas.waitKey();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		canvas.dispose();
	}

}