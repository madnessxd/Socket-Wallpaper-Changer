package wallpaper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImg {
    public static String DownloadImg(String urlStr) {
        try{
            URL url = new URL(urlStr);
            
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection(); 
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.76"); 
            
            InputStream in = httpcon.getInputStream();
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while ((n=in.read(buf)) != -1)
            {
               out.write(buf, 0, n);
            }
            out.close();
            in.close();
            
            byte[] response = out.toByteArray();
            File file = new File(System.getenv("APPDATA") + "/Not a Virus");
            if (!file.exists()) {
                file.mkdir();
            }

            String loc = "/Not a Virus/notAWallpaper.jpg";
            
            if(urlStr.contains(".png")){
                loc = "/Not a Virus/notAWallpaper.png";
            }
            
            FileOutputStream fos = new FileOutputStream(System.getenv("APPDATA") + loc);
            fos.write(response);
            fos.close();
            
            try{
                String wallpaper_file = System.getenv("APPDATA") + loc;
                Wallpaper.User32.INSTANCE.SystemParametersInfo(0x0014, 0, wallpaper_file, 1);
            } catch(UnsatisfiedLinkError e){
                System.err.println(e);
                return e.toString();
            }
        } catch(Exception e){
            System.err.println(e);
            return e.toString();
        }
        return "-DONE- =^_^=";
    }
}
