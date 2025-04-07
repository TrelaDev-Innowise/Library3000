# 📚 Library3000App

Library3000App is a simple console application for managing books, written in Java using Spring. Book data is stored in a CSV file.

## ✨ Features  
✅ Display the list of books  
✅ Add a new book  
✅ Edit a book  
✅ Delete a book  
✅ Search books by keyword  

## 🛠 Requirements  
🔹 Java 17+  
🔹 Gradle  

## 🚀 Installation & Running  

### 1️⃣ Open the Project  
You can open the project in an IDE (like IntelliJ IDEA or Eclipse). The testing  `books.csv` file is located in the `src/main/resources` folder. Also there is `books_backup.csv` there for more convinient testing

Alternatively, you can build the project and run the fat JAR. In that case, the `books.csv` file will be created automatically in the folder where the JAR is executed.  

### 2️⃣ Build the project  
First, compile the project and generate a fat JAR (with all dependencies):  

```sh
gradlew shadowJar
```

### 3️⃣ Run the application  
After building the project, run the application in the console:  

```sh
java -jar build/libs/Library3000-1.0-SNAPSHOT.jar
```

📌 **Note:** If the `books.csv` file does not exist, the application will automatically create it in the `src/main/resources` folder with some sample books.  

---

## 📂 Project Structure  

```bash
src/
 ├── main/
 │   ├── java/dev/trela/testing/
 │   │   ├── config/       # Spring configuration
 │   │   ├── model/        # Book class (data model)
 │   │   ├── repository/   # CSV file operations
 │   │   ├── service/      # Business logic
 │   │   ├── Library3000App.java # Main application class
 │   ├── resources/        # books.csv file (CSV database)
 ├── test/                 # Unit & integration tests
 ├── build/                # Output folder after building the application
```

---

## 📂 CSV File Format  
The `books.csv` file follows this format:  

```bash
id,title,author,description
1,Hobbit,"J.R.R. Tolkien","Fantasy novel about Bilbo Baggins"
2,1984,"George Orwell","Dystopian fiction about a totalitarian regime"
3,Dziady,"Adam Mickiewicz","Polish dramatic poem about spirits and the afterlife"
...
```

---

## 🎮 How to Use?  
After running the application, you will see the following menu:  

```sql
Choose an option:
1 - Display book list
2 - Create a new book
3 - Edit a book
4 - Delete a book
5 - Search by keyword
6 - Exit
Your choice:
```

🔹 **Search Feature:** Enter a keyword, and the program will display books whose title, author, or description contains the keyword.  

---

## 🧪 Testing  

The project includes **unit tests** and **integration tests**:  

✅ **Unit tests** verify individual components like repository and service logic.  
✅ **Integration tests** ensure the system works as expected when interacting with the CSV file.  

Run tests with:  

```sh
./gradlew test
```

---

## ⚙ Technologies Used  

🔹 **Java 17** – Programming language  
🔹 **Spring Context** – Component management and IoC  
🔹 **Jackson CSV** – Handling CSV files  
🔹 **JUnit 5** – Unit & integration testing  
🔹 **Gradle (Shadow Plugin)** – Creating a **fat JAR**  
