
import java.awt.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;

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
    public static String getRutaServidor(){
        File f = new File(""); //genero una instancia de tipo file pero no le asigno ninguna ruta ""
        String ruta = f.getAbsolutePath(); //obtengo la ruta
        //si no hago nada el archivo se guarda en la raiz del proyecto, pero no quiero, voy a crear una carpeta
        String carpeta="archivos"; //nombre que quiero para la carpeta
        String ruta_archivos = ruta+File.separator+carpeta+File.separator;
        return ruta_archivos;
    }
    
    public static ArrayList getArrayS(String path, String borrar){
        ArrayList arrayS = new ArrayList();
        //System.out.println("El path es: "+path+" y se borrara "+borrar);
        File ff2 = new File(path);
        int t=0;
        File [] inside = ff2.listFiles();
        for (int r = 0; r < inside.length; r++) {
            if (inside[r].isDirectory()) {
                arrayS.add(inside[r].getName()+"\\");
                t++;
//                ListFilesInsidePath(inside[r].getPath(),borrar);
//            }else{
//                System.out.println(inside[r].getPath().replace(borrar, ""));
            }
        }
        for (int r = 0; r < inside.length; r++) {
            if (!(inside[r].isDirectory())) {
                arrayS.add(inside[r].getName());
//                ListFilesInsidePath(inside[r].getPath(),borrar);
//            }else{
//                System.out.println(inside[r].getPath().replace(borrar, ""));
            }
        }
        
        return arrayS;
    }
    
        public static ArrayList getArrayC(String path, String borrar){
        ArrayList arrayS = new ArrayList();
        //System.out.println("El path es: "+path+" y se borrara "+borrar);
        File ff2 = new File(path);
        int t=0;
        File [] inside = ff2.listFiles();
        for (int r = 0; r < inside.length; r++) {
            if (inside[r].isDirectory()) {
                arrayS.add(inside[r].getName()+"\\");
                t++;
//                ListFilesInsidePath(inside[r].getPath(),borrar);
//            }else{
//                System.out.println(inside[r].getPath().replace(borrar, ""));
            }
        }
        for (int r = 0; r < inside.length; r++) {
            if (!(inside[r].isDirectory())) {
                arrayS.add(inside[r].getName());
//                ListFilesInsidePath(inside[r].getPath(),borrar);
//            }else{
//                System.out.println(inside[r].getPath().replace(borrar, ""));
            }
        }
        return arrayS;
    }
    
    public static String getRutaCliente(){
        String rutaCliente = ".."+File.separator+".."+File.separator+".."+File.separator+"1Prueba"+File.separator;
        return rutaCliente;
    }
    
    public static void clienteSubir(){
        try{
            int pto = 8000; //asumimos que el servidor estará en el puerto 8000
            String dir = "127.0.0.1";
            Socket cl = new Socket(dir,pto); //creo el socket, cliente
            System.out.println("Conexion con servidor establecida");

            UIManager.put("FileChooser.cancelButtonText","Cancelar"); //Cambio el nombre de cancel a eliminar
            JFileChooser jf = new JFileChooser(); //genero mi caja de diálogo
//////////////Establezco un directorio específico para abrir el JFilrChooser/////////////////////////
            jf.setCurrentDirectory(new File(".."+File.separator+".."+File.separator+".."+File.separator+"1Prueba")); //le digo a jfilechooser dónde iniciar
            jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //puede seleccionar directorios o archivos
            jf.setMultiSelectionEnabled(true); //con esto hacemos que el file chooser pueda seleccionar mas de un archivo
            jf.setApproveButtonText("Subir"); //cambio el nombre del botón a subir
            Action details = jf.getActionMap().get("viewTypeDetails"); //iniciamos JFileChooser en list view
            details.actionPerformed(null);
            int r = jf.showOpenDialog(null); //con jf.showOpenDialog hago visible la caja de diálogo (null)-> ventana padre donde se genera la caja de diálogo || el int r valida el estado de la caja de diálogo
            //si le dio open r =APPROVE_OPTION, si le dio cancel r = CANCEL_OPTION

            if(r==JFileChooser.APPROVE_OPTION){ //si le dio abrir
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
                numArchi.writeInt(1);
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
            else if(r==JFileChooser.CANCEL_OPTION){
            }//else if
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//CEnviaNArchivo
    
    public static void clienteBorrar(){
        UIManager.put("FileChooser.cancelButtonText","Cancelar"); //Cambio el nombre de cancel a eliminar
        JFileChooser jf = new JFileChooser(); //genero mi caja de diálogo
//////////////Establezco un directorio específico para abrir el JFilrChooser/////////////////////////
        jf.setCurrentDirectory(new File(".."+File.separator+".."+File.separator+".."+File.separator+"1Prueba")); //le digo a jfilechooser dónde iniciar
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //puede seleccionar directorios o archivos
        jf.setMultiSelectionEnabled(true); //con esto hacemos que el file chooser pueda seleccionar mas de un archivo
        jf.setApproveButtonText("Eliminar"); //cambio el nombre del botón a subir
        Action details = jf.getActionMap().get("viewTypeDetails"); //iniciamos JFileChooser en list view
        details.actionPerformed(null);
        int r = jf.showOpenDialog(null); //con jf.showOpenDialog hago visible la caja de diálogo (null)-> ventana padre donde se genera la caja de diálogo || el int r valida el estado de la caja de diálogo
        //si le dio open r =APPROVE_OPTION, si le dio cancel r = CANCEL_OPTION

        if(r==JFileChooser.APPROVE_OPTION){ //si le dio abrir
            File[] f = jf.getSelectedFiles();
            int i;
            for(i=0; i<f.length; i++){
                deleteFile(f[i]);
            }
        }//if
    }
    
    public static void servidorDescargar(){
        try{
            int pto = 8000; //asumimos que el servidor estará en el puerto 8000
            String dir = "127.0.0.1";
            Socket cl = new Socket(dir,pto); //creo el socket, cliente
            System.out.println("Conexion con servidor establecida");

            UIManager.put("FileChooser.cancelButtonText","Cancelar"); //Cambio el nombre de cancel a eliminar
            JFileChooser jf = new JFileChooser(); //genero mi caja de diálogo
//////////////Establezco un directorio específico para abrir el JFilrChooser/////////////////////////
            String rutaServidor = getRutaServidor();
            jf.setCurrentDirectory(new File(rutaServidor)); //le digo a jfilechooser dónde iniciar
            jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //puede seleccionar directorios o archivos
            jf.setMultiSelectionEnabled(true); //con esto hacemos que el file chooser pueda seleccionar mas de un archivo
            jf.setApproveButtonText("Descargar"); //cambio el nombre del botón a subir
            Action details = jf.getActionMap().get("viewTypeDetails"); //iniciamos JFileChooser en list view
            details.actionPerformed(null);
            int r = jf.showOpenDialog(null); //con jf.showOpenDialog hago visible la caja de diálogo (null)-> ventana padre donde se genera la caja de diálogo || el int r valida el estado de la caja de diálogo
            //si le dio open r =APPROVE_OPTION, si le dio cancel r = CANCEL_OPTION

            if(r==JFileChooser.APPROVE_OPTION){ //si le dio abrir
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
                numArchi.writeInt(2);
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
            else if(r==JFileChooser.CANCEL_OPTION){
            }//else if
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }
    
    public static void servidorBorrar(){
        UIManager.put("FileChooser.cancelButtonText","Cancelar"); //Cambio el nombre de cancel a eliminar
        JFileChooser jf = new JFileChooser(); //genero mi caja de diálogo
//////////////Establezco un directorio específico para abrir el JFilrChooser/////////////////////////
        String rutaServidor = getRutaServidor();
        jf.setCurrentDirectory(new File(rutaServidor));
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //puede seleccionar directorios o archivos
        jf.setMultiSelectionEnabled(true); //con esto hacemos que el file chooser pueda seleccionar mas de un archivo
        jf.setApproveButtonText("Eliminar"); //cambio el nombre del botón a subir
        Action details = jf.getActionMap().get("viewTypeDetails"); //iniciamos JFileChooser en list view
        details.actionPerformed(null);
        int r = jf.showOpenDialog(null); //con jf.showOpenDialog hago visible la caja de diálogo (null)-> ventana padre donde se genera la caja de diálogo || el int r valida el estado de la caja de diálogo
        //si le dio open r =APPROVE_OPTION, si le dio cancel r = CANCEL_OPTION

        if(r==JFileChooser.APPROVE_OPTION){ //si le dio abrir
            File[] f = jf.getSelectedFiles();
            int i;
            for(i=0; i<f.length; i++){
                deleteFile(f[i]);
            }
        }//if
    }
    
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
    
    public static void deleteFile(File element) {
        if (element.isDirectory()) {
            for (File sub : element.listFiles()) {
                deleteFile(sub);
            }
        }
        element.delete();
    }//deleFile
    
    public static void ListFilesInsidePath(String path, String borrar){
        //System.out.println("El path es: "+path+" y se borrara "+borrar);
        File ff2 = new File(path);
        File [] inside = ff2.listFiles();
        for (int r = 0; r < inside.length; r++) {
            
            if (inside[r].isDirectory()) {
                ListFilesInsidePath(inside[r].getPath(),borrar);
            }else{
                System.out.println(inside[r].getPath().replace(borrar, ""));
            }
            
        }
    } //ListFilesInsidePath
    
    
}

