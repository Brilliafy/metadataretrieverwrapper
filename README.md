# audiometadataretriever

An AI2 Extension to retrieve metadata information from an audio file.

## Why was it made

During the development of my 'roll dice!' app, I encountered a shocking issue... I couldn't animate the dice to roll precisely the duration of the sound effect, and there was no other extension available in the community to accomplish that same functionality I was looking for. **SO I MADE MY OWN!**

You could unmistakenly suggest that I could just— hardcode the duration of the specific audio file, to the timer of the animation. **..BUT WHERE'S THE FUN IN THAT?!?**

## Alteration

My objective was only extracting the duration of my audio file, therefore, do not anticipate every and each attribute to be available for retrieval using this extension. —However, you could quite conveniently duplicate my `GetAudioDuration(String fileName)` function, and replace the argument of the nested function `metaRetriever.extractMetadata(arg)`, according to this [article](https://developer.android.com/reference/android/media/MediaMetadataRetriever) (do not forget to build the extension in the end!).   

## Documentation

Like in the function description, accessing files for the `GetAudioDuration(String fileName)` function, is fairly easy, due to my **Built-in file handling** *(I am well-aware that I should have made a separate IO library). 

You can use no prefix for the fullpath of a file (relative directory is root),

you can use the prefix '/' to access a file from the sd card (relative directory is the root of the sd card),

you can use the prefix '//' to access a file from the AI2 development folder (relative directory is quite self-explanatory),

you can use the prefix '///' to access a file from the relative path (also quite self-explanatory),

and finnaly, the prefix '@' to access an asset from the current app (file previously uploaded to the project media list)!


## Examples

`storage/emulated/0/DCIM/foo.jpeg` returns: `/storage/emulated/0/DCIM/foo.jpeg`

`/Downloads/foo.mp4` returns: `/mnt/sdcard/Downloads/foo.mp4`

`//foo.png` returns: `storage/emulated/0/AppInventor/assets/foo.png`

`file:///foo.txt` returns: `/data/data/<app packageName>/foo.txt`

`@foo.png` returns foo.png from within the app

## Closure

For any inquiries, [contact me](https://github.com/Brillianware).

### Thank you!

