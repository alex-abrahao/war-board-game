package Model;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveGame {

    public static void saveBinario(Object match, String fileName) {
      File file = new File(fileName);
      try {
        file.delete();
        file.createNewFile();
      
        ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(file));
        objOutput.writeObject(match);
        objOutput.close();
      
      } catch(IOException erro) {
          System.out.printf("Erro: %s", erro.getMessage());
      }
    }

    // public static ArrayList<Object> readBinario(String fileName) {
    //   ArrayList<Object> lista = new ArrayList();
    //   try {
    //     File arq = new File(fileName);
    //     if (arq.exists()) {
    //        ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(arq));
    //        lista = (ArrayList<Object>)objInput.readObject();
    //        objInput.close();
    //     }
    //   } catch(IOException erro1) {
    //       System.out.printf("Erro: %s", erro1.getMessage());
    //   } catch(ClassNotFoundException erro2) {
    //       System.out.printf("Erro: %s", erro2.getMessage());
    //   }
    
    //   return(lista);
    // }

    public static void readBinario(String fileName) {
      Object load = new Object();
      try {
        File arq = new File(fileName);
        if (arq.exists()) {
           ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(arq));
           load = (Object)objInput.readObject();
           objInput.close();
        }
      } catch(IOException erro1) {
          System.out.printf("Erro: %s", erro1.getMessage());
      } catch(ClassNotFoundException erro2) {
          System.out.printf("Erro: %s", erro2.getMessage());
      }
      System.out.println(load);
      //return(lista);
    }


    
}