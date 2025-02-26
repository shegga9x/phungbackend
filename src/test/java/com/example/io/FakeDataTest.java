package com.example.io;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class FakeDataTest {

	// @Test
	// public void testReadFileFromResources() throws IOException {
	// Path path = Paths.get("src/main/resources/fake-data/author.sql");
	// List<String> lines = Files.readAllLines(path);
	// StringBuilder stringBuilder = new StringBuilder();
	// for (int i = 1; i < lines.size(); i++) {
	// String line = lines.get(i);
	// int startIndex = line.indexOf('(') + 1;
	// int endIndex = line.indexOf(',', startIndex);
	// // Extract the id and add it to the StringBuilder
	// String id = line.substring(startIndex, endIndex).trim();
	// stringBuilder.append(id).append(System.lineSeparator());
	// }
	// // Write the ids to a txt file
	// Path outputPath = Paths.get("src/main/resources/fake-data/authorIDs.txt");
	// Files.write(outputPath, stringBuilder.toString().getBytes());
	// }

	@Test
	public void testReadFileAndAddToList() throws IOException {
		Path pathID = Paths.get("src/main/resources/fake-data/bookIDs.txt");

		List<String> linesID = Files.readAllLines(pathID);
		List<String> ids = new ArrayList<>();
		for (int i = 1; i < linesID.size(); i++) {
			ids.add(linesID.get(i));
		}

		Path pathType = Paths.get("src/main/resources/fake-data/type.txt");
		List<String> linesType = Files.readAllLines(pathType);
		List<String> types = new ArrayList<>();
		for (int i = 1; i < linesType.size(); i++) {
			types.add(linesType.get(i));
		}
		// System.out.println(ids);
		// System.out.println(types);

		Faker faker = new Faker();
		Random random = new Random();

		String bookFile = "books.sql";
		String authorFile = "authors.sql";
		String bookAuthorsFile = "book_authors.sql";
		int numBooks = 2_000_000;
		try (FileWriter bookWriter = new FileWriter(bookFile);
				FileWriter authorWriter = new FileWriter(authorFile);
				FileWriter bookAuthorWriter = new FileWriter(bookAuthorsFile)) {

			// Generate Books and Book-Author Relationships
			bookWriter.write(
					"INSERT INTO books (id, title, subtitle, price, url_img, type, published_at, stock) VALUES\n");
			bookAuthorWriter.write("INSERT INTO book_authors (book_id, author_id) VALUES\n");

			for (int i = 8139; i <= numBooks; i++) {
				String title = faker.book().title().replace("'", "''");
				String subtitle = faker.lorem().sentence(5).replace("'", "''");
				double price = Math.round((5 + random.nextDouble() * 95) * 100.0) / 100.0;
				String urlImg = "https://itbook.store/img/books/" + ids.get(random.nextInt(ids.size())) + ".png";
				String bookType = types.get(random.nextInt(types.size()));
				String publishedAt = faker.date().birthday(10, 50).toInstant().toString().replace("T", " ").substring(0, 19);
				int stock = random.nextInt(50) + 1;

				bookWriter.write(String.format("(%d, '%s', '%s', %.2f, '%s', '%s', '%s', %d)%s\n",
						i, title, subtitle, price, urlImg, bookType, publishedAt, stock, (i < numBooks ? "," : ";")));

				// Assign 1 to 3 random authors
				int numAuthorsPerBook = random.nextInt(3) + 1;
				Set<Integer> selectedAuthors = new HashSet<>();
				while (selectedAuthors.size() < numAuthorsPerBook) {
					selectedAuthors.add(random.nextInt(8791) + 1);
				}

				for (int authorId : selectedAuthors) {
					bookAuthorWriter.write(String.format("(%d, %d)%s\n",
							i, authorId, (i < numBooks ? "," : ";")));
				}
			}
			System.out.println("✅ Data generation complete! Files created: books.sql, authors.sql, book_authors.sql");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}