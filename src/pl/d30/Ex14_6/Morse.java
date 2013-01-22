package pl.d30.Ex14_6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Morse {
    
    // possible sources:
    public final static int PARAM = 0; // passed by 2nd parameter
    public final static int CONSOLE = 1; // read from console
    public final static int FILE = 2; // read from `awesome.txt` file
    public final static int RANDOM = 3; // non-working
    
    // defaults
    private final static int MAX_LENGTH = 16;
    
    private String lastRunCode;
    
    private static Map<String, String> alphabet;
    
    public Morse() {
        alphabet = new HashMap<String, String>();
        alphabet.put("e", "."   );
        alphabet.put("i", ".."  );
        alphabet.put("s", "..." );
        alphabet.put("h", "....");
        alphabet.put("v", "...-");
        alphabet.put("u", "..-" );
        alphabet.put("f", "..-.");
        // ..--
        alphabet.put("a", ".-"  );
        alphabet.put("r", ".-." );
        alphabet.put("l", ".-..");
        // ._._
        alphabet.put("w", ".--" );
        alphabet.put("p", ".--.");
        alphabet.put("j", ".---");
        alphabet.put("t", "-"   );
        alphabet.put("n", "-."  );
        alphabet.put("d", "-.." );
        alphabet.put("b", "-...");
        alphabet.put("x", "-..-");
        alphabet.put("k", "-.-" );
        alphabet.put("c", "-.-.");
        alphabet.put("y", "-.--");
        alphabet.put("m", "--"  );
        alphabet.put("g", "--." );
        alphabet.put("z", "--..");
        alphabet.put("q", "--.-");
        alphabet.put("o", "---" );
        // ---.
        // ----
    }
    
    public String generateCode(int source, int minLength) {
        return generateCode(source, minLength, MAX_LENGTH);
    }
    
    public String generateCode(int source, String s, int minLength) {
        return generateCode(source, s, minLength, MAX_LENGTH);
    }
    
    public String generateCode(int source, int minLength, int maxLength) {
        
        if(source==FILE) {
            String tmp = "";
            try {
                FileReader fr = new FileReader("src/pl/d30/Ex14_6/awesome.txt");
                BufferedReader br = new BufferedReader(fr);
                
                String line;
                while((line = br.readLine())!=null) tmp += line;
                br.close();
                
            } catch( FileNotFoundException e ) {
                System.out.println("No file called `awesome.txt` found.");
                return null;
                
            } catch( IOException e ) {
                System.out.println("Cannot access file `awesome.txt`, or something.");
                return null;
            }   
            return generateCode(PARAM, tmp, minLength, maxLength);
            
        } else if(source==CONSOLE) {
            Scanner sc = new Scanner(System.in);
            String tmp = sc.next();
            sc.close();
            return generateCode(PARAM, tmp, minLength, maxLength);
            
        } else {
            System.out.println("No input provided.");
            return null;
        }
    }
    
    public String generateCode(int source, String s, int minLength, int maxLength) {
        
        if( s.length()>maxLength ) s = s.substring(0, maxLength);
        else while( s.length()<minLength ) s += s.substring(0, (maxLength-s.length())%s.length());
        
        s = s.toLowerCase();
        String c, out = "";
        for(int i=0; i<s.length(); i++) if((c=alphabet.get(s.substring(i, i+1)))!=null) out += c;
        return out;
    }
    
    public void decode( String code ) {
        lastRunCode = code;
        decode(code, "", "", "@");
    }
    
    private void decode( String code, String decoded, String buff, String id ) {
        
        if( code.length()==0 ) {
            if( buff.length()>0 ) {
                if( toLetter(buff)!="" ) decoded += toLetter(buff);
                else return;
            }
            System.out.print(decoded);
            if( !lastRunCode.equals(generateCode(PARAM,decoded,0)) ) System.out.print("\tFAILED!!!");
            System.out.println("\n");
            return;        
        }
        
        if(buff.length()>0 && toLetter(buff)!=null) {
            String newLet = toLetter(buff);
            if( newLet=="" ) return;
            decode( code.substring(1), decoded + newLet , code.substring(0,1), id ); // save letter
        }
        decode(code.substring(1), decoded, buff + code.substring(0,1), id ); // try with longer buffore
    }
    
    private String toLetter(String s) {        
        switch(s){
            case ".": return "e";
            case "..": return "i";
            case "...": return "s";
            case "....": return "h";
            case "...-": return "v";
            case "..-": return "u";
            case "..-.": return "f";
            case ".-": return "a";
            case ".-.": return "r";
            case ".-..": return "l";
            case ".--": return "w";
            case ".--.": return "p";
            case ".---": return "j";
            case "-": return "t";
            case "-.": return "n";
            case "-..": return "d";
            case "-...": return "b";
            case "-..-": return "x";
            case "-.-": return "k";
            case "-.-.": return "c";
            case "-.--": return "y";
            case "--": return "m";
            case "--.": return "g";
            case "--..": return "z";
            case "--.-": return "q";
            case "---": return "o";
            default: return "";
        }
    }

}
