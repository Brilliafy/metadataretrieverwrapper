
package com.michaelam.metadataretrieverwrapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


@DesignerComponent(
        version = MetadataRetriever.VERSION,
        description =
                "An efficient extension for extracting media metadata.",
        category = ComponentCategory.EXTENSION, nonVisible = true,
        iconName = "https://i.ibb.co/RTxMLN4/icon.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.WRITE_EXTERNAL_STORAGE")
public class MetadataRetriever extends AndroidNonvisibleComponent {
    public static final int VERSION = 1;
    private static Context context;

    public final HashMap<Integer,Integer> minConstantsSDKVersion = new HashMap<Integer,Integer>(){{
        //(K)ey is constant, (V)alue is SDK version.
        put(1,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(2,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(3,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(4,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(5,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(6,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(7,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(8,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(9,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(10,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(12,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(13,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(14,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(15,Build.VERSION_CODES.GINGERBREAD_MR1);
        put(16,Build.VERSION_CODES.ICE_CREAM_SANDWICH);
        put(17,Build.VERSION_CODES.ICE_CREAM_SANDWICH);
        put(18,Build.VERSION_CODES.ICE_CREAM_SANDWICH);
        put(19,Build.VERSION_CODES.ICE_CREAM_SANDWICH);
        put(20,Build.VERSION_CODES.ICE_CREAM_SANDWICH);
        put(23,Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1);
        put(24,Build.VERSION_CODES.JELLY_BEAN_MR1);
        put(25,Build.VERSION_CODES.M);
        put(26,Build.VERSION_CODES.P);
        put(27,Build.VERSION_CODES.P);
        put(28,Build.VERSION_CODES.P);
        put(29,Build.VERSION_CODES.P);
        put(30,Build.VERSION_CODES.P);
        put(31,Build.VERSION_CODES.P);
        put(32,Build.VERSION_CODES.P);
        put(33,Build.VERSION_CODES.Q);
        put(34,30);
        put(35,30);
        put(36,30);
        put(37,30);
        put(38,31);
        put(39,31);
        put(41,31);
        put(42,31);

    }};

    public MetadataRetriever(ComponentContainer container) {
        super(container.$form());
        context = container.$context();
    }

    @SimpleFunction(description = " This method retrieves the meta data value associated with the keyCode. The keyCode currently supported is listed in the official android MetadataRetriever class page as METADATA_XXX constants. With any other value, it returns a null pointer.")
    public String extractMetadata(String absoluteFilename, int keyCode) {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1) {
            return "ERROR! Insufficient API level.";
        }
        if(android.os.Build.VERSION.SDK_INT < minConstantsSDKVersion.get(keyCode))
        {
            return "ERROR! Insufficient API level. API level should be '" + minConstantsSDKVersion.get(keyCode).toString() + "' to use that keycode. Current API level is: " + Integer.toString(android.os.Build.VERSION.SDK_INT);
        }

        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        String metaData = "";
        try
        {
            metaRetriever.setDataSource(locateAbsoluteFilePath(absoluteFilename));
            metaData = metaRetriever.extractMetadata(keyCode);
            metaRetriever.release();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(metaData == null || metaData.isEmpty() || metaData.trim().isEmpty())
        {
            return "ERROR! Could not retrieve metadata.";
        }
        return metaData;
    }

    @SimpleFunction(description = "This method finds the optional graphic or album/cover art associated associated with the data source. If there are more than one pictures, (any) one of them is returned.")
    public String getEmbeddedPicture(String absoluteFilename)
    {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1) {
            return "ERROR! Insufficient API level.";
        }

        String embeddedImagePath = "ERROR! Could not retrieve embedded image.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(locateAbsoluteFilePath(absoluteFilename));
            byte[] embeddedImageData = metaRetriever.getEmbeddedPicture();
            metaRetriever.release();
            if(embeddedImageData == null) {
                return embeddedImagePath;
            }
            Bitmap embeddedImageBitmap = BitmapFactory.decodeByteArray(embeddedImageData, 0, embeddedImageData.length);
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "embeddedImage.png"));
            embeddedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            embeddedImagePath = new File(context.getCacheDir(), "embeddedImage.png").getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "file://" + embeddedImagePath;
    }

    @SimpleFunction(description = "This method retrieves a video frame by its index.")
    public String getFrameAtIndex (String absoluteFilename, int frameIndex)
    {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return "ERROR! Insufficient API level.";
        }

        String videoFramePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(locateAbsoluteFilePath(absoluteFilename));
            int mediaTotalFrames =  Integer.parseInt(metaRetriever.extractMetadata(32));
            if(mediaTotalFrames < frameIndex)
            {
                return "ERROR! Frame index is out of bounds. Last frame index is: " + mediaTotalFrames;
            }
            Bitmap frameBitmap = metaRetriever.getFrameAtIndex(frameIndex);
            metaRetriever.release();
            if(frameBitmap == null) {
                return videoFramePath;
            }
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            videoFramePath = new File(context.getCacheDir(), "videoFrame.png").getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "file://" + videoFramePath;
    }

    @SimpleFunction(description = "This method retrieves a video frame by its index.")
    public String getFrameAtTime (String absoluteFilename, long timeUs)
    {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1) {
            return "ERROR! Insufficient API level.";
        }

        String videoFramePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(locateAbsoluteFilePath(absoluteFilename));
            int mediaDurationMicroseconds =  Integer.parseInt(metaRetriever.extractMetadata(9)) * 1000;
            if(mediaDurationMicroseconds < timeUs)
            {
                return "ERROR! Frame is out of media duration bounds. Last frame is captured at: " + mediaDurationMicroseconds + " microseconds.";
            }
            Bitmap frameBitmap = metaRetriever.getFrameAtTime(timeUs);
            metaRetriever.release();
            if(frameBitmap == null) {
                return videoFramePath;
            }
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            videoFramePath = new File(context.getCacheDir(), "videoFrame.png").getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "file://" + videoFramePath;
    }

    @SimpleFunction(description = "This method retrieves a video frame by its index.")
    public String getFrameAtTimeOptionOverload(String absoluteFilename, long timeUs, int option)
    {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1) {
            return "ERROR! Insufficient API level.";
        }

        String videoFramePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(locateAbsoluteFilePath(absoluteFilename));
            int mediaDurationMicroseconds =  Integer.parseInt(metaRetriever.extractMetadata(9)) * 1000;
            if(mediaDurationMicroseconds < timeUs)
            {
                return "ERROR! Frame is out of media duration.";
            }
            Bitmap frameBitmap = metaRetriever.getFrameAtTime(timeUs, option);
            metaRetriever.release();
            if(frameBitmap == null) {
                return videoFramePath;
            }
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            videoFramePath = new File(context.getCacheDir(), "videoFrame.png").getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "file://" + videoFramePath;
    }

    @SimpleFunction(description = "This method retrieves a still image by its index.")
    public String getImageAtIndex (String absoluteFilename, int imageIndex)
    {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return "ERROR! Insufficient API level.";
        }

        String imagePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(locateAbsoluteFilePath(absoluteFilename));
            int mediaTotalFrames =  Integer.parseInt(metaRetriever.extractMetadata(32));
            if(mediaTotalFrames < imageIndex)
            {
                return "ERROR! Image index is out of bounds. Last frame index is: " + mediaTotalFrames;
            }
            Bitmap imageBitmap = metaRetriever.getImageAtIndex(imageIndex);
            metaRetriever.release();
            if(imageBitmap == null) {
                return imagePath;
            }
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "stillImage.png"));
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            imagePath = new File(context.getCacheDir(), "stillImage.png").getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "file://" + imagePath;
    }

    @SimpleFunction(description = "This method retrieves the primary image of the media content.")
    public String getPrimaryImage (String absoluteFilename)
    {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return "ERROR! Insufficient API level.";
        }
        String imagePath = "ERROR! Could not primary frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(locateAbsoluteFilePath(absoluteFilename));
            Bitmap imageBitmap = metaRetriever.getPrimaryImage();
            metaRetriever.release();
            if(imageBitmap == null) {
                return imagePath;
            }
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "primaryImage.png"));
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            imagePath = new File(context.getCacheDir(), "primaryImage.png").getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "file://" + imagePath;
    }

    @SimpleFunction(description = "Retrieve a video frame near a given timestamp scaled to a desired size. This method finds a representative frame close to the given time position by considering the given option if possible, and returns it as a bitmap with same aspect ratio as the source while scaling it so that it fits into the desired size of dst_width by dst_height. This is useful for generating a thumbnail for an input data source or just to obtain a scaled frame at the given time position.")
    public String getScaledFrameAtTime(String absoluteFilename,
                                       long timeUs,
                                       int option,
                                       int dstWidth,
                                       int dstHeight)
    {
        if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            return "ERROR! Insufficient API level.";
        }

        String scaledFramePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(locateAbsoluteFilePath(absoluteFilename));
            int mediaDurationMicroseconds =  Integer.parseInt(metaRetriever.extractMetadata(9)) * 1000;
            if(mediaDurationMicroseconds < timeUs)
            {
                return "ERROR! Frame is out of media duration.";
            }
            Bitmap frameBitmap = metaRetriever.getScaledFrameAtTime(timeUs,option,dstWidth,dstHeight);
            metaRetriever.release();
            if(frameBitmap == null) {
                return scaledFramePath;
            }
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "scaledFrame.png"));
            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            scaledFramePath = new File(context.getCacheDir(), "scaledFrame.png").getAbsolutePath();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "file://" + scaledFramePath;
    }

    public static String locateAbsoluteFilePath(String path)
    {
        if(path.contains("file:///")) {
            path = path.substring(7);
        }

        File file = new File(path);
        if (!file.exists()) {
            if (isDevelopment()) {
                File developmentAsset = new File(getAI2AssetsFolderPath(), path);
                if (developmentAsset.exists()) {
                    file = new File(developmentAsset.getAbsolutePath());
                } else {
                    Log.e("MetadataRetriever", "ERROR! COULD NOT FIND ASSET.");
                }
            } else {
                try {
                    file = new File(ExtractAssetToCache(path).getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    public static File ExtractAssetToCache(String file) throws java.io.IOException {
        File cacheFile = new File(context.getCacheDir(), file);
        try {
            try (InputStream inputStream = context.getAssets().open(file)) {
                try (FileOutputStream outputStream = new FileOutputStream(cacheFile)) {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Could not open asset/asset does not exist", e);
        }
        return cacheFile;
    }

    public static Boolean isDevelopment()
    {
        return context.getFilesDir().getAbsolutePath().contains("companion");
    }

    public static String getAI2AssetsFolderPath()
    {
        return new File(context.getExternalFilesDir(null).getAbsolutePath(), "/AppInventor/assets/").getAbsolutePath();
    }
}
