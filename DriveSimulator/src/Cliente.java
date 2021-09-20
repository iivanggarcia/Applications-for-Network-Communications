
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author divan
 */
public class Cliente {
    public static void main(String[] args){
//        CEnviaUnArchivo();
        CEnviaNArchivo();
    }//main
    
    public static void CEnviaUnArchivo(){
        try{
            int pto = 8000; //asumimos que el servidor estará en el puerto 8000
            String dir = "127.0.0.1";
            Socket cl = new Socket(dir,pto); //creo el socket, cliente
            System.out.println("Conexion con servidor establecida.. lanzando FileChooser..");
            JFileChooser jf = new JFileChooser(); //genero mi caja de diálogo
//            jf.setMultiSelectionEnabled(true); //con esto hacemos que el file chooser pueda seleccionar mas de un archivo
            int r = jf.showOpenDialog(null); //con jf.showOpenDialog hago visible la caja de diálogo (null)-> ventana padre donde se genera la caja de diálogo || el int r valida el estado de la caja de diálogo
                //si le dio open r =APPROVE_OPTION, si le dio cancel r = CANCEL_OPTION
            if(r==JFileChooser.APPROVE_OPTION){ //si le dio abrir
                File f = jf.getSelectedFile(); //devuelve referencia de tipo file del archivo que escogió y así se tiene acceso al archivo          
                
                String nombre = f.getName(); //obtengo nombre, lo quiero porque de lado del serividor, antes de descaragr el archivo, tengo que crear la estructura del archivo con el mismo nombre
                String path = f.getAbsolutePath(); //obtengo ruta donde se encuentra, para localizarlo y leer de ahí
                long tam = f.length(); //tamaño, para saber cuál es la condición de paro
                System.out.println("Preparandose pare enviar archivo "+path+" de "+tam+" bytes\n\n");
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //creo flujo de escritura asociado al socket (necesito un string para el nombre y un long para el tamañp)
                DataInputStream dis = new DataInputStream(new FileInputStream(path)); //creo flujo de lectura asociado a la ruta del archivo "path"
                dos.writeUTF(nombre); //escribo en el socket como texto el nombre del archivo
                dos.flush(); //dejo que se vaya
                dos.writeLong(tam); //escribo el tamaño del archivo
                dos.flush(); //dejo que se vaya
                //ahora toca enviar el contenido del archivo, pero debe ser por pedacitos
                long enviados = 0; // acumulador, por cada itelración se incrementa en los bytes que se transmitieron, cuando valga lo mismo que el tamaño del archivo es que ya se acabó de enviar
                int l=0,porcentaje=0; //para saber cuantos bytes se pudieron leer desde el archivo|| porcentaje del archivo enviado
                while(enviados<tam){
                    byte[] b = new byte[1500]; //es un contenedor copiamos de 1500 a 1500 bytes (limitado por el MTU de la tarjeta de red)
                    l=dis.read(b); //leo hasta 1500 byes, me dice cuántos se pudieron leer
//                    System.out.println("enviados: "+l);
                    dos.write(b,0,l); //los escribo en el socket, desde la posición 0 del socket hasta l (los bytes que s pudieron leer)
                    dos.flush(); //dejo que se vayan
                    enviados = enviados + l; //actualizo mi acumulador
                    porcentaje = (int)((enviados*100)/tam); //calculo el porcentaje
//                    System.out.println("\rEnviado el "+porcentaje+" % del archivo: "+enviados+" bytes");
                }//while
                System.out.println("\nArchivo " +nombre+ " enviado..");
                //cierro los flujos
                dis.close();
                dos.close();
                cl.close(); //cierro la conexión
            }//if
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//CEnviaUnArchivo
    
    public static void CEnviaNArchivo(){
        try{
            int pto = 8000; //asumimos que el servidor estará en el puerto 8000
            String dir = "127.0.0.1";
            
            System.out.println("Conexion con servidor establecida.. lanzando FileChooser..");
            JFileChooser jf = new JFileChooser(); //genero mi caja de diálogo
            //////////Establezco un directorio específico para abrir el JFilrChooser/////////////////////////
            jf.setCurrentDirectory(new File(".."+File.separator+".."+File.separator+".."+File.separator+".."+File.separator+"1Prueba")); //le digo a jfilechooser dónde iniciar
            jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //puede seleccionar directorios o archivos
            jf.setMultiSelectionEnabled(true); //con esto hacemos que el file chooser pueda seleccionar mas de un archivo
            int r = jf.showOpenDialog(null); //con jf.showOpenDialog hago visible la caja de diálogo (null)-> ventana padre donde se genera la caja de diálogo || el int r valida el estado de la caja de diálogo
                //si le dio open r =APPROVE_OPTION, si le dio cancel r = CANCEL_OPTION
            if(r==JFileChooser.APPROVE_OPTION){ //si le dio abrir
                Socket cl = new Socket(dir,pto); //creo el socket, cliente
                //si seleccionamos n archivos, nos devuelve n referencias a archivo
                File[] f = jf.getSelectedFiles();
                String[] nombre = new String[f.length];
                String[] path = new String[f.length];
                long[] tam = new long[f.length];
                Boolean[] zip = new Boolean[f.length];
                int i;
                
                DataOutputStream numArchi = new DataOutputStream(cl.getOutputStream());
                numArchi.writeLong(f.length); //escribo el num de archivos
                numArchi.flush();
                int completados=0;
                
                for(i=0; i<f.length; i++){
                    Socket cl_datos = new Socket(dir,pto+1);
                    System.out.print("\n\nPreparandose pare enviar archivo "+f[i].getAbsolutePath());
                    
                    if (f[i].isDirectory()) {
                        try {
                            zipFolder(Paths.get(f[i].getPath()), Paths.get(f[i].getPath()+".zip"));
                            zip[i] = true;
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        f[i] = new File(f[i].getPath()+".zip");
                    } else zip[i]= false;
                    nombre[i] = f[i].getName();
//                    System.out.println("Archivo "+i+" = "+nombre[i]);
                    path[i] = f[i].getAbsolutePath();
                    tam[i] = f[i].length();
                    System.out.println(" de "+f[i].length()+" bytes");
                    
                    DataOutputStream dos = new DataOutputStream(cl_datos.getOutputStream()); //creo flujo de escritura asociado al socket (necesito un string para el nombre y un long para el tamañp)
                    DataInputStream dis = new DataInputStream(new FileInputStream(path[i])); //creo flujo de lectura asociado a la ruta del archivo "path"
                    dos.writeUTF(nombre[i]); //escribo en el socket como texto el nombre del archivo
                    dos.flush(); //dejo que se vaya
                    dos.writeLong(tam[i]); //escribo el tamaño del archivo
                    dos.flush(); //dejo que se vaya
                    dos.writeBoolean(zip[i]); //escribo si el archivo fue comprimido
                    dos.flush(); //dejo que se vaya
                    //ahora toca enviar el contenido del archivo, pero debe ser por pedacitos
                    long enviados = 0; // acumulador, por cada itelración se incrementa en los bytes que se transmitieron, cuando valga lo mismo que el tamaño del archivo es que ya se acabó de enviar
                    int l=0,porcentaje=0; //para saber cuantos bytes se pudieron leer desde el archivo|| porcentaje del archivo enviado
                    while(enviados<tam[i]){
                        byte[] b = new byte[1500]; //es un contenedor copiamos de 1500 a 1500 bytes (limitado por el MTU de la tarjeta de red)
                        l=dis.read(b); //leo hasta 1500 byes, me dice cuántos se pudieron leer
//                        System.out.println("enviados: "+l);
                        dos.write(b,0,l); //los escribo en el socket, desde la posición 0 del socket hasta l (los bytes que s pudieron leer)
                        dos.flush(); //dejo que se vayan
                        enviados = enviados + l; //actualizo mi acumulador
//                        porcentaje = (int)((enviados*100)/tam[i]); //calculo el porcentaje
//                        System.out.println("\rEnviado el "+porcentaje+" % del archivo: "+enviados+" bytes");
                    }//while
                    completados++;
                    //cierro los flujos
                    dis.close();
                    dos.close();
                    cl_datos.close(); //cierro la conexión
                    if(zip[i]==true) f[i].delete();
                    System.out.println(nombre[i]+ " enviado..");
                }
                System.out.println("\nArchivos enviados = "+completados);
                numArchi.close();
                cl.close();
            }//if
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//CEnviaNArchivo
    
    public static void zipFolder(Path sourceFolderPath, Path zipPath) throws Exception {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
            Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                    Files.copy(file, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }//zipFolder
}

