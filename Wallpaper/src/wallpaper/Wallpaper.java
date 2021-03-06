package wallpaper;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

//Uses the sun.jna Library to change the default background
public class Wallpaper {    
    public static interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32",User32.class,W32APIOptions.DEFAULT_OPTIONS);        
        boolean SystemParametersInfo (int one, int two, String s ,int three);         
    }
}