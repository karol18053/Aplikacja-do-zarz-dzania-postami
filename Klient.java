package mongo;

import java.util.Iterator;
import java.util.Scanner;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class Klient {

	public static void main(String[] args) {

		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		MongoClient mongo = new MongoClient("localhost", 27017);

		System.out.println("Apliakcja do zarz�dzania postami z wyko�ystaniem MongoDB");
		// System.out.println("Podaj nazw� bazy");

		Scanner skan = new Scanner(System.in);
		// String nazwa_bazy = skan.nextLine();

		try {

			// Creating a Mongo client
			// MongoClient mongo = new MongoClient("localhost", 27017);

			// Creating Credentials
			MongoCredential credential;
			credential = MongoCredential.createCredential("karol", "postydb", "karol1".toCharArray());
			System.out.println("Po��czono z baz�");

			database = mongo.getDatabase("postydb");

			// System.out.println("Poswiadczenia ::" + credential);
			// mongo.close();
		} catch (MongoClientException e) {

			System.out.println("B��d: " + e);
		}

		Scanner scanner = new Scanner(System.in);
		int wybor;

		do {
			System.out.println("Wybierz co chcesz zrobi�:");//
			System.out.println("0. Wstaw now� kolekcj�");// dzia�a
			System.out.println("1. Wy�wietl wszystkie posty");// dzia�a
			System.out.println("2. Wstaw nowy post");// dzia�a
			System.out.println("3. Zaktualizuj wybranego posta");// dzia��
			System.out.println("4. Usu� wybrany post");// dzia�a+/-
			System.out.println("5. Znajdz wybarny post");// dzia��
			System.out.println("6. Sortuj posty malej�co");//dzia�a
			System.out.println("9. Zamknij");// dzia��

			wybor = scanner.nextInt();

			switch (wybor) {
			// Utw�rz now� kolekcj�
			case 0:
				System.out.println("Wpisz nazw� nowej kolekcji: ");
				String nazwa_kolekcji = skan.nextLine();
				database.createCollection(nazwa_kolekcji);
				System.out.println("Kolekcja utwotrzona");

				break;
			// Poka� wszystkie posty
			case 1:
				System.out.println("Wy�wietl wszystkie posty");
				System.out.println("Wpisz nazw� szukanej kolekcji: ");
				String nazwa_kolekcji1 = skan.nextLine();
				// Retrieving a collection
				collection = database.getCollection(nazwa_kolekcji1);
				System.out.println("Kolekcja " + nazwa_kolekcji1 + " wybrana");

				// Getting the iterable object
				FindIterable<Document> iterDoc_p = collection.find();
				int i_p = 1;

				// Getting the iterator
				Iterator<Document> it_p = iterDoc_p.iterator();

				while (it_p.hasNext()) {
					System.out.println(it_p.next());

					i_p++;
				}
				break;
			// Wybierz istniej�c� kolekcj�, wstaw posta
			case 2:
				System.out.println("Wstaw nowy post");
				System.out.println("Wpisz nazw� szukanej kolekcji: ");
				String nazwa_kolekcji2 = skan.nextLine();
				// Retieving a collection
				collection = database.getCollection(nazwa_kolekcji2);
				System.out.println("Kolekcja " + nazwa_kolekcji2 + " wybrana");

				System.out.println("Podaj dane do dokumentu: tre��, tagi, autor");

				FindIterable<Document> iterDoc_update_w = collection.find();
				int i_update_w = 1;

				// Getting the iterator
				Iterator<Document> it_update_w = iterDoc_update_w.iterator();

				while (it_update_w.hasNext()) {
					it_update_w.next();
					i_update_w++;
				}

				String tresc = skan.nextLine();
				String tagi = skan.nextLine();
				String autor = skan.nextLine();
				// String nic = skan.nextLine();

				Document document2 = new Document().append("post", tresc).append("tag", tagi).append("autor", autor)
						.append("numer", i_update_w);
				collection.insertOne(document2);
				System.out.println("Post wstawiony");
				i_update_w++;

				break;
			// Zaktualizuj wybrany dokument
			case 3:
				System.out.println("Zaktualizuj wybranego posta");
				System.out.println("Wpisz nazw� szukanej kolekcji: ");
				String nazwa_kolekcji3 = skan.nextLine();
				// Retrieving a collection
				collection = database.getCollection(nazwa_kolekcji3);
				System.out.println("Kolekcja " + nazwa_kolekcji3 + " wybrana");

				System.out.println("Wpisz dan� jak� chcesz zmieni� (post, tag, autor): ");
				String kolumna = skan.nextLine();
				System.out.println("Wpisz now� warto��: ");
				String wartosc = skan.nextLine();
				System.out.println("Wpisz numer szukanego postu: ");
				int numer = skan.nextInt();
				collection.updateOne(Filters.eq("numer", numer), Updates.set(kolumna, wartosc));
				System.out.println("Post zaktualizowany");

				// Retrieving the documents after updation
				// Getting the iterable object
				FindIterable<Document> iterDoc_update = collection.find();
				int i_update = 1;

				// Getting the iterator
				Iterator<Document> it_update = iterDoc_update.iterator();

				while (it_update.hasNext()) {
					System.out.println(it_update.next());
					i_update++;
				}

				break;
			// Usu� wybrany dokument
			case 4:
				System.out.println("Usu� wybrany post: ");
				System.out.println("Wpisz nazw� szukanej kolekcji: ");
				String nazwa_kolekcji4 = skan.nextLine();
				// Retrieving a collection
				collection = database.getCollection(nazwa_kolekcji4);
				System.out.println("Kolekcja " + nazwa_kolekcji4 + " wybrana");
				System.out.println("Wpisz numer szukanego postu do usuni�cia: ");
				int numer_delete = skan.nextInt();
				// Deleting the documents
				collection.deleteOne(Filters.eq("numer", numer_delete));
				System.out.println("Post usuni�ty");

				// Retrieving the documents after updation
				// Getting the iterable object
				FindIterable<Document> iterDoc_delete = collection.find();
				int i_delete = 1;

				// Getting the iterator
				Iterator<Document> it_delete = iterDoc_delete.iterator();

				while (it_delete.hasNext()) {
					System.out.println("Post wstawiony: " + i_delete);
					System.out.println(it_delete.next());
					i_delete++;
				}
				break;
			// Znajdz wybran� kolekcj� i dane
			case 5:
				System.out.println("Znajdz wybrany post: ");
				System.out.println("Wpisz nazw� kolekcji do znalezienia: ");
				String nazwa_kolekcji5 = skan.nextLine();
				System.out.println("Kolekcja " + nazwa_kolekcji5 + " wybrana");
				System.out.println("Wpisz nazw� pola: ");
				String nazwa_pola = skan.nextLine();
				System.out.println("Wpisz szukan� fraze: ");
				String nazwa_szukana = skan.nextLine();

				// Getting the iterable object
				collection = database.getCollection(nazwa_kolekcji5);
				// DBCollection table = database.getCollection("Klienci");
				BasicDBObject searchQuery = new BasicDBObject();
				searchQuery.put(nazwa_pola, nazwa_szukana);
				FindIterable<Document> iterDoc1 = collection.find(searchQuery);
				int ii = 1;
				// collection.find((Bson) iterDoc1);

				// Getting the iterator
				Iterator<Document> it1 = iterDoc1.iterator();

				while (it1.hasNext()) {
					System.out.println(it1.next());

					ii++;
				}

				break;
			// Sortuj wszystkie kolekcje
			case 6:
				System.out.println("Sortuj posty malej�co: ");
				System.out.println("Wpisz nazw� kolekcji: ");
				String nazwa_kolekcji6 = skan.nextLine();
				System.out.println("Kolekcja " + nazwa_kolekcji6 + " wybrana");
				System.out.println("Wpisz nazw� pola wg kt�rego b�dze wykonane sortowanie: ");
				String nazwa_sort = skan.nextLine();

				collection = database.getCollection(nazwa_kolekcji6);

				FindIterable<Document> iterDoc_sort = collection.find();

				// sorting the cursor based in descending order based on speed
				// field
				iterDoc_sort.sort(new BasicDBObject(nazwa_sort, -1));

				MongoCursor<Document> iterator = iterDoc_sort.iterator();
				try {
					while (iterator.hasNext()) {
						System.out.println(iterator.next());
					}
				} finally {
					iterator.close();
				}

				System.out.println("Zrobione");

				break;

			case 9:
				System.out.println("Ko�czenie pracy aplikacji... ");
				mongo.close();
				System.exit(0);
			default:

				System.out.println("Co� posz�o nie tak...");

			}

		} while (wybor != 0);
		scanner.close();
		skan.close();

	}

}
