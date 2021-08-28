
package com.michaelam.metadataretrieverwrapper;

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

    @SimpleFunction(description = "Retrieves media information")
    public String ExtractMediaMetaData(String fullFileName, int keyConstant) {
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
        File file = new File(fullFileName);
        if (!file.exists() && !file.getAbsolutePath().contains("/")) {
            if (isDevelopment()) {
                File developmentAsset = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/AppInventor/assets/", fullFileName);
                if (developmentAsset.exists()) {
                    file = new File(developmentAsset.getAbsolutePath());
                } else {
                    Log.e("AssetExtractor", "ERROR! COULD NOT FIND ASSET.");
                }
            } else {
                try {
                    file = new File(ExtractAssetToCache(fullFileName).getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
            metaRetriever.setDataSource(file.getAbsolutePath());
            String metaData = metaRetriever.extractMetadata(keyConstant);
            metaRetriever.release();
            return metaData;
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

    public Boolean isDevelopment()
    {
        return context.getFilesDir().getAbsolutePath().contains("companion");
    }
}
