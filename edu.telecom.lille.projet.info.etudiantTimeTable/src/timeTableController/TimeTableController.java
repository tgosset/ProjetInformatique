package timeTableController;

import java.util.Date;
import java.util.Hashtable;

import timeTableModel.TimeTableDB;
/**
 * Cette classe est le contrôleur d'emplois du temps que vous devez implémenter. 
 * Elle contient un attribut correspondant à la base de données d'emplois du temps que vous allez créer.
 * Elle contient toutes les fonctions de l'interface ITimeTableController que vous devez implémenter.
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe à modifier

public class TimeTableController implements ITimeTableController{

	/**
	 * Contient une instance de base de données d'emplois du temps
	 * 
	 */
	TimeTableDB tTDB;
	/**
	 * Constructeur de controleur d'emplois du temps créant la base de données d'emplois du temps
	 * 
	 * @param tTfile
	 * 		Fichier XML contenant la base de données d'emplois du temps
	 */
	public TimeTableController(String tTfile) {
		TimeTableDB tTDB=new TimeTableDB(tTfile);
		this.tTDB=tTDB;
	}

	@Override
	public String getTeacherLogin(int timeTableId, int bookId) {
		String string ;
		string = this.tTDB.getProfesseurId(timeTableId, bookId);
		
		return string;
	}

	@Override
	public String[] roomsIdToString() {
		String string[];
		string = this.tTDB.salleIdToString();
		
		return string;
	}

	@Override
	public String[] roomsToString() {
		String string[];
		string = this.tTDB.salleToString();
		
		return string;
	}

	@Override
	public String[] timeTablesIDToString() {
		String string[];
		string= this.tTDB.EDTToString();
		
		return string;
	}

	@Override
	public String[] booksIdToString(int timeTableId) {
		String string[];
		string= this.tTDB.reservationToString(timeTableId);
		
		return string;
	}

	@Override
	public boolean addRoom(int roomId, int capacity) {
		boolean reussite;
		reussite= this.tTDB.AddSalle(roomId, capacity);
		
		return reussite;
	}

	@Override
	public boolean removeRoom(int roomId) {
		boolean reussite;
		reussite = this.tTDB.DeleteSalle(roomId);
		
		return reussite;
	}

	@Override
	public int getRoom(int timeTableId, int bookId) {
		int numero;
		numero = this.tTDB.getSalle(timeTableId, bookId);
		
		return numero;
	}

	@Override
	public boolean addTimeTable(int timeTableId) {
		boolean reussite;
		reussite = this.tTDB.addEDT(timeTableId);
		
		return reussite;
	}

	@Override
	public boolean removeTimeTable(int timeTableId) {
		boolean reussite;
		reussite = this.tTDB.deleteEDT(timeTableId);
		
		return reussite;
	}

	@Override
	public boolean addBooking(int timeTableId, int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
		boolean reussite;
		reussite = this.tTDB.addReservation(timeTableId, login,bookingId,dateBegin, dateEnd, roomId);
		
		return reussite;
	}

	@Override
	public void getBookingsDate(int timeTableId, Hashtable<Integer, Date> dateBegin, Hashtable<Integer, Date> dateEnd) {
		this.tTDB.getDateReservation(timeTableId, dateBegin, dateEnd);
	}

	@Override
	public boolean removeBook(int timeTableId, int bookId) {
		boolean reussite;
		reussite = this.tTDB.deleteReservation(timeTableId, bookId);
		
		return reussite;
	}

	@Override
	public int getBookingsMaxId(int timeTableId) {
		int maxReservation;
		maxReservation = this.tTDB.getIdReservationMax(timeTableId);
		
		return maxReservation;
	}

	@Override
	public boolean saveDB() {
		return this.tTDB.saveDB();
	}

	@Override
	public boolean loadDB() {
		return this.tTDB.loadDB();
	}
	
	

}
