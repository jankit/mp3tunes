# HOWTO Submit Log Information #

Occasionally you might run into problems. MP3tunes has a limited selection of physical devices to test in our QA lab so we are unable to test on all Android phones. We might be able to get more information about the problem by reviewing a log file. But, to do that, you have to install the Android SDK and capture a log.

You can get the Android SDK for free: http://developer.android.com/sdk/index.html

After it's installed, you can look inside the "tools" directory and you'll see an application called "ddms". This is the Dalvik Debug Monitor Server:

http://developer.android.com/guide/developing/tools/ddms.html

To use it you can plug in a USB cable to your device. Then, go to Settings > Applications > Development and check the box that says "USB debugging".

Then, launch ddms and you should see an icon for your phone appear in the upper left. When you highlight the icon it will show you a log file in the pane below.

If you launch the MP3tunes application while running ddms it will keep a log of what the device is doing. This might give us some information about what is going wrong.

To send it, you can highlight all the text from the log and click the disc icon (save log). Just give it a name like "log.txt" and save it to your hard drive. Then, you can attach it to [email](mailto:mp3tunes.android@gmail.com) and we will review and try to fix any bugs it might reveal.

## DDMS Screenshot: ##

![http://i133.photobucket.com/albums/q41/mp3tunes/android-log-file-screenshot.png](http://i133.photobucket.com/albums/q41/mp3tunes/android-log-file-screenshot.png)