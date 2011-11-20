package core;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import postboardIO.RssReader;
import postboardIO.RssWriter;

import data.Feedback;
import data.Post;
 

public class Connettore{

	private String address1;
	private String address2;
	private String author1;
	private String author2;
	private String alias1;
	private String alias2;
	private ArrayList<String> tags;
	
	private RssReader r1;
	private RssReader r2;
	private RssWriter w1;
	private RssWriter w2;
	
	public Connettore(String address1, String address2, String author1,
			String author2, String alias1, String alias2, ArrayList<String> tags) {
		super();
		
		this.address1 = address1;
		this.address2 = address2;
		this.author1 = author1;
		this.author2 = author2;
		this.alias1 = alias1;
		this.alias2 = alias2;
		this.tags = tags;
		
		r1 = new RssReader(address1, author2, tags);//author2
		w1 = new RssWriter(address2, alias2, author1);//author2
		
		r2 = new RssReader(address2, author1,tags);//author1
		w2 = new RssWriter(address1, alias1, author2);//author1
	}	
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAuthor1(String author1) {
		this.author1 = author1;
	}
	public String getAuthor1() {
		return author1;
	}
	public void setAuthor2(String author2) {
		this.author2 = author2;
	}
	public String getAuthor2() {
		return author2;
	}
	public void setAlias1(String alias1) {
		this.alias1 = alias1;
	}
	public String getAlias1() {
		return alias1;
	}
	public void setAlias2(String alias2) {
		this.alias2 = alias2;
	}
	public String getAlias2() {
		return alias2;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	
	public boolean federate(){
		boolean res = true;
		ArrayList<Post> posts1 = r1.readPosts();
		ArrayList<Post> posts2 = r2.readPosts();
		
		if(posts1!=null){
			Iterator<Post> it = posts1.iterator();
			while(it.hasNext()){
				Post post = (Post) it.next();
				res = w1.writePost(post,r2,true);
			}
		}else res =false;
		
		if(posts2!=null&&res!=false){
			Iterator <Post> it = posts2.iterator();
			while(it.hasNext()){
				Post post = (Post) it.next();
				res = w2.writePost(post,r1,true);
			}
		}else res=false;
		
		return res;
	}
	
	public ArrayList<Post> readPosts(int bacheca){
		ArrayList<Post> res=null;
		if (bacheca>1){
			res = r2.readAllPosts();
		}
		else {
			res = r1.readAllPosts();
		}
		return res;
	}
	public boolean writePost(int bacheca, Post p){
		if (bacheca>1)
			return w1.writePost(p, r2,false);
		else 
			return w2.writePost(p, r1,false);
	}
	public ArrayList<Feedback> readFeedbacks(int bacheca, Post p){
		ArrayList<Post> lista=new ArrayList<Post>();
		if (bacheca>1){
			lista= readPosts(2);
		}
		else {
			lista= readPosts(1);
		}
		Iterator<Post> it=lista.iterator();
		while (it.hasNext()){
			Post x=it.next();
			if (p.equals(x))
				return x.getFeedbacks();
		}
		return new ArrayList<Feedback>();
	}
	public boolean writeFeedback(int bacheca, Post p, Feedback f){
		ArrayList<Post> lista=new ArrayList<Post>();
		RssWriter w=null;
		if (bacheca>1){
			lista= readPosts(2);
			w=w1;
		}
		else {
			lista= readPosts(1);
			w=w2;
		}
		Iterator<Post> it=lista.iterator();
		while (it.hasNext()){
			Post x=it.next();
			if (p.equals(x)){
				f.setFeedbackname(x.getId());
				return w.writeFeedback(f,false);
			}
		}
		return false;
		
	}
	
	public static void main(String[] args) {
		
		
		/*if(args.length<7){
			System.out.println("Usage connettore: [Url_VirtualBoard1] [Url_VirtualBoard2] [Default_Author_VB1] [Default_Author_VB2] [Alias_VB1] [Alias_VB2] [Refresh_Timer_Virtualboard] [Tag1 if Exists] [Tag2 if exists] [TagN if exists]..");
		}
		else{
			String url_vb1=args[0];
			String url_vb2=args[1];
			String author1=args[2];
			String author2=args[3];
			String alias1=args[4];
			String alias2=args[5];
			int timer=Integer.parseInt(args[6])*1000;
			ArrayList<String> al = new ArrayList<String>();
			for(int i = 8; i<args.length; i++){
				al.add(args[i]);
			}
			Connettore c = new Connettore(url_vb1, url_vb2, author1, author2, alias1, alias2, al);
			Timer t = new Timer();
			t.schedule(c, new Date(0), timer);
		}*/
		java.io.BufferedReader console = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		String url_vb1="";
		String url_vb2="";
		String author1="";
		String author2="";
		String alias1="";
		String alias2="";
		String yn="";
		String tag="";
		ArrayList<String> lista=new ArrayList<String>();
		boolean ancora=true;
		boolean repeat=true;
		boolean inserisci=true;
		boolean ok=true;
		boolean inserisciParametri=true;
		Connettore c=new Connettore("default1","default2","default3","default4","default5","default6",new ArrayList<String>()); 
		System.out.println("Questo programma federa due bacheche virtuali.");
		while (repeat){
			if (inserisciParametri){
				System.out.print("Inserisci il link della prima bacheca: ");
				while (inserisci){
					try {
						url_vb1 = console.readLine();
						if (url_vb1==null) throw new IOException();
						if (url_vb1.isEmpty()) throw new IOException();
						inserisci=false;
					} catch (IOException e) {
						System.out.print("Errore nell'inserimento dell'indirizzo della prima bacheca, inseriscilo di nuovo: ");
						}
				}
				inserisci=true;
				System.out.print("Inserisci il link della seconda bacheca: ");			
				while (inserisci){
					try {
						url_vb2 = console.readLine();
						if (url_vb2==null) throw new IOException();
						if (url_vb2.isEmpty()) throw new IOException();
						inserisci=false;
					} catch (IOException e) {
						System.out.print("Errore nell'inserimento dell'indirizzo della seconda bacheca, inseriscilo di nuovo: ");
						}
				}
				inserisci=true;
				System.out.print("Inserisci l'autore per la prima bacheca: ");
				while (inserisci){
					try {
						author1 = console.readLine();
						if (author1==null) throw new IOException();
						if (author1.isEmpty()) throw new IOException();
						inserisci=false;
					} catch (IOException e) {
						System.out.print("Errore nell'inserimento dell'autore per la prima bacheca, inseriscilo di nuovo: ");
						}
				}
				inserisci=true;
				System.out.print("Inserisci l'autore per la seconda bacheca: ");
				while (inserisci){
					try {
						author2 = console.readLine();
						if (author2==null) throw new IOException();
						if (author2.isEmpty()) throw new IOException();
						inserisci=false;
					} catch (IOException e) {
						System.out.print("Errore nell'inserimento dell'autore per la seconda bacheca, inseriscilo di nuovo: ");
						}
				}
				inserisci=true;
				System.out.print("Inserisci l'alias per la prima bacheca: ");
				while (inserisci){
					try {
						alias1 = console.readLine();
						if (alias1==null) throw new IOException();
						if (alias1.isEmpty()) throw new IOException();
						inserisci=false;
					} catch (IOException e) {
						System.out.print("Errore nell'inserimento dell'alias per la prima bacheca, inseriscilo di nuovo: ");
						}
				}
				inserisci=true;
				System.out.print("Inserisci l'alias per la seconda bacheca: ");
				while (inserisci){
					try {
						alias2 = console.readLine();
						if (alias2==null) throw new IOException();
						if (alias2.isEmpty()) throw new IOException();
						inserisci=false;
					} catch (IOException e) {
						System.out.print("Errore nell'inserimento dell'alias per la seconda bacheca, inseriscilo di nuovo: ");
						}
				}
				System.out.print("Vuoi filtrare i post da federare? (Y/N) ");
				inserisci=true;
				while (inserisci){
					try {
						yn = console.readLine();
						if (yn==null) throw new IOException();
						if (yn.isEmpty()) throw new IOException();
						if (!((yn.toUpperCase().equals("Y"))||(yn.toUpperCase().equals("N")))) throw new IOException();
						inserisci=false;
					} catch (IOException e) {
						System.out.print("Errore nell'inserimento della risposta, inseriscila di nuovo: ");
						}
				}
				if (yn.toUpperCase().equals("Y")){
					while (ancora){
						inserisci=true;
						System.out.print("Inserisci una parola per aggiungerla come tag per il filtraggio oppure " +
								"direttamente invio per avviare la federazione: ");
						while (inserisci){
							try {
								tag = console.readLine();
								if (tag==null) throw new IOException();
								inserisci=false;
							} catch (IOException e) {
								System.out.print("Errore nell'inserimento del tag, inseriscilo di nuovo: ");
								}
						}
						if (tag.isEmpty()) ancora=false; 
						else lista.add(tag);
					}
				}
				inserisciParametri=false;
				c = new Connettore(url_vb1, url_vb2, author1, author2, alias1, alias2, lista);
			}
			ok=c.federate();
			if (ok) System.out.println("Federazione avvenuta con successo.");
			else System.out.println("Ci sono stati errori nella federazione.");
			inserisci=true;
			System.out.println("Vuoi avviare di nuovo la federazione? (Y/N) ");
			while (inserisci){
				try {
					yn = console.readLine();
					if (yn==null) throw new IOException();
					if (yn.isEmpty()) throw new IOException();
					if (!((yn.toUpperCase().equals("Y"))||(yn.toUpperCase().equals("N")))) throw new IOException();
					inserisci=false;
				} catch (IOException e) {
					System.out.print("Errore nell'inserimento della risposta, inseriscila di nuovo: ");
					}
			}
			if (yn.toUpperCase().equals("N"))
				break;
			else {
				inserisci=true;
				System.out.print("Vuoi cambiare i parametri?: ");
				while (inserisci){
					try {
						yn = console.readLine();
						if (yn==null) throw new IOException();
						if (yn.isEmpty()) throw new IOException();
						if (!((yn.toUpperCase().equals("Y"))||(yn.toUpperCase().equals("N")))) throw new IOException();
						inserisci=false;
					} catch (IOException e) {
						System.out.print("Errore nell'inserimento della risposta, inseriscila di nuovo: ");
						}
				}
				if (yn.toUpperCase().equals("Y")) inserisciParametri=true;
			}
		}
			System.out.println("Il programma è terminato correttamente");
	}
}
