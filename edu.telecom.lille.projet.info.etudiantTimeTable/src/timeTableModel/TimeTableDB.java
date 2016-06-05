package timeTableModel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;


/**
 * 
 * Cette classe gÃ©re la base de donnÃ©es d'emplois du temps. Elle doit permettre de sauvegarder et charger les emplois du temps ainsi que les salles Ã  partir d'un fichier XML. 
 * La structure du fichier XML devra Ãªtre la mÃªme que celle du fichier TimeTableDB.xml.
 * @see <a href="../../TimeTableDB.xml">TimeTableDB.xml</a> 
 * 
 * @author Jose Mennesson (Mettre Ã  jour)
 * @version 04/2016 (Mettre Ã  jour)
 * 
 */

public class TimeTableDB {
	
	private Hashtable<Integer, TimeTable> EDT;
	private Hashtable<Integer, Salle> salles;
	
	
	private String file;
	
	
	/**
	 * Constructeur de timeTableDB
	 * 
	 * definit la base de la classe
	 * 
	 * @param file
	 */
	public TimeTableDB(String file){
		this.setFile(file);
		this.salles = new  Hashtable<Integer, Salle>();
		this.EDT = new Hashtable<Integer, TimeTable>();
		this.loadDB();
	}

	
	/**
	 * 
	 * La fonction getFile() permet de recuperer le fichier
	 * 
	 * @return fichier
	 */
	public String getFile() {
		return file;
	}
	
	/** 
	 * La fonction setFile() permet de selectionner le fichier
	 * @param file
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	
	/**
	 * 
	 * permet d'ajouter les salles avec les paramètres souhaités.
	 * 
	 * @param salleId
	 * @param capacite
	 * @return un bool de la sauvegarde
	 */
	public boolean AddSalle(int salleId, int capacite) {
		this.salles.put(salleId, new Salle(salleId, capacite));
		return this.saveDB();
	}
	
	
	/**
	 * permet de supprimer une salle a partir de son numero
	 * 
	 * @param salleId
	 * @return un bool de la sauvegarde
	 */
	public boolean DeleteSalle(int salleId) {
		this.salles.remove(salleId);
		return this.saveDB();
	}
	
	
	/**
	 * 
	 * Permet de recuperer l'id du professeur à partir de la reservation réalisé
	 * @param timeTableId
	 * @param reservationId
	 * @return soit l'id du prof si il a été trouvé soit null
	 */
	public String getProfesseurId (int timeTableId, int reservationId) {
		TimeTable edt = this.EDT.get(timeTableId);
		if (edt != null) {
			return edt.getProfesseurId(reservationId);
		}
		else {
			return null;
		}
	}
	
	/** 
	 * 
	 * permet de recuperer le numero de salle a partir du numéro de reservation
	 * @param timeTableId
	 * @param reservationId
	 * @return le numero de salle si elle a été trouvé sinon null
	 */
	public int getSalle(int timeTableId, int reservationId) {
		TimeTable edt = this.EDT.get(timeTableId);
		if (edt != null) {
			return edt.getSalleId(reservationId);
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Permet d'ajouter des emplois du temps
	 * @param timeTableId
	 * @return  un bool en fonction de la sauvegarde
	 */
	public boolean addEDT (int timeTableId) {
		this.EDT.put(timeTableId, new TimeTable ( timeTableId, new Hashtable<Integer, Reservation>()));
		return this.saveDB();
	}
	
	/**
	 * Permet de supprimer des emplois du temps
	 * @param timeTableId
	 * @return un bool en fonction de la sauvegarde
	 */
	public boolean deleteEDT (int timeTableId) {
		this.EDT.remove(timeTableId);
		return this.saveDB();
	}
	
	/**
	 * permet d'ajouter des reservations grace aux informations rentrées
	 * @param timeTableId
	 * @param professeurId
	 * @param reservationId
	 * @param dateDeb
	 * @param dateFin
	 * @param salleId
	 * @return un bool de la sauvegarde 
	 */
	public boolean addReservation (int timeTableId, String professeurId, int reservationId, Date dateDeb, Date dateFin, int salleId) {
		boolean reussite = true;
		TimeTable edt = this.EDT.get(timeTableId);
		if (edt != null) {
			reussite = edt.AddReservation(reservationId, professeurId, dateDeb, dateFin, salleId, timeTableId);
		}
		else {
			reussite = false;
		}
		return this.saveDB() && reussite;
	}
	
	
	/** 
	 * permet de supprimer une reservation à partir du numero de reservation
	 * @param timeTableId
	 * @param reservationId
	 * @return un bool en fonction de la sauvegarde
	 */
	public boolean deleteReservation (int timeTableId, int reservationId) {
		boolean reussite = true;
		TimeTable edt = this.EDT.get(timeTableId);
		
		if (edt != null) {
			reussite = edt.DeleteReservation(reservationId);
		}
		return this.saveDB() && reussite;
	}
	
	/**
	 * permet de recuperer l'id de reservation max 
	 * @param timeTableId
	 * @return l'id de reservation max ou -1
	 */
	public int getIdReservationMax (int timeTableId) {
		TimeTable edt = this.EDT.get(timeTableId);
		if (edt != null) {
			return edt.getReservationMax();
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Permet de recuperer les heures des reservations effectuées
	 * @param timeTableId
	 * @param dateDeb
	 * @param dateFin
	 */
	public void getDateReservation( int timeTableId, Hashtable<Integer, Date> dateDeb, Hashtable<Integer, Date> dateFin) {
		TimeTable edt = this.EDT.get(timeTableId);
		if (edt != null) {
			edt.getDateReservation(dateDeb, dateFin);
		}
	}
	
	/**
	 * permet de transformer le int de l'id de la salle en un string
	 * @return un string de l'id de la salle
	 */
	public String[] salleIdToString() {
		String stringId[] = new String[200];
		int i=0;
		Set<Integer> salleId = this.salles.keySet();
		
		for (Integer id : salleId) {
			stringId[i] =id.toString();
			i++;
		}
		return stringId;
	}
	
	/**
	 * permet de transformer les informations de la salle afin de leur donner 
	 * la forme souhaitée
	 * @return un string 
	 */
	public String[] salleToString() {

		String stringId[] = new String[200];
		int i = 0;
		Set<Integer> salle = this.salles.keySet();
		
		for (Integer id : salle) {

			stringId[i] = id.toString() + " | " + String.valueOf(salles.get(id).getNbPlace());
			i++;
		}
		return stringId;
	}

	/**
	 * permet de transformer de un emploi en string
	 * @return string
	 */
	public String[] EDTToString() {
		String stringId[] = new String[200];
		int i = 0;
		Set<Integer> edtId = this.EDT.keySet();
		
		for (Integer id : edtId) {
			stringId[i] = id.toString();
			i++;
		}
		return stringId;
	}
	
	/**
	 * permet de transformer la reservation en string 
	 * @param timeTableId
	 * @return un string
	 */
	public String[] reservationToString(int timeTableId) {
		TimeTable edt = this.EDT.get(timeTableId);
		String stringId[] = new String[200];
		int i = 0;
		Set<Integer> reservationId = edt.getReservationId();
		
		for (Integer id : reservationId) {
			stringId[i] = id.toString();
			i++;
		}
		return stringId;
	}
	
	/**
	 * permet de sauvegarder les salles 
	 * @return l'element de sauvegarde
	 */
	private Element saveSalles() {
		Element sallesXML = new Element("Salles") ;
		Set<Integer> salleIds = this.salles.keySet();
		Element salle, salleId, capacite;
		
		for (Integer id : salleIds) {
			salle = new Element ("Salle");
			salleId = new Element("SalleId");
			capacite = new Element("Capacite");
			salleId.addContent(id.toString());
			capacite.addContent(String.valueOf(this.salles.get(id).getNbPlace()));
			
			sallesXML.addContent(salle);
			salle.addContent(salleId);
			salle.addContent(capacite);
		}
		return sallesXML;
	}
	
	/**
	 * permet de sauvegarder les emplois du temps
	 * @return l'element de sauvegarde
	 */
	private Element saveEDT() {
		Element EDTXML = new Element("EDT") ;
		Set<Integer> edtIds = this.EDT.keySet();
		Element edt, edtId;
		
		for (Integer id : edtIds) {
			edt = new Element ("Emploisdutemps");
			edtId = new Element("idDuGroupe");
			edtId.addContent(id.toString());
			edt.addContent(edt);
			edt.addContent(this.saveReservation(this.EDT.get(id)));
			EDTXML.addContent(edt);
		}
		return EDTXML;
	}
	
	/**
	 * permet de sauvegarder les reservations 
	 * @return l'element de sauvegarde
	 */
	private Element saveReservation(TimeTable edtActuel) {
		Element reservations, reservation, reservationId, id, dateDeb, dateFin, salleId;
		Set<Integer> reservationIds = edtActuel.getReservationId();
		reservations = new Element("reservations");
		
		for (Integer reservId : reservationIds) {
			reservation = new Element("Reservation");
			reservationId = new Element("ReservationId");
			id = new Element("Id");
			dateDeb = new Element("DateDeb");
			dateFin = new Element("DateFin");
			salleId = new Element("SalleId");
			
			reservation.addContent(reservationId);
			reservation.addContent(id);
			reservation.addContent(dateDeb);
			reservation.addContent(dateFin);
			reservation.addContent(salleId);
			reservations.addContent(reservation);
			
			reservationId.addContent(reservId.toString());
			id.addContent(edtActuel.getProfesseurId(reservId));
			dateDeb.addContent(dateToString(edtActuel.getDateDeb(reservId)));
			dateFin.addContent(dateToString(edtActuel.getDateFin(reservId)));
			salleId.addContent(String.valueOf(edtActuel.getSalleId(reservId)));
		}
		return reservations;
	}
	
	/**
	 * permet de transformer le format date en string
	 * 
	 * @param date
	 * @return la date en string
	 */
	public String dateToString (Date date) {
		SimpleDateFormat DATE = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
		return DATE.format(date);
	}
	
	/**
	 * permet de transformer le string en date
	 * @param string
	 * @return une date
	 */
	public Date stringToDate (String string) {
		SimpleDateFormat DATE = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date datee;
		try {
			datee = DATE.parse(string);
		} catch (Exception e) {
			datee = null;
		}
		return datee;
	}
	
	/**
	 * permet de sauvegarder la base de donnée
	 * @return le document sauvegardé
	 */
	public boolean saveDB() {
		Element racine = new Element ("TimeTablesDB");
		org.jdom2.Document document = new Document(racine);
		racine.addContent(this.saveSalles());
		racine.addContent(this.saveEDT());
		
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(this.getFile()));
			return true;
		} catch (java.io.IOException e) {
			return false;
		}

	}

	/**
	 * permet de charger la base de donnée
	 * @return reussite (un bool)
	 */
	public boolean loadDB() {
		org.jdom2.Document document = null;
		Element elt;
		SAXBuilder builder = new SAXBuilder();
		boolean reussite = true;

		try {
			document = builder.build(new File(this.getFile()));
		} catch (Exception e) {
			reussite = false;
		}

		if (document != null && reussite) {
			elt = document.getRootElement();

			if (elt.getName() == "EDTs") {
				reussite = loadSalles(elt.getChild("Salles")) && loadEDT(elt.getChild("EDTs"));
			} else
				reussite = false;
		}

		return reussite;
	}

	/**
	 * permet de charger les salles 
	 * @param elementSalle
	 * @return reussite (un bool)
	 */
	private boolean loadSalles(Element elementSalle) {
		int id, capacite;
		boolean reussite = true;
		java.util.List<Element> elementSalles;

		if (elementSalle == null)
			reussite = false;
		else {
			elementSalles = elementSalle.getChildren("Salle");

			for (Element salle : elementSalles) {
				id = Integer.parseInt(salle.getChildText("salleId"));
				capacite = Integer.parseInt(salle.getChildText("capacite"));
				this.salles.put(id, new Salle(id, capacite));
			}
		}
		return reussite;
	}

	/**
	 * permet de charger les emplois du temps
	 * @param elementEDT
	 * @return reussite (un bool)
	 */
	private boolean loadEDT(Element elementEDT) {
		int edtId;
		Hashtable<Integer, Reservation> reservations;
		java.util.List<Element> elementEDTs;
		boolean reussite = true;

		if (elementEDT == null)
			reussite = false;
		else {
			elementEDTs = elementEDT.getChildren("TimeTable");
			for (Element edt : elementEDTs) {
				edtId = Integer.parseInt(edt.getChildText("GroupId"));
				reservations = new Hashtable<Integer, Reservation>();
				reussite = loadReservation(edt.getChild("Reservations"), reservations, edtId);
				this.EDT.put(edtId, new TimeTable(edtId, reservations));
			}
		}
		return reussite;
	}

	/**
	 * permet de charger les reservations contenues dans la base de donnée
	 * @param elementReservation
	 * @param reservations
	 * @param edtId
	 * @return reussite (un bool)
	 */
	private boolean loadReservation(Element elementReservation, Hashtable<Integer, Reservation> reservations, int edtId) {
		int reservationId, salleId;
		String id;
		Date dateDeb, dateFin;
		boolean reussite = true;
		java.util.List<Element> elementReservations;

		if (elementReservation == null)
			reussite = false;
		else {
			elementReservations = elementReservation.getChildren("Reservation");

			for (Element reservation : elementReservations) {
				dateDeb = stringToDate(reservation.getChildText("DateDeb"));
				dateFin = stringToDate(reservation.getChildText("DateFin"));
				reservationId = Integer.parseInt(reservation.getChildText("ReservationId"));
				id = reservation.getChildText("Id");
				salleId = Integer.parseInt(reservation.getChildText("SalleId"));

				reservations.put(reservationId, new Reservation(reservationId, edtId, id, dateDeb, dateFin, salleId));
			}
		}
		return reussite;
	}
}
