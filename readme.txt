GLWallpaperService - Version 0.9.1
==================================
http://groups.google.com/group/glwallpaperservice

A library for making OpenGL Live Wallpapers for Android.

Provided as free open source software under the Apache License version 2.0.
Parts of this software are derived from code provided by the Android Open Source Project.

Getting started making your wallpaper
-------------------------------------
Follow these instructions to begin making your OpenGL wallpaper. You can also see the finished product of this tutorial at:
    https://github.com/markfguerra/GLWallpaperExample

The easiest way to use this project is by downloading the latest Jar file. You can find it here:
    https://github.com/downloads/markfguerra/GLWallpaperService/GLWallpaperService.jar

Once you have GLWallpaperService.jar, you can add it to your workspace. If needed, setup the SDK and create a new Android workspace as you would normally. Create a new project and use API level 7 or higher. API level 7 which is the necessary API level needed to run a Live Wallpaper. You won't need an Activity in your project if you don't want one. More information is available at http://developer.android.com/ 

Now that you have your Android project, you will need to add GLWallpaperService.jar to your classpath. Here is one way you can do that if you are using Eclipse. These steps were tested in Eclipse Galileo.
1. Create a new folder directly under your project folder. We'll call it lib/
2. Put GLWallpaperService.jar into the ProjectFolder/lib/ folder.
3. If Eclipse doesn't see the jar, refresh your project.
4. Right-click on your Android project. Choose Properties from the menu.
5. Under "Java Build Path", choose the "Libraries" tab.
6. Click on the "Add JARs...". Select GLWallpaperService.jar under ProjectFolder/lib and click ok. Click ok to get out of the Properties window.
7. This will automatically add a line to your .classpath file that reads something like this: 
    <classpathentry kind="lib" path="lib/GLWallpaperService.jar"/>

The next step would be to tell the Android system that you are making a Live Wallpaper. Place the following code inside your AndroidManifest.xml between the <Application> and </Application> tags

    <service android:label="@string/service_label" android:name=".MyWallpaperService"
        android:permission="android.permission.BIND_WALLPAPER">
        <intent-filter>
            <action android:name="android.service.wallpaper.WallpaperService" />
        </intent-filter>
        <meta-data android:name="android.service.wallpaper"
            android:resource="@xml/myglwallpaper" />
    </service>

android:name=".MyWallpaperService" corresponds to the name of a class you will create later on. You can name it what you want, but be consistent. 
android:resource="@xml/myglwallpaper" corresponds to an xml file that you will also create. Likewise, be consistent in your naming here too.

Also, add the following line to AndroidManifest.xml outside of the <Application> tag, but inside the <Manifest> tag:

    <uses-feature android:name="android.software.live_wallpaper" android:required="true" />

Next, create a folder and call it ProjectFolder/res/xml. Create a new file in there and call it myglwallpaper.xml. Place the following content inside.

    <?xml version="1.0" encoding="utf-8"?>
    <wallpaper xmlns:android="http://schemas.android.com/apk/res/android"
        android:description="@string/description"
    />

Add the following lines to res/values/strings.xml. Use whatever values you like.

    <string name="service_label">Name of your Wallpaper</string>
    <string name="description">Description of your wallpaper</string>

Now it is time to make your two main classes, the Service class and the Renderer class. 
To create the Renderer class, create a new class that we'll call MyRenderer. It implements the interface GLWallpaperService.Renderer and also has a method called release(). Here is an example.

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

Finally, create a class that extends GLWallpaperService. It behaves essentially the same as the Android class WallpaperService. We'll call it MyWallpaperService. Here is an example.

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

All that remains is to run the project on your phone or Emulator. In Eclipse, create a new run configuration of type "Android Application". Set the Project to the project you created, and click on the "Run" button. This will install the wallpaper on the phone or emulator.

To view the wallpaper, use the Android Live Wallpaper picker screen, and select your Wallpaper. If successful, the code above will display a green screen.

Congratulations, you're all ready to go! Now go forth and build an awesome OpenGL wallpaper.
