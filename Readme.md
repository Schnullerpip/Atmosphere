# Atmosphere
Rather simple audioplayer that provides a webserver, so it can be controlled via smartphone/tablet etc.

My **motivation** for this project is that I want to have a simple audioapplication that I can manage my atmospheric sounds with, that I use in my Dungeons and Dragons group. I'd like to have an application, that I can also or maybe especially controll with my smartphone, so I decided to implement a rather generic webinterface to the application.

For educational reasons I tried to implement the webserver myself as good as possible, but my knowledge in this matter is rather rusty - dont judge :>

I am using the **native AudioSystem** that is provided by java, to reduce additional dependencies
however this only allows me to use **.wav files**

The executable searches and traverses through a directory called "sounds" next to it (will be modifiable somewhen)
it **generates html files**, so whatever kind of sounds someone wants to manage with Atmosphere, just need to be placed inside the sounds folder.

subfolders are registered and stored as "libs"

Feel free to **fork/contribute**
have fun
stay sharp
make Donald Drumpf again!
