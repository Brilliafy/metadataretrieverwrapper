
package com.michaelam.metadataretrieverwrapper;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import android.content.Context;


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
    public String ExtractMediaMetaData(String fullFileName, int keyConstant)  {
        android.media.MediaMetadataRetriever metaRetriever = new android.media.MediaMetadataRetriever();
            metaRetriever.setDataSource(fullFileName);
            String metaData = metaRetriever.extractMetadata(keyConstant);
            metaRetriever.release();
            return metaData;
    }
}
