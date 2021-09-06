# Metadata Retriever Wrapper

An AI2 Extension to wrap the MetadataRetriever class.


*Reference:* https://developer.android.com/reference/android/media/MediaMetadataRetriever


## Why was it made

During the development of my *'roll dice!'* app, I encountered a shocking issue... I couldn't animate the dice to roll precisely the duration of the sound effect, and I was not aware of any other extension available in the community to accomplish that same functionality I was looking for. **SO I MADE MY OWN!**


You could unmistakenly suggest that I could just have— hard-coded the duration of the specific audio file, to the timer of the animation. **..BUT WHERE'S THE FUN IN THAT?!?**


## Documentation

#### This extension currently wraps essentially all functions of the MetadataRetriever class, including:

1. [MetadataRetriever.extractMetadata](https://developer.android.com/reference/android/media/MediaMetadataRetriever#extractMetadata(int))(String absoluteFilename, int keyCode)
2. [MetadataRetriever.getEmbeddedPicture](https://developer.android.com/reference/android/media/MediaMetadataRetriever#getEmbeddedPicture())(String absoluteFilename)
3. [getFrameAtIndex](https://developer.android.com/reference/android/media/MediaMetadataRetriever#getFrameAtIndex(int))(String absoluteFilename, int frameIndex)
4. [getFrameAtTime](https://developer.android.com/reference/android/media/MediaMetadataRetriever#getFrameAtTime(long))(String absoluteFilename, long timeUs)
5. [getFrameAtTimeOptionOverload](https://developer.android.com/reference/android/media/MediaMetadataRetriever#getFrameAtTime(long,%20int))(String absoluteFilename, long timeUs, int option)
6. [getImageAtIndex](https://developer.android.com/reference/android/media/MediaMetadataRetriever#getImageAtIndex(int))(String absoluteFilename, int imageIndex)
7. [getPrimaryImage](https://developer.android.com/reference/android/media/MediaMetadataRetriever#getPrimaryImage())(String absoluteFilename)
8. [getScaledFrameAtTime](https://developer.android.com/reference/android/media/MediaMetadataRetriever#getScaledFrameAtTime(long,%20int,%20int,%20int))(String absoluteFilename, long timeUs, int option, int dstWidth, int dstHeight)


### Method return

All functions **return type `String`. They return the absolute file path of the new extracted image** in cache, aside from the function *`MetadataRetriever.extractMetadata`*, which returns metadata. Take for instance: 


- *`MetadataRetriever.extractMetadata(METADATA_KEY_DURATION)`* or preferably *`MetadataRetriever.extractMetadata(9)`*, returns the duration in milliseconds, such as *`3125`*
- *`MetadataRetriever.extractMetadata(METADATA_KEY_TITLE)`* or preferably *`MetadataRetriever.extractMetadata(7)`*, returns the title of media, if available, such as *"My title"*



### Min API - Errors

Each function prerequisites a unique API level. Seeing that these functions are virtual methods, granted that one of them was invoked on a device with an older platform version than mandatory, instead of returning results, the method returns an error message. The current error message is: *`"ERROR! Insufficient API level."`*


On the occasion that the function fails, it generally returns an error message with a clause. You can handle them by checking if the string returned contains *"ERROR!"*, as it is widely used as a universal error message prefix all across the extension. **Setting a picture component as the error message, causes a crash! Ensure you scan the returned string to handle the error in your code, rather than rawly assigning the path of the image to a MetadataRetriever function.**


### Method Arguments - IO

**The first argument**, is usually the argument `absoluteFilename`, which is much self-explanatory. Unlike AI2, *`file:///`* is a redundant prefix; the extension's functions execute without interference regardless of the occurrence of the prefix. Be that as it may, all paths returned from the functions, emphatically have to use and are returned with the *`file:///`* prefix.  Furthermore, asset utilisation is approved, despite if the application is running in development mode, thanks to the automatic debugging detection, which is an abstract of the AssetExtractor project, which you can visit [here](https://github.com/Brillianware/AssetExtractor/).


The file assigned can differ in file formats, since it is wrapping an Android pre-defined method, in accordance to your Android API level, explained thoroughly [here](https://developer.android.com/guide/topics/media/media-formats#audio-formats) for audio, and [here](https://developer.android.com/guide/topics/media/media-formats#video-formats) for video.


All methods require an absolute file path of the target media, as of the MetadataRetriever.setDataSource function, for it is necessary for relatively every method within the MetadataRetriever class.


**Any other arguments required,** are referenced in great detail inside the [MediaMetadataRetriever Class Developer Reference](https://developer.android.com/reference/android/media/MediaMetadataRetriever#public-methods_1).



## Downloads

Click [here](https://github.com/Brilliafy/metadataretrieverwrapper/raw/master/com.michaelam.metadataretrieverwrapper.aix) to download the extension.

Click [here](https://github.com/Brilliafy/metadataretrieverwrapper/blob/master/MetadataRetrieverExample.aia) to download the sample project.


## Closure
For any inquiries, [contact me](https://github.com/Brilliafy).


### Thank you!
