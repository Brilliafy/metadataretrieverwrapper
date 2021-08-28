# Metadata Retriever Wrapper

An AI2 Extension to wrap the MediaMetadataRetriever class.

## Why was it made

During the development of my 'roll dice!' app, I encountered a shocking issue... I couldn't animate the dice to roll precisely the duration of the sound effect, and there was no other extension available in the community to accomplish that same functionality I was looking for. **SO I MADE MY OWN!**

You could unmistakenly suggest that I could just— hardcode the duration of the specific audio file, to the timer of the animation. **..BUT WHERE'S THE FUN IN THAT?!?**

## Documentation

There is currently one main function, the `ExtractMediaData` wrapper method, which consists for two arguments.

The first one, expects the absolute path of a file, yet deals with assets just fine! Thanks to the automatic debugging detection, and an abstract of our AssetExtractor project, which you can visit [here](https://github.com/Brillianware/AssetExtractor/).

The second argument, anticipates an interger. More precisely, a constant. Since it is just wrapping the MediaMetadataRetriever.extractMetadata method call, you can find all the coresponding constants with an accurate description of each and every one of them in this [article](https://developer.android.com/reference/android/media/MediaMetadataRetriever#constants_1].

## Closure

For any inquiries, [contact me](https://github.com/Brillianware).

### Thank you!

