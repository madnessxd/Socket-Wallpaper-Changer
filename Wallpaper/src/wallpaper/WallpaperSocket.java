
package wallpaper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
Program that enables it to change your wallpaper through a socket connection.
This means that you could change the wallpaper by using a different device.
Just connect to port 23 in a telnet based client.
*/
public class WallpaperSocket {
    public static void main(String[] args) {
        try {
            //Open port 23 and keep the program alive in a while loop.
            ServerSocket serverSocket = new ServerSocket(23);
            while(true){
                System.out.println("Waiting for connection");
                Socket socket = serverSocket.accept();
                
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                
                try{
                    System.out.println("Connection opened");
                    buf.write("---------------" + "\r\n");
                    buf.write("|  Wallpaper  |" + "\r\n");
                    buf.write("| Version 1.0 |" + "\r\n");
                    buf.write("---------------" + "\r\n");
                    buf.write("Yo bitches! Input command" + "\r\n");
                    buf.write("Type help to show all commands ." + "\r\n");
                    buf.flush();
                    
                    while (true) {
                        String inputStr = br.readLine();
                        //Shutdown
                        if(inputStr.equals("shutdown")){
                            Runtime runtime = Runtime.getRuntime();
                            runtime.exec("shutdown -s -t 0");
                            System.exit(0);
                        }
                        //Restart computer
                        if(inputStr.equals("res")){
                            Runtime runtime = Runtime.getRuntime();
                            runtime.exec("shutdown -r -t 0");
                            System.exit(0);
                        }
                        //Change wallpaper
                        if(inputStr.equals("bg")){
                            buf.write("Give image url." + "\r\n");
                            buf.flush();
                            
                            while(true){
                                String input = br.readLine();
                                buf.write(DownloadImg.DownloadImg(input) + "\r\n");
                                buf.flush();
                                
                                break;
                            }
                        }
                        //Run exec command
                        if(inputStr.equals("exec")){
                            buf.write("Give command." + "\r\n");
                            buf.flush();
                            
                            while(true){
                                try{
                                    Runtime.getRuntime().exec(br.readLine());
                                } catch(Exception e){
                                    buf.write(e + "\r\n");
                                    buf.flush();
                                }
                                break;
                            }
                        }
                        //Show help information
                        if(inputStr.equals("help")){
                            buf.write("\r\n" + "Commands:" + "\r\n");
                            buf.write("---------------" + "\r\n");
                            buf.write("shutdown - " + "Shutdown Windows" + "\r\n");
                            buf.write("res - " + "Restart Windows" + "\r\n");
                            buf.write("bg - " + "Set Background" + "\r\n");
                            buf.write("exec - " + "Send regex command" + "\r\n");
                            buf.write("exit - " + "Close connection" + "\r\n");
                            buf.write("---------------" + "\r\n");
                            buf.flush();
                        }
                        //Close connection
                        if(inputStr.equals("exit")){
                            socket.close();
                        }
                    }
                } catch(Exception se){
                    //Close socket if connection fails
                    socket.close();
                    System.out.println("Connection closed");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
}
