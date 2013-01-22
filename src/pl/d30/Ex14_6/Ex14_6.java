package pl.d30.Ex14_6;

public class Ex14_6 {

    public static void main(String[] args) {
        
        Morse m = new Morse();
        
        String code = m.generateCode(Morse.FILE, 10);
        System.out.println(code);
        
        m.decode( code );
        

    }

}
