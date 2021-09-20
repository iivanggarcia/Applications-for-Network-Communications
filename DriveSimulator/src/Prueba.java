
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author divan
 */
public class Prueba {
    public static void main(String[] args){
//                String[] nombre = new String[20];
//                String[] path = new String[20];
//                long[] tam = new long[20];
//                int i=0,  c=0;
//                String a="a", b="b";
//                for(i=0; i<20; i++){
//                    nombre[i] = a+i;
//                    path[i] = b+i;
//                    tam[i] = c+i;
//                }
//                for(i=0; i<20; i++){
//                    System.out.println("nombre "+i+": "+nombre[i]);
//                    System.out.println("path "+i+": "+path[i]);
//                    System.out.println("tam "+i+": "+tam[i]);
//                }
        String directoryName1 = System.getProperty("user.dir");
        System.out.println("Current Working Directory is = " +directoryName1);
        
        Path path = Paths.get("");
        String directoryName = path.toAbsolutePath().toString();
        System.out.println("Current Working Directory is = " +directoryName);
    }//main
}
