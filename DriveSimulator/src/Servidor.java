
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author divan
 */
public class Servidor {
    public static void main(String[] args){
      try{ //siempre iniciamos con un bloque try catch
          int pto = 8000,i;
          ServerSocket s = new ServerSocket(pto); //verificamos el puerto / recibe metainformación / cuantos archuivos va a recibir
          ServerSocket s_datos = new ServerSocket(pto+1); //para protocolo ftp
          s.setReuseAddress(true); //por si queremos llegar a reutilizar el puerto
          s_datos.setReuseAddress(true);
          //el servidor vaa recibir el archivo por conexión, recibe el nombre del archivo, el tamaño del archivo, y el contenido del archivo en varios paquetes (buffer 65535 bytes)
          System.out.println("Servidor iniciado esperando por archivos..");
          //se guarda el archivo en una carpeta
          File f = new File(""); //genero una instancia de tipo file pero no le asigno ninguna ruta ""
          String ruta = f.getAbsolutePath(); //obtengo la ruta
          //si no hago nada el archivo se guarda en la raiz del proyecto, pero no quiero, voy a crear una carpeta
          String carpeta="archivos"; //nombre que quiero para la carpeta
          String ruta_archivos = ruta+File.separator+carpeta+File.separator; //concateno la ruta con la carpeta, es \\ porque el primero es para decir que viene un caracter especial y el segundo ya es el que Java cuenta
          System.out.println("ruta:"+ruta_archivos);
          File f2 = new File(ruta_archivos); //creo la carpeta creando una instancia de tipo file
          f2.mkdirs(); //aquí se crea la carpeta
          f2.setWritable(true); //La hago editable
          for(;;){
              Socket cl = s.accept(); //acepto la conexion y recibo una referencia de tipo socket
              System.out.println("\n\nCliente conectado desde "+cl.getInetAddress()+":"+cl.getPort());
              DataInputStream disCl = new DataInputStream(cl.getInputStream());
              long numArchivos = disCl.readLong();
              System.out.println("Num archivos = "+ numArchivos);
              for(i=0;i<numArchivos; i++){
                  Socket cl_datos = s_datos.accept(); //acepto la conexion y recibo una referencia de tipo socket
                  DataInputStream dis = new DataInputStream(cl_datos.getInputStream()); //primero asocio el flujo de lectura para saber el nombre del archivo a escribir
                                            //no puedo asociar el flujo de escritura porque no se asocia al socket sino al sistema de archivos local y para eso necesito el nombre
                  String nombre = dis.readUTF(); //leo el nombre del archivo
                  String nombreTmp = nombre;
                  long tam = dis.readLong(); //leo el tamaño
                  Boolean zip = dis.readBoolean(); //veo si el archivo fue comprimido
                  if(zip) nombreTmp = nombre.replace(".zip", "");
                  System.out.println("\nComienza descarga del archivo "+nombreTmp+" de "+tam+" bytes");
                  DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta_archivos+nombre)); //asocio el flujo de escritura a la ruta que se creo y le agrego el nombre del archivo, DataOutputStream permite escribir tipos de datos primitivos, FileOutputStream solo escribe bytes, "ruta_archivos+nombre" es la ruta donde va a escribir
                  long recibidos=0;
                  int l=0, porcentaje=0;
                  while(recibidos<tam){
                      byte[] b = new byte[1500]; //Contenedor temporal antes de que se agreguen al archivo , 1500 es el MTU de una tarjeta de red Ethernet
                      l = dis.read(b); //lo que se pueda leer entre 0 y 1500 bytes lo va a guardar en b y me dice cuántos se pudieron leer
//                    System.out.println("leidos: "+l);
                      dos.write(b,0,l); //escribo en el archivo desde el indice 0 a l del arreglo b
                      dos.flush(); //vacío el buffer para verificar que los datos sí se escriben
                      recibidos = recibidos + l; //actualizo el num de bytes recibidos
                      porcentaje = (int)((recibidos*100)/tam); //saco el porcentaje
//                    System.out.print("\rRecibido el "+ porcentaje +" % del archivo");
                  }//while
                  //una vez que ya se acabó de descargar el archivo
                  dos.close();
                  //cierro los flujos
                  dis.close();
                  cl_datos.close();
                  if (zip) {
                      unZip(ruta_archivos+nombre, ruta_archivos+nombreTmp);
                      File paraBorrar = new File(ruta_archivos+nombre);
                      paraBorrar.delete();
                    }//if
                  System.out.println("Archivo " +nombreTmp +" recibido..");
              }//for
              disCl.close();
              cl.close(); //cierro el socket y vuelve a iniciar el for
          }//for
          
      }catch(Exception e){
          e.printStackTrace();
      }  
    }//main
    
    private static void unZip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
//                System.out.println("Unzipping to "+newFile.getAbsolutePath());

                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//unZip
}

