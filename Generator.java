package generator;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Generator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MongoDatabase database = null;
		MongoCollection<Document> collection = null;
		MongoClient mongo = new MongoClient("localhost", 27017);

		String[] tresc1 = { "Dziœ", "Jutro", "Zaraz", "Rano", "Wieczorem" };
		String[] tresc2 = { "idê", "stojê", "jadê", "biegnê", "lecê" };
		String[] tresc3 = { "na", "do", "w", "pod", "za" };
		String[] tresc4 = { "pizze", "szko³e", "kina", "si³ownie", "spotkanie" };
		String[] tagi = { "jedzenie", "szko³a", "dzieñ", "¿ycie", "praca" };
		String[] autor = { "loka", "maja21", "gacu91", "persi54", "strzala34" };
		// "wiki5423", "master12", "posti9765", "pegaz43", "rezi5423" };

		Scanner skan = new Scanner(System.in);

		try {

			MongoCredential credential;
			credential = MongoCredential.createCredential("karol", "postydb", "karol1".toCharArray());
			System.out.println("Po³¹czono z baz¹");
			database = mongo.getDatabase("postydb");

		} catch (MongoClientException e) {

			System.out.println("B³¹d: " + e);
		}

		System.out.println("Generator postów, podaj liczbê postów do wygenerowania");
		int n = skan.nextInt();

		collection = database.getCollection("posty");
		////////////////
		FindIterable<Document> iterDoc_update = collection.find();
		int i_update = 1;

		// Getting the iterator
		Iterator<Document> it_update = iterDoc_update.iterator();

		while (it_update.hasNext()) {
			it_update.next();
			i_update++;
		}
		// System.out.println(i_update);
		//////////////////
		Random r = new Random();

		for (int i = 1; i <= n; i++) {

			int numer1 = r.nextInt(4);
			int numer2 = r.nextInt(4);
			int numer3 = r.nextInt(4);
			int numer4 = r.nextInt(4);
			String tresc = tresc1[numer1] + " " + tresc2[numer2] + " " + tresc3[numer3] + " " + tresc4[numer4];

			Document document2 = new Document().append("post", tresc).append("tag", tagi[numer4])
					.append("autor", autor[numer1]).append("numer", i_update);
			collection.insertOne(document2);
			System.out.println("Post wstawiony");

			i_update++;

		}
		skan.close();
		mongo.close();

	}

}
