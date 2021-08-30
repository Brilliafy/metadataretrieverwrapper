
package com.michaelam.metadataretrieverwrapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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



@DesignerComponent(
        version = MetadataRetriever.VERSION,
        description =
                "An efficient extension for extracting media metadata.",
        category = ComponentCategory.EXTENSION, nonVisible = true,
        iconName = "https://i.ibb.co/RTxMLN4/icon.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.READ_EXTERNAL_STORAGE")
public class MetadataRetriever extends AndroidNonvisibleComponent {
    public static final int VERSION = 1;
    private static Context context;

    public MetadataRetriever(ComponentContainer container) {
        super(container.$form());
        context = container.$context();
    }

    @SimpleFunction(description = " This method retrieves the meta data value associated with the keyCode. The keyCode currently supported is listed in the official android MetadataRetriever class page as METADATA_XXX constants. With any other value, it returns a null pointer.")
    public String ExtractMediaMetaData(String fullFileName, int keyCode) {
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        String metaData = "Error! Could not retrieve metadata.";
        try
        {
            metaRetriever.setDataSource(LocateAbsoluteFilePath(fullFileName));
            metaData = metaRetriever.extractMetadata(keyCode);
            metaRetriever.release();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return metaData;
    }

//    @SimpleFunction(description = " This method retrieves the meta data value associated with the keyCode. The keyCode currently supported is listed in the official android MetadataRetriever class page as METADATA_XXX constants. With any other value, it returns a null pointer.")
//    public String ExtractMediaMetaData(com.google.appinventor.components.runtime.File file, int keyCode) {
//        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
//        String metaData = "Error! Could not retrieve metadata.";
//        try
//        {
//            metaRetriever.setDataSource(file.);
//            metaData = metaRetriever.extractMetadata(keyCode);
//            metaRetriever.release();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return metaData;
//    }

    @SimpleFunction(description = "This method finds the optional graphic or album/cover art associated associated with the data source. If there are more than one pictures, (any) one of them is returned.")
    public String getEmbeddedPicture(String fullFileName)
    {
        String embeddedImagePath = "ERROR! Could not retrieve embedded image.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(LocateAbsoluteFilePath(fullFileName));
            byte[] embeddedImageData = metaRetriever.getEmbeddedPicture();
            metaRetriever.release();
            Bitmap embeddedImageBitmap = BitmapFactory.decodeByteArray(embeddedImageData, 0, embeddedImageData.length);
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "embeddedImage.png"));
            embeddedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return embeddedImagePath;
    }

//    @SimpleFunction(description = "This method finds the optional graphic or album/cover art associated associated with the data source. If there are more than one pictures, (any) one of them is returned.")
//    public String getEmbeddedPicture(File file)
//    {
//        String embeddedImagePath = "ERROR! Could not retrieve embedded image.";
//        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
//        try {
//            metaRetriever.setDataSource(file.getAbsolutePath());
//            byte[] embeddedImageData = metaRetriever.getEmbeddedPicture();
//            metaRetriever.release();
//            Bitmap embeddedImageBitmap = BitmapFactory.decodeByteArray(embeddedImageData, 0, embeddedImageData.length);
//            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "embeddedImage.png"));
//            embeddedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return embeddedImagePath;
//    }

    @SimpleFunction(description = "This method retrieves a video frame by its index.")
    public String getFrameAtIndex (String fullFileName, int frameIndex)
    {
        String videoFramePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(LocateAbsoluteFilePath(fullFileName));
            Bitmap frameBitmap = metaRetriever.getFrameAtIndex(frameIndex);
            metaRetriever.release();
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return videoFramePath;
    }

//    @SimpleFunction(description = "This method retrieves a video frame by its index.")
//    public String getFrameAtIndex (File file, int frameIndex)
//    {
//        String videoFramePath = "ERROR! Could not retrieve video frame.";
//        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
//        try {
//            metaRetriever.setDataSource(file.getAbsolutePath());
//            Bitmap frameBitmap = metaRetriever.getFrameAtIndex(frameIndex);
//            metaRetriever.release();
//            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
//            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return videoFramePath;
//    }

    @SimpleFunction(description = "This method retrieves a video frame by its index.")
    public String getFrameAtTime (String fullFileName, long timeUs)
    {
        String videoFramePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(LocateAbsoluteFilePath(fullFileName));
            Bitmap frameBitmap = metaRetriever.getFrameAtTime(timeUs);
            metaRetriever.release();
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return videoFramePath;
    }
//
//    @SimpleFunction(description = "This method retrieves a video frame by its index.")
//    public String getFrameAtTime (File file, long timeUs)
//    {
//        String videoFramePath = "ERROR! Could not retrieve video frame.";
//        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
//        try {
//            metaRetriever.setDataSource(file.getAbsolutePath());
//            Bitmap frameBitmap = metaRetriever.getFrameAtTime(timeUs);
//            metaRetriever.release();
//            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
//            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return videoFramePath;
//    }

    @SimpleFunction(description = "This method retrieves a video frame by its index.")
    public String getFrameAtTime (String fullFileName, long timeUs, int option)
    {
        String videoFramePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(LocateAbsoluteFilePath(fullFileName));
            Bitmap frameBitmap = metaRetriever.getFrameAtTime(timeUs, option);
            metaRetriever.release();
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return videoFramePath;
    }

//    @SimpleFunction(description = "This method retrieves a video frame by its index.")
//    public String getFrameAtTime (File file, long timeUs, int option)
//    {
//        String videoFramePath = "ERROR! Could not retrieve video frame.";
//        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
//        try {
//            metaRetriever.setDataSource(file.getAbsolutePath());
//            Bitmap frameBitmap = metaRetriever.getFrameAtTime(timeUs, option);
//            metaRetriever.release();
//            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "videoFrame.png"));
//            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return videoFramePath;
//    }

    @SimpleFunction(description = "This method retrieves a still image by its index.")
    public String getImageAtIndex (String fullFileName, int imageIndex)
    {
        String imagePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(LocateAbsoluteFilePath(fullFileName));
            Bitmap imageBitmap = metaRetriever.getImageAtIndex(imageIndex);
            metaRetriever.release();
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "stillImage.png"));
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

//    @SimpleFunction(description = "This method retrieves a still image by its index.")
//    public String getImageAtIndex (File file, int imageIndex)
//    {
//        String imagePath = "ERROR! Could not retrieve video frame.";
//        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
//        try {
//            metaRetriever.setDataSource(file.getAbsolutePath());
//            Bitmap imageBitmap = metaRetriever.getImageAtIndex(imageIndex);
//            metaRetriever.release();
//            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "stillImage.png"));
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imagePath;
//    }

    @SimpleFunction(description = "This method retrieves the primary image of the media content.")
    public String getPrimaryImage (String fullFileName)
    {
        String imagePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(LocateAbsoluteFilePath(fullFileName));
            Bitmap imageBitmap = metaRetriever.getPrimaryImage();
            metaRetriever.release();
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "primaryImage.png"));
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }
//
//    @SimpleFunction(description = "This method retrieves the primary image of the media content.")
//    public String getPrimaryImage (File file)
//    {
//        String imagePath = "ERROR! Could not retrieve video frame.";
//        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
//        try {
//            metaRetriever.setDataSource(file.getAbsolutePath());
//            Bitmap imageBitmap = metaRetriever.getPrimaryImage();
//            metaRetriever.release();
//            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "primaryImage.png"));
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imagePath;
//    }

    @SimpleFunction(description = "Retrieve a video frame near a given timestamp scaled to a desired size. This method finds a representative frame close to the given time position by considering the given option if possible, and returns it as a bitmap with same aspect ratio as the source while scaling it so that it fits into the desired size of dst_width by dst_height. This is useful for generating a thumbnail for an input data source or just to obtain a scaled frame at the given time position.")
    public String getScaledFrameAtTime(String fullFileName,
                                       long timeUs,
                                       int option,
                                       int dstWidth,
                                       int dstHeight)
    {
        String scaledFramePath = "ERROR! Could not retrieve video frame.";
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        try {
            metaRetriever.setDataSource(LocateAbsoluteFilePath(fullFileName));
            Bitmap frameBitmap = metaRetriever.getScaledFrameAtTime(timeUs,option,dstWidth,dstHeight);
            metaRetriever.release();
            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "scaledFrame.png"));
            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return scaledFramePath;
    }

//    @SimpleFunction(description = "Retrieve a video frame near a given timestamp scaled to a desired size. This method finds a representative frame close to the given time position by considering the given option if possible, and returns it as a bitmap with same aspect ratio as the source while scaling it so that it fits into the desired size of dst_width by dst_height. This is useful for generating a thumbnail for an input data source or just to obtain a scaled frame at the given time position.")
//    public String getScaledFrameAtTime(File file,
//                                       long timeUs,
//                                       int option,
//                                       int dstWidth,
//                                       int dstHeight)
//    {
//        String scaledFramePath = "ERROR! Could not retrieve video frame.";
//        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
//        try {
//            metaRetriever.setDataSource(file.getAbsolutePath());
//            Bitmap frameBitmap = metaRetriever.getScaledFrameAtTime(timeUs,option,dstWidth,dstHeight);
//            metaRetriever.release();
//            FileOutputStream out = new FileOutputStream(new File(context.getCacheDir(), "scaledFrame.png"));
//            frameBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return scaledFramePath;
//    }


    public static String LocateAbsoluteFilePath(String filepath)
    {
        File file = new File(filepath);
        if (!file.exists()) {
            if (isDevelopment()) {
                File developmentAsset = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/AppInventor/assets/", filepath);
                if (developmentAsset.exists()) {
                    file = new File(developmentAsset.getAbsolutePath());
                } else {
                    Log.e("MetadataRetriever", "ERROR! COULD NOT FIND ASSET.");
                }
            } else {
                try {
                    file = new File(ExtractAssetToCache(filepath).getAbsolutePath());
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
}
