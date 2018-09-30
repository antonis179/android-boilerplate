## Coming soon ##

- More info
- More boilerplate!
	- ViewModel
	- LiveData
	- WorkManager
	- NetworkCapabilities utility


## Build notes ##

### AAR generation ###

To generate an aar for every module in the project run one of the following commands at project root:
-   ```groovy
    ./gradlew assemble
    ```
-   ```groovy
    ./gradlew assembleDebug
    ```
-   ```groovy
    ./gradlew assembleRelease
    ```

The libraries will be stored in /root/genLibs

## Technologies / Architecture ##

### Architecture ###
	MVP is used as the architecture.
	
### Technologies ###
	- Kotlin
	- Dagger 2
	- RXJava / RXKotlin
	- Retrofit / Okhttp
	- Realm
	- Timber
	- Butterknife
	- Firebase Job Dispatcher
	- Event Bus
	- Crashlytics
	
	
	
	
## Setup ##

### Without Firebase ###

- Remove signing configuration from app level gradle
	```groovy
	signingConfigs {
			debug {
				def props = getPasswords()
				def loc = getAttribute(props, "${KEY_SIGNING_LOC}")
				def pwd = getAttribute(props, "${KEY_SIGNING_PWD}")
				def alias = getAttribute(props, "${KEY_SIGNING_ALIAS}")
				def aliasPwd = getAttribute(props, "${KEY_SIGNING_ALIAS_PWD}")
	
				storeFile file(loc)
				storePassword pwd
				keyAlias alias
				keyPassword aliasPwd
			}
	
			release {
				def props = getPasswords()
				def loc = getAttribute(props, "${RELEASE_KEY_SIGNING_LOC}")
				def pwd = getAttribute(props, "${RELEASE_KEY_SIGNING_PWD}")
				def alias = getAttribute(props, "${RELEASE_KEY_SIGNING_ALIAS}")
				def aliasPwd = getAttribute(props, "${RELEASE_KEY_SIGNING_ALIAS_PWD}")
	
				storeFile file(loc)
				storePassword pwd
				keyAlias alias
				keyPassword aliasPwd
			}
		}
	
	
		buildTypes {
			debug {
				//...
				signingConfig signingConfigs.debug
			}
			release {
				//...
				signingConfig signingConfigs.release
			}
		}
	```



- Remove all firebase implementation
	- From project level gradle:
	
		```groovy
		//Analytics
		classpath "com.google.gms:google-services:${FIREBASE}"
		classpath "com.google.firebase:firebase-plugins:${FIREBASE_PERFORMANCE}"
		```
	- From app level gradle
	
		```groovy
		apply plugin: 'com.google.firebase.firebase-perf'
		//...
		//Analytics
        implementation "com.google.firebase:firebase-core:${FIREBASE_CORE}"
        implementation "com.google.firebase:firebase-perf:${FIREBASE_PERF}"
        implementation "com.crashlytics.sdk.android:crashlytics:${CRASHLYTICS}"
	    //...
	    apply plugin: 'com.google.gms.google-services'
		```
	- Remove "FCMService" (from manifest as well)
	- Remove Firebase metadata from manifest
	```xml
	<application>
		<!-- FCM -->
	    <meta-data
	        android:name="com.google.firebase.messaging.default_notification_channel_id"
	        android:value="@string/notif_default_channel_id"/>
	
	    <meta-data
	        android:name="com.google.firebase.messaging.default_notification_icon"
	        android:resource="@android:drawable/ic_menu_info_details" />
	
	    <meta-data
	        android:name="com.google.firebase.messaging.default_notification_color"
	        android:resource="@color/accent" />
    </application>
	```
	
### With Firebase ###

- Setup a signing key through android studio
- Setup a firebase project and add google-services.json as per instructions
- Add "\_SENSITIVE_HIDE_FROM_GIT_" folder in project root
- Add password.properties in "\_SENSITIVE_HIDE_FROM_GIT_":
	```properties
	debug.key.location=
	debug.key.password=
	debug.key.alias=
	debug.key.alias.password=
	
	release.key.location=
	release.key.password=
	release.key.alias=
	release.key.alias.password=
	```

## Donations ##

So you liked my code and want to buy me coffee and pizza? That's awesome!

- BTC: **1JaV93dtuy13gw4u1wHeDvnzfJ2oAGFprF**
- Paypal: **https://www.paypal.me/neo179**