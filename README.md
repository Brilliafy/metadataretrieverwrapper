# Metadata Retriever Wrapper

An AI2 Extension to wrap the MetadataRetriever class.

## Why was it made

During the development of my 'roll dice!' app, I encountered a shocking issue... I couldn't animate the dice to roll precisely the duration of the sound effect, and there was no other extension available in the community to accomplish that same functionality I was looking for. **SO I MADE MY OWN!**

You could unmistakenly suggest that I could just— hardcode the duration of the specific audio file, to the timer of the animation. **..BUT WHERE'S THE FUN IN THAT?!?**

## Documentation

There is currently one main function in this extension: The `ExtractMediaData` wrapper block, which consists for two arguments.

**The first argument**, expects the absolute path of a media file, yet deals with assets just fine! Thanks to the automatic debugging detection, which is an abstract of the AssetExtractor project, which you can visit [here](https://github.com/Brillianware/AssetExtractor/).

The file can differ in file formats since it is wrapping an Android pre-defined method, in accordance to your Android API level, explained thoroughly [here](https://developer.android.com/guide/topics/media/media-formats#audio-formats) for audio, and [here](https://developer.android.com/guide/topics/media/media-formats#video-formats) for video.

**The second argument**, anticipates an integer. More precisely, a constant. Since it is just wrapping the MediaMetadataRetriever.extractMetadata method call, you can find all the corresponding constants with an accurate description of each one of them in this [article](https://developer.android.com/reference/android/media/MediaMetadataRetriever#constants_1).

The function can sometimes return null. This could be dealt easily with the `is a string? thing` block. Detailed example can be found on the [original extension post](https://community.appinventor.mit.edu/t/under-evaluation-free-metadata-retriever-wrapper-extract-information/40042). 

## Download

Click [here](https://community.appinventor.mit.edu/uploads/short-url/q2Vjf09dJNfhc4ZJmB4nNdpKOb6.aix) to download.

## Closure

For any inquiries, [contact me](https://github.com/Brillianware).

### Thank you!
