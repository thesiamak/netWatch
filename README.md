### netWatch   [![](https://jitpack.io/v/draxdave/netWatch.svg)](https://jitpack.io/#draxdave/netWatch)

Simple Android library to notify Android device network changes and react properly.

## Preview GIF
![netWatch - Simple Android library to notify Android device network changes and react properly](https://github.com/draxdave/netWatch/blob/master/gif/netwatch1.gif)



## Features: 
- Detects internet connection changes(Connect / Disconnect)
- Alerts user via a customizable status-bar notification message on connection loos / hides it on connection regain
- Asks for a direct connection(Ping) to a reliable CDN(changeable IP address) to make sure user connected to internet
- Checks connectivity periodically (duration calculated automatically)
- Handles app close and open states
- Changeable messages and drawables  


## Installation
 Step 1. Add this in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	} 
 ```
 
 Step 2. Add the dependency

 ```
dependencies {
	        implementation 'com.github.draxdave:netWatch:0.d.5'
	}
 ```
 


 
## Final speech 
Feel free to engage! 
This tiny library have a lot to do obviously . So help it if you liked it.
There is always better solutions and libraries so search more and you'll found a better code.   
Correct my mistakes and rewrite them in case you found some thing better or a more convenient way to handle issues.

### This library created for a personal project 
   
