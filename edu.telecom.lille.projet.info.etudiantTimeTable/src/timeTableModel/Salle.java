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
 * Cette classe a pour utilit� de poser les bases de la reservation avec tous les arguments
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
	 *  Les fonctions get qui permettent de recup�rer tout ce dont les fonctions sup�rieures 
	 *  ont besoin. 
	 *  
	 *  Chaque fonction permet de recup�rer un param�tre en particulier
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
