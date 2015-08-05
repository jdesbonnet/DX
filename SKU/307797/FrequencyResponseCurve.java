import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


/**
 * Generate a frequency response curve with DSO-X 3014A InfiniiVision-3000 series scope.
 * Assumes the WaveGen feature is enabled on the device.
 * 
 * To use put the oscilloscope host name or IP address as an argument. For scopes without
 * the optional ethernet port, it should also be possible to run this script via the
 * USB port, but I haven't got around to trying that yet.
 * 
 * Note: ideally this script should make no assumptions about the current state of the
 * scope measurement menu, gain, timebase etc. In its current state it assumes much
 * of the setup is done manually. I'll try to update later to do a more thorough 
 * configuration prior to acquiring data.
 * 
 * @author Joe Desbonnet
 *
 */
public class FrequencyResponseCurve {
	
	private static OutputStream out;
	private static InputStream in;
	private static OutputStreamWriter w;
	private static InputStreamReader r;

	public static void main (String[] arg) throws Exception {
		
		// Create and open socket in port 5025 (SCPI commands without prompt)
        Socket socket = new Socket(arg[0], 5025);
        
        out = socket.getOutputStream();
        in = socket.getInputStream();
        
        w = new OutputStreamWriter(out);
        r = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(r);
        
               
        // Enable WaveGen
        sendCommand(":WGEN:OUTP 1");
        
        // Set WaveGen function to sine wave
        sendCommand(":WGEN:FUNC SIN");
        
        // Set WaveGen Vpp to 1V
        sendCommand(":WGEN:VOLT 1");
        
        // Sweep through frequencies
        
        for (int frequency = 20000; frequency < 5000000; frequency+=1000) {
        	
        	// Set WaveGen frequency	
        	sendCommand(":WGEN:FREQ " + frequency);
        	
        	//sendCommand(":WGEN:STAT:RES\n");
        	//Thread.sleep(100);
        	
        	// Measure peak-to-peak voltage of output
        	sendCommand(":MEAS:VPP?\n");
        	
        	// Read result from socket
        	String Vpp = br.readLine();
        	
        	// Output to datafile
        	System.out.println ("" + frequency + " " + Vpp);
        }
        
        socket.close();
        
	}
	
	private static void sendCommand(String cmd) throws IOException {
		w.write(cmd + "\r\n");
		w.flush();
	}
}
