package Model;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveGame {
	
	// Returns if the save was successful
	public static boolean saveBinary(Object match, String fileName) {
		File file = new File(fileName);
		try {
			file.delete();
			file.createNewFile();
			
			ObjectOutputStream objOutput = new ObjectOutputStream(new FileOutputStream(file));
			objOutput.writeObject(match);
			objOutput.close();
			
		} catch(IOException erro) {
			System.out.printf("Erro: %s", erro.getMessage());
			return false;
		}
		return true;
	}
						
	public static SaveMatch readBinary(String fileName) {
		SaveMatch load = new SaveMatch();
		try {
			File arq = new File(fileName);
			if (arq.exists()) {
				ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(arq));
				load = (SaveMatch)objInput.readObject();
				objInput.close();
			} else {
				return null;
			}
		} catch(IOException erro1) {
			System.out.printf("Erro: %s", erro1.getMessage());
			return null;
		} catch(ClassNotFoundException erro2) {
			System.out.printf("Erro: %s", erro2.getMessage());
			return null;
		}
		
		System.out.println(load);
		
		return(load);
	}					
}