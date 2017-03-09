# Atmosphere
Rather simple audioplayer that provides a webserver, so it can be controlled via smartphone/tablet etc.

My **motivation** for this project is that I want to have a simple audioapplication that I can manage my atmospheric sounds with, that I use in my Dungeons and Dragons group. I'd like to have an application, that I can also or maybe especially controll with my smartphone, so I decided to implement a rather generic webinterface to the application.

For educational reasons I tried to implement the webserver myself as good as possible, but my knowledge in this matter is rather rusty - dont judge :>

## How to use
Just maintain a directory called _sounds_ next to the executable, containing any filehierarchy of .wav files. They will be automatically registered. 

Here is an example index page:
![alt tag](https://cloud.githubusercontent.com/assets/3928616/23765763/94781d18-0502-11e7-8a92-3b862a398f72.PNG)
The badges next to te libraries names tell you how many .wav files are inside that library. Your options here are at the moment:

1. click a library      -> opens the library
2. click a play button  -> loops ALL the files inside the respective library (very good to for example play a prepared _Forest_ ambient
3. click a pause button -> stops ALL the files inside the respective library
4. click a loop button  -> plays ALL the files inside the respective library _one after the other_ over and over again

Here is an example Library page:
![alt tag](https://cloud.githubusercontent.com/assets/3928616/23765762/9477eff0-0502-11e7-9e27-1d940f5c25e5.PNG)
Your options here are at the moment:

1. click a play button  -> plays the .wav file
2. click a pause buton  -> pauses the .wav file
3. click a loop button  -> loop the .wav file
4. cilck the back button on the top left -> return to the index page
5. click a .wav file    -> just like clicking play button


## Technical details

I am using the **native AudioSystem** that is provided by java, to reduce additional dependencies
however this only allows me to use **.wav files**

The executable searches and traverses through a directory called "sounds" next to it (will be modifiable somewhen)
it **generates html files**, so whatever kind of sounds someone wants to manage with Atmosphere, just need to be placed inside the sounds folder.

subfolders are registered and stored as "libs"

Feel free to **fork/contribute**
have fun
stay sharp
make Donald Drumpf again!
