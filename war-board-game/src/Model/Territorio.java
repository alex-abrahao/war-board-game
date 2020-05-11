package Model;

public class Territorio {
	private String name;
	private String owner;
	private Continente continent;
	private int army;
	private Territorio[] neighbors;
	
	
	public String getOwnerName() {
		return owner;
	}
	
	public boolean validaAtaque(Territorio territorio) {
		
		if (territorio.getOwnerName().equals(this.getOwnerName())) {
			return false;
		}
		
		if (this.army<=1) {
			return false;
		}
		
		//TO DO: checar vizinhos
		
		return true;
	}
	
	
}
