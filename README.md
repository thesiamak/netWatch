# netWatch   [![](https://jitpack.io/v/draxdave/netWatch.svg)](https://jitpack.io/#draxdave/netWatch)

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
	        implementation 'com.github.draxdave:netWatch:0.d.6'
	}
 ```

Step 3. Register NetWatch

Add below code inside base activity or Application class

```java
 NetWatch.builder(activity)
                .setIcon(R.drawable.ic_signal_wifi_off)
                .setCallBack(new NetworkChangeReceiver_navigator() {
                    @Override
                    public void onConnected(int source) {
                        // do some thing
                    }

                    @Override
                    public void onDisconnected() {
			// do some other thing
                    }
                })
                .build();

```

## Available options
- setIcon : Statusbar notification drawable graphic
- setCallBack : onConnect and onDisconnect callbacks
- setMessage : Statusbar notification message
- setNotificationCancelable : Statusbar notification cancelable
- setNotificationEnabled : Show statusbar notification on disconnect from internet
- setNotificationBuilder : Create your own statusbar notification to show
- unregister : disable NetWatch


## Extra configs
### Target server IP address
Add following line inside string.xml file :

    <string name="netwatch_target_ping_server_ip_add">8.8.8.8</string>
    
### Disconnect message text
Add following line inside string.xml file :

    <string name="netwatch_lost_connection">No internet connection</string>




## Final speech 
Feel free to engage! 
This tiny library have a lot to do obviously . So help it if you liked it.
There is always better solutions and libraries so search more and you'll found a better code.   
Correct my mistakes and rewrite them in case you found some thing better or a more convenient way to handle issues.

### This library created for a personal project but you can use any part of it as you wish .
   
