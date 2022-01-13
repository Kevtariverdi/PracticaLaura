package Clases;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.internal.build.AllowSysOut;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




public class clasePrincipal {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int num=-1;

		System.out.println("Bienvenido");
		
		
		do {	
			System.out.println("1º Escribir fichero XML");
			System.out.println("\n2º Leer fichero XML");
			System.out.println("\n3º Insertar jugador");
			System.out.println("\n4º Actualizar jugador");
			System.out.println("\n5º Insertar equipo");
			System.out.println("\n6º Actualizar equipo");
			System.out.println("\n7º Eliminar jugador");
			System.out.println("\n8º Eliminar equipo");
			System.out.println("\n9º Recuperar jugadores y equipos");
			System.out.println("\n10º Consultar un equipo");
			System.out.println("\n11º Consultar un jugador");

			
			num=sc.nextInt();

			switch (num){

			case 1:
				xmlEquipos();
				break;
				
			case 2:
				leerficheroXml();
				break;
				
			case 3:
				insertarJugadores();
				break;		
				
			case 4:
				actualizarJugador();
				break;
				
			case 5:
				insertarEquipo();
				break;
				
			case 6:
				actualizarEquipo();
				break;
				
			case 7:
				eliminarJugador();
				break;
				
			case 8:
				eliminarEquipo();
				break;
				
			case 9:
				RecuperarJugadoresYEquipos();
				break;
				
			case 10:
				ConsultarUnEquipo();
				break;
			
			case 11:
				ConsultarUnJugador();
				break;
				
			default:
				if (num!=0) 
					System.out.println("No es una opcion de menu correcta, si deseas finalizar pulsa 0");
			}


		}while(num!=0);
		num=sc.nextInt();

	}

	private static void ConsultarUnJugador() {
		// TODO Auto-generated method stub
		
	}

	private static void ConsultarUnEquipo() {
		// TODO Auto-generated method stub
		
	}

	private static void leerficheroXml() {
		// TODO Auto-generated method stub
		
	}

	public static Session session() {
		Session session = utilConfiguration.getSessionFactory().openSession();
		return session;

	}

	private static void xmlEquipos() {
		Session session =session();
		session.beginTransaction();
		List<Jugadores> result = (List<Jugadores>) session.createQuery("from Jugadores").list();
		session.getTransaction().commit();

		

		try {

			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;


			dBuilder = dFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Element elementoprincipal= doc.createElement("Jugadores");
			doc.appendChild(elementoprincipal);
			
			for(Jugadores jug : result) {
				

				Node elementoJugador = doc.createElement("Jugador"); 
				((Element)elementoJugador).setAttribute("id_jugador", Integer.toString(jug.getIdJugador()));

				Node equipo = doc.createElement("Equipo");
				Node equipo1 = doc.createTextNode(jug.getEquipos().getNombre().toString());
				equipo.appendChild(equipo1);

				Node Nombre  = doc.createElement("Nombre");
				Node nombre1 = doc.createTextNode(jug.getNombre().toString());
				Nombre.appendChild(nombre1);

				Node posicion = doc.createElement("Posicion");
				Node posicion1= doc.createTextNode(jug.getPosicion().toString());
				posicion.appendChild(posicion1);

				Node peso = doc.createElement("Peso");
				Node peso1= doc.createTextNode(jug.getPeso().toString());
				peso.appendChild(peso1);
				
				Node fecha = doc.createElement("Fecha_Alta");
				Node fecha1= doc.createTextNode(jug.getFechaAlt().toString());
				fecha.appendChild(fecha1);
				
				elementoJugador.appendChild(equipo);
				elementoJugador.appendChild(Nombre);
				elementoJugador.appendChild(posicion);
				elementoJugador.appendChild(fecha);
				elementoJugador.appendChild(posicion);
				elementoJugador.appendChild(fecha);
				

				elementoprincipal.appendChild(elementoJugador);
			}   
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domsource = new DOMSource(doc);
			StreamResult domresult = new StreamResult(new File("Jugadores.xml"));

			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(domsource, domresult);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	private static void eliminarEquipo() {
		Session session = session();
		Scanner sc = new Scanner(System.in);
	
		System.out.println("\nIntroduce el id del equipo que quieres eliminar");
		int id=sc.nextInt();

		Transaction tx = null;
		tx = session.beginTransaction();

		Equipos eq= (Equipos) session.get(Equipos.class, id); 
		eq.setIdEquipo(id);
		session.delete(eq);
		System.out.println("Departaamentos " + id + ","+ eq.getNombre()+  " eliminado");

		tx.commit();
		session.getTransaction().commit();
		session.close();
	}
	private static void eliminarJugador() {
		Session session = session();
		Scanner sc = new Scanner(System.in);
		System.out.println("\nIntroduce el id del jugador que quieres eliminar");
		int id=sc.nextInt();
		
		
		Transaction tx = null;
		tx = session.beginTransaction();

		Jugadores jug = (Jugadores) session.get(Jugadores.class, id); //Siempre poner esto!!!
		jug.setIdJugador(id);
		session.delete(jug);
		
		System.out.println("Jugador " + id +  "," + jug.getNombre() +" eliminado");

		tx.commit();
		session.getTransaction().commit();
		session.close();


	}

	
	private static void actualizarEquipo() {
		
		Session session = session();
		Scanner sc = new Scanner(System.in);
		session.beginTransaction();
		
		System.out.println("\nIntroduce el id que quieres actualizar");
		int id=sc.nextInt();
		Equipos eq = (Equipos) session.get(Equipos.class, id); 
		
		System.out.println("Introduce el nombre de la ciudad");
		eq.setNombre(sc.next());
		System.out.println("Introduce el nombre de la ciudad");
		eq.setCiudad(sc.next());
		System.out.println("Introduce la division del equipo");
		eq.setDivision(sc.next());
		

		session.save(eq);
		session.getTransaction().commit();
		session.close();
		
	}

	private static void actualizarJugador() {
	
		Session session = session();
		Scanner sc = new Scanner(System.in);
		Date fecha = new Date();
		
		System.out.println("\nIntroduce el id del jugador que quieres actualizar.");
		int id= sc.nextInt();


		Jugadores jug = (Jugadores) session.get(Jugadores.class, id); 
		

		System.out.println("Introduce el nombre del jugador");
		jug.setNombre(sc.next());
		System.out.println("Introduce la posicion del jugador");
		jug.setPosicion(sc.next());
		System.out.println("Introduce el peso");
		jug.setPeso(sc.nextFloat());
		jug.setFechaAlt(fecha);
	
		session.save(jug);
		session.getTransaction().commit();
		session.close();
		
	}
	

	private static void insertarJugadores() {
		Scanner sc = new Scanner(System.in);
		Date fecha = new Date();
		
		Equipos eq = new Equipos();
		Jugadores jug = new Jugadores();
		
		Session session = session();
		session.beginTransaction();
		
		System.out.println("Introduce el id del equipo del que pertenece");
		eq.setIdEquipo(sc.nextInt());
		
		System.out.println("Introduce el idjugador");
		jug.setIdJugador(sc.nextInt());
		System.out.println("Introduce el nombre del jugador");
		jug.setNombre(sc.next());
		System.out.println("Introduce la posicion del jugador");
		jug.setPosicion(sc.next());
		System.out.println("Introduce el peso");
		jug.setPeso(sc.nextFloat());
		
		jug.setFechaAlt(fecha);
		
		jug.setEquipos(eq);
		
		
		session.save(jug);
		session.getTransaction().commit();
		session.close();
		
	}
	private static void insertarEquipo() {
		Scanner sc = new Scanner(System.in);
		Equipos eq= new Equipos();
		Jugadores j = new Jugadores();
		
		Session session = session();
		session.beginTransaction();
		
		
		System.out.println("Introduce el id del equipo");
		eq.setIdEquipo(sc.nextInt());
		System.out.println("Introduce el nombre de la ciudad");
		eq.setNombre(sc.next());
		System.out.println("Introduce el nombre de la ciudad");
		eq.setCiudad(sc.next());
		System.out.println("Introduce la division del equipo");
		eq.setDivision(sc.next());
		

		session.save(eq);
		session.getTransaction().commit();
		session.close();

	}
	
	private static void RecuperarJugadoresYEquipos() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Que quieres imprimir \n1º Jugadores \n2º Equipos");
		int num = sc.nextInt();
		
		switch(num) {
			case 1:
				Session session = session();
				try {

					session.beginTransaction();
					@SuppressWarnings("unchecked")

					List<Jugadores> result = (List<Jugadores>) session.createQuery("from Jugadores").list();
					session.getTransaction().commit();
					for(Jugadores jug: result) {

					System.out.println(jug.getIdJugador());
					System.out.println(jug.getEquipos());
					System.out.println(jug.getNombre());
					System.out.println(jug.getPosicion());
					System.out.println(jug.getPeso());
					System.out.println(jug.getFechaAlt());
					
					
					}
				}
				catch(Exception e) {
					
					System.out.println(e.getMessage());
				}
				break;
				
			case 2:
				try {
					Session session1 = session();
					session1.beginTransaction();
					@SuppressWarnings("unchecked")

					List<Equipos> result = (List<Equipos>) session1.createQuery("from Equipos").list();
					session1.getTransaction().commit();
					for(Equipos eq: result) {

					System.out.println(eq.getIdEquipo());
					System.out.println(eq.getNombre());
					System.out.println(eq.getCiudad());
					System.out.println(eq.getDivision());
					
					
					}
				}
				catch(Exception e) {
					System.out.println("Mascota con id duplicado");
					System.out.println(e.getMessage()); 
			
				}
				break;
		}
	
	}

	}


