package timeTableModel;

import java.util.Hashtable;
import java.util.Date;
import java.util.Set;


/**
 * Description of TimeTable.
 * 
 * @author Thomas-Gosset
 * 
 * Cette classe pose les bases de l'emplois du temps en prenant les informations de 
 * reservation
 * Elle permet de creer les emplois du temps qui seront stocker par la suite dans TimeTableDB
 * 
 */
public class TimeTable {
	
	private int timeTableId;
	
	private Hashtable<Integer, Reservation> reservation;
	

	/**
	 * Le constructeur de la classe
	 * 
	 * @param timeTableId
	 * @param reservations
	 */
	public TimeTable(int timeTableId, Hashtable<Integer, Reservation> reservations) {
		this.timeTableId = timeTableId;
		this.reservation = reservations;
		
	}

	/**
	 * Les fonctions get permettent de recuperer les différents paramètres  dans les classes 
	 * supérieures
	 * 
	 * 
	 * @param reservationId
	 * 
	 * @return le numero de salle
	 * @return l'id du professeur
	 * @return l'heure de début de la reservation
	 * @return l'heure de fin de la reservation
	 */
	public int getSalleId( int reservationId) {
		Reservation r = this.reservation.get(reservationId);
		if ( r!= null ) {
			return r.getSalleId();
		}
		else {
			return -1 ;
		}
	}
	
	public String getProfesseurId (int reservationId) {
		Reservation r = this.reservation.get(reservationId);
		if (r != null) {
			return r.getProfesseurId();
		}
		else {
		return null;
		}
	}
	
	public Date getDateDeb( int reservationId) {
		Reservation r = this.reservation.get(reservationId);
		if (r != null) {
			return r.getDateDeb();
		}
		else {
		return null;
		}
	}
	
	public Date getDateFin ( int reservationId) {
		Reservation r = this.reservation.get(reservationId);
		if (r != null) {
			return r.getDateFin();
		}
		else {
		return null;
		}
	}

	
	/**
	 * 
	 *Accesseur de ReservationId
	 * 
	 * @return l'id de la reservation
	 */
	public Set<Integer> getReservationId() {
		return this.reservation.keySet();
	}
	
	
	/**
	 * 
	 * fonction permettant de recupérer l'id de la timeTable
	 * 
	 * @return l'id de la timeTable
	 */
	public int getTimeTableId() {
		return this.timeTableId;
	}
	
	
	/**
	 * Fonction permettant de definir la valeur de l'id de la timeTable
	 * 
	 * @param timeTableId
	 */
	public void setTimeTableId (int timeTableId) {
		this.timeTableId = timeTableId;
	}
	
	/**
	 * 
	 * Permet de recuperer le nombre de reservation max
	 * 
	 * @return le nombre de reservation max
	 */
	public int getReservationMax() {
		int maxReserv = -1;
		Set<Integer> reservId = this.reservation.keySet();
		
		for(Integer reservationId : reservId){
			if (reservationId> maxReserv ) {
				maxReserv = reservationId;
			}
		}
		
		return maxReserv ;
	}
	
	/**
	 * 
	 * Permet d'ouvrir toutes les reservations et de recuperer les heures de debut
	 * et de fin de chacune.
	 * 
	 * @param dateDeb
	 * @param dateFin
	 */
	public void getDateReservation ( Hashtable<Integer, Date> dateDeb, Hashtable<Integer, Date> dateFin) {
		Set<Integer> reservId = this.getReservationId();

		for (int Reservations : reservId) {
			dateDeb.put(Reservations, this.reservation.get(Reservations).getDateDeb());
			dateFin.put(Reservations, this.reservation.get(Reservations).getDateFin());
		}

	}	
	
	
	/**
	 * 
	 * Permet de rajouter une reservation en fonction de tous les paramètres 
	 * rentrées
	 * 
	 * @param reservationId
	 * @param professeurId
	 * @param dateDeb
	 * @param dateFin
	 * @param salleId
	 * @param timeTableId
	 * 
	 * @return true 
	 */
	public boolean AddReservation(int reservationId, String professeurId, Date dateDeb, Date dateFin, int salleId, int timeTableId) {
		this.reservation.put(reservationId, new Reservation (timeTableId, reservationId, professeurId, dateDeb, dateFin, salleId));
		return true;
	}

	
	/**
	 * Permet de supprimer la reservation 
	 * 
	 * @param reservationId
	 * 
	 * @return true si cela a fonctionné
	 * @return false sinon
	 */
	public boolean DeleteReservation( int reservationId) {
		if (this.reservation.remove(reservationId) != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
