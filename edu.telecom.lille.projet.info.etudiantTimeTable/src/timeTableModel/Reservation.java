/*******************************************************************************
 * 2016, All rights reserved.
 *******************************************************************************/

package timeTableModel;
import java.util.Date;


/**
 * Description of Reservation.
 * 
 * @author Thomas-Gosset
 * 
 * Cette classe a pour utilité de poser les bases de la reservation avec tous les arguments
 * qui le compose. 
 * 
 */
public class Reservation {

	private int reservationId;
	private String professeurId;
	private Date dateDeb;
	private Date dateFin;
	private int salleId;
	private int timeTableId;
	
	
	/**
	 * 
	 * Constructeur de base de la classe Reservation
	 * 
	 * @param timeTableId
	 * @param reservationId
	 * @param professeurId
	 * @param dateDeb
	 * @param dateFin
	 * @param salleId
	 */
	
	public Reservation(int timeTableId, int reservationId, String professeurId, Date dateDeb, Date dateFin, int salleId) {
		this.reservationId = reservationId;
		this.professeurId = professeurId;
		this.dateDeb = dateDeb;
		this.dateFin = dateFin;
		this.salleId = salleId;
		this.timeTableId = timeTableId;
	}
	
	/**
	 *  Les fonctions get qui permettent de recupérer tout ce dont les fonctions supérieures 
	 *  ont besoin. 
	 *  
	 *  Chaque fonction permet de recupérer un paramètre en particulier
	 *  
	 * @return professeurId
	 * @return dateDeb
	 * @return dateFin
	 * @return salleId
	 * @return reservationId
	 * @return timeTableId
	 * 
	 */
	public String getProfesseurId() {
		return this.professeurId;
	}
	
		public int getTimeTableId () {
		return this.timeTableId;
	}
		
		public Date getDateDeb() {
		return this.dateDeb;
	}

	public Date getDateFin()  {
		return this.dateFin;
	}
	
	public int getSalleId() {
		return this.salleId;
	}
	
	public int getReservationId() {
		return this.reservationId;
	}
	

}