
package com.michaelam.audiometadataretriever;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import java.io.File;
import java.io.IOException;
import android.content.Context;
import java.io.InputStream;
import java.io.FileOutputStream;


@DesignerComponent(
        version = MetadataRetriever.VERSION,
        description =
                "An efficient extension for retrieving media audiodata.",
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

    @SimpleFunction(description = "Retrieves the length of the audio file in miliseconds from a file")
    public String GetAudioDuration(String fileName) {
        String duration;
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        metaRetriever.setDataSource(GetPath(fileName));
        duration = metaRetriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
        metaRetriever.release();
        return duration;
    }

@SimpleFunction(description = "return a file's filename, using prefixes: '' for fullpath, '/' for sd path, '//' for app inventor path, '///' for relative path, and '@' for asset")
public String GetPath(String file){
    return defineDir(file);
}

    String defineDir(String path)
    {


        if(path.startsWith("//") || context.getFilesDir().getAbsolutePath().contains("aicompanion"))
        {
            int characters = (path.startsWith("//") ? 1 : 0) +
                    (context.getFilesDir().getAbsolutePath().contains("aicompanion") ? 1 : 0);

            return "/storage/emulated/0/AppInventor/assets/"+path.substring(characters);
        }else if(path.startsWith("/")){
                return "/mnt/sdcard"+path;
            }else if(path.startsWith("file:///")) {
                    return path.substring(7);
                }
                else if(path.startsWith("@"))
                {
                    try
                    {
                        return getAssetFile(path.substring(1)).getAbsolutePath();
                    }
                    catch(java.io.IOException e)
                    {
                        return "null";
                    }
                }
                else return path;
            }


    public static File getAssetFile(String file) throws java.io.IOException {
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
            throw new IOException("Could not open resource", e);
        }
        return cacheFile;
    }
}
