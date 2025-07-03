package furhatos.app.sciencefestival.flow.camerafeed

import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import java.util.Base64



//cameraFeed


//object MyCameraFeedListener: CameraFeedListener() {
//    override fun cameraImage(image: BufferedImage, imageData: ByteArray, faces: List<FaceData>) {
//        // You can choose whether to process the image as raw JPEG data or as a BufferedImage.
//    }
//}
//// Add your listener to the camera feed. This will automatically enable the feed. Note that it can only be enabled programmatically when the skill is running on the robot. Otherwise, you have to enable it manually from the web interface.
//furhat.cameraFeed.addListener(MyCameraFeedListener)

fun getImgBase64(image: BufferedImage): String {

//    val data = baos.toByteArray();
    val outStreamObj = ByteArrayOutputStream()
    ImageIO.write(image, "jpg", outStreamObj)
    val data = outStreamObj.toByteArray()
    val encodedString: String = Base64.getEncoder().encodeToString(data)

    return "data:image/jpeg;base64,$encodedString"
}
