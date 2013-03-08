#Release Notes - GLWallpaperService


##Version 0.9.2
* You can now use a GLWallpaperService.Renderer to draw to a GLSurfaceView. The interface GLWallpaperService.Renderer, which used to be a stand-alone interface, now extends GLSurfaceView.Renderer. The method signatures remain the same. Although most projects will be unaffected, be mindful of whether this change would affect your project.


##Version 0.9.1
* Mitigate a bug that ceases animation and dumps many WaitForCondition(LockCondition) messages to logcat

##Version 0.9
* Initial release as copied from rbgrn.net

