/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/
package timeTableModel;


/**
 * Description of Salle.
 * 
 * @author Thomas-Gosset
 * 
 * Definit les bases de Salle
 * 
 * Cette classe a pour utilité de poser les bases de la reservation avec tous les arguments
 * qui le compose. 
 * 
 */
public class Salle {

	private int salleId;
	private int capacite;
	
	
	/**
	 *
	 * Constructeur de base de la salle
	 *
	 *
	 *@param salleId
	 *@param capacite
	 *
	 */
	public Salle(int salleId, int capacite) {
		this.salleId= salleId;
		this.capacite= capacite;
	}


	/**
	 *  Les fonctions get qui permettent de recupérer tout ce dont les fonctions supérieures 
	 *  ont besoin. 
	 *  
	 *  Chaque fonction permet de recupérer un paramètre en particulier
	 *  
	 * @return capacite
	 * @return salleId
	 * 
	 */
	public int getNbPlace() {
		return this.capacite;
	}
	
	public int getSalleId() {
		return this.salleId;
	}
}
