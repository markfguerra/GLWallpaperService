#GLWallpaperService - Version 0.9.2
 
[Google Group](http://groups.google.com/group/glwallpaperservice)

[Repository](https://github.com/GLWallpaperService/GLWallpaperService)

A library for making OpenGL Live Wallpapers for Android.

Provided as free open source software under the Apache License version 2.0.
Parts of this software are derived from code provided by the Android Open Source Project.

##Getting started making your wallpaper


###Get the library
The easiest way to use this project is by downloading the [latest Jar file](https://github.com/downloads/markfguerra/GLWallpaperService/GLWallpaperService.jar)

Once you have GLWallpaperService.jar, you can add it to your workspace:

* If needed, setup the [Android SDK](http://developer.android.com/sdk/) and/or create a new Android workspace as you would normally
* Create a new Android project using API level 7 or higher
* You don't need an Activity in your project if you don't want one, but it can be nice to give users an icon to access your wallpaper settings

###Add the library to your project
Now that you have your Android project, you will need to add GLWallpaperService.jar to your classpath. Here is one way you can do that if you are using Eclipse. These steps were tested in Eclipse Galileo.

1. Create a new folder directly under your project folder called libs/
2. Put GLWallpaperService.jar into the ProjectFolder/libs/ folder.
3. If Eclipse doesn't see the jar, refresh your project.
4. Right-click on your Android project. Choose Properties from the menu.
5. Under "Java Build Path", choose the "Libraries" tab.
6. Click on the "Add JARs...". Select GLWallpaperService.jar under ProjectFolder/libs/ and click ok. Click ok to get out of the Properties window.
7. This will automatically add a line to your .classpath file that reads something like this: 
    <classpathentry kind="lib" path="libs/GLWallpaperService.jar"/>

###Configure AndroidManifest.xml
The next step would be to tell the Android system that you are making a Live Wallpaper. Place the following code inside your AndroidManifest.xml between the `<Application>` and `</Application>` tags

    <service android:label="@string/service_label" android:name=".MyWallpaperService"
        android:permission="android.permission.BIND_WALLPAPER">
        <intent-filter>
            <action android:name="android.service.wallpaper.WallpaperService" />
        </intent-filter>
        <meta-data android:name="android.service.wallpaper"
            android:resource="@xml/myglwallpaper" />
    </service>

* `android:name=".MyWallpaperService"` corresponds to the name of a class you will create later on. You can name it what you want, but be consistent. 
* `android:resource="@xml/myglwallpaper"` corresponds to an xml file that you will also create. Likewise, be consistent in your naming here too.

Also, add the following line to AndroidManifest.xml outside of the `<Application>` tag, but inside the `<Manifest>` tag:

    <uses-feature android:name="android.software.live_wallpaper" android:required="true" />

Next, create a folder and call it ProjectFolder/res/xml. Create a new file in there and call it myglwallpaper.xml. Place the following content inside.

    <?xml version="1.0" encoding="utf-8"?>
    <wallpaper xmlns:android="http://schemas.android.com/apk/res/android"
        android:description="@string/description"
    />

Add the following lines to res/values/strings.xml. Use whatever values you like.

    <string name="service_label">Name of your Wallpaper</string>
    <string name="description">Description of your wallpaper</string>

###Renderer Implementation
Now it is time to make your two main classes, the Service class and the Renderer class. 
To create the Renderer class, create a new class that we'll call `MyRenderer`. It implements the interface `GLWallpaperService.Renderer` and also has a method called `release()`. Here is an example.

    import net.rbgrn.android.glwallpaperservice.*;
    
    public class MyRenderer implements GLWallpaperService.Renderer {
        public void onDrawFrame(GL10 gl) {
            // Your rendering code goes here
            
            gl.glClearColor(0.2f, 0.4f, 0.2f, 1f);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        }
        
        public void onSurfaceChanged(GL10 gl, int width, int height) {
        }
        
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        }
        
        /**
         * Called when the engine is destroyed. Do any necessary clean up because
         * at this point your renderer instance is now done for.
         */
        public void release() {
        }
    }

###Service Implementation
Finally, create a class that extends `GLWallpaperService`. It behaves essentially the same as the Android class [`WallpaperService`](http://developer.android.com/reference/android/service/wallpaper/WallpaperService.html). We'll call it `MyWallpaperService`. Here is an example.

    import net.rbgrn.android.glwallpaperservice.*;
    
    // Original code provided by Robert Green
    // http://www.rbgrn.net/content/354-glsurfaceview-adapted-3d-live-wallpapers
    public class MyWallpaperService extends GLWallpaperService {
        public MyWallpaperService() {
            super();
        }
        
        public Engine onCreateEngine() {
            MyEngine engine = new MyEngine();
            return engine;
        }
        
        class MyEngine extends GLEngine {
            MyRenderer renderer;
            public MyEngine() {
                super();
                // handle prefs, other initialization
                renderer = new MyRenderer();
                setRenderer(renderer);
                setRenderMode(RENDERMODE_CONTINUOUSLY);
            }
            
            public void onDestroy() {
                super.onDestroy();
                if (renderer != null) {
                    renderer.release();
                }
                renderer = null;
            }
        }
    }

###Running the app
All that remains is to run the project on your phone or Emulator. In Eclipse, create a new run configuration of type "Android Application". Set the Project to the project you created, and click on the "Run" button. This will install the wallpaper on the phone or emulator.

To view the wallpaper, use the Android Live Wallpaper picker screen, and select your Wallpaper. If successful, the code above will display a green screen.

Congratulations, you're all ready to go! Now go forth and build an awesome OpenGL wallpaper.
