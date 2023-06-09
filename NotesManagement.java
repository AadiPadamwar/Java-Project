import java.io.*;
import java.util.*;

public class NotesManagement 
{
    // Define ANSI escape codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[96m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[91m";

    public static void main(String[] args) throws IOException 
    {
        Scanner sc = new Scanner(System.in);
        String directoryPath = "notes/";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        while (true) 
        {
            try {
                System.out.println(ANSI_CYAN + "\n========================================");
                System.out.println("|         " + ANSI_YELLOW + "Notes Management System" + ANSI_CYAN + "       |");
                System.out.println("========================================\n" + ANSI_RESET);
                System.out.println("Select " + ANSI_YELLOW + "1" + ANSI_RESET + " To create a new note");
                System.out.println("Select " + ANSI_YELLOW + "2" + ANSI_RESET + " To read an existing note");
                System.out.println("Select " + ANSI_YELLOW + "3" + ANSI_RESET + " To edit an existing note");
                System.out.println("Select " + ANSI_YELLOW + "4" + ANSI_RESET + " To delete an existing note");
                System.out.println("Select " + ANSI_YELLOW + "5" + ANSI_RESET + " To exit");
                System.out.println("----------------------------------------");
                System.out.println("");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) 
                {
                    case 1:
                        createNote(directoryPath, sc);
                        break;
                    case 2:
                        readNote(directoryPath, sc);
                        break;
                    case 3:
                        editNote(directoryPath, sc);
                        break;
                    case 4:
                        deleteNote(directoryPath, sc);
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println(ANSI_RED + "Invalid choice! Please try again." + ANSI_RESET);
                }
            } 
            catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Invalid choice! Please enter a number." + ANSI_RESET);
                sc.nextLine(); 
            }
        }
    }

    private static void createNote(String directoryPath, Scanner sc) throws IOException 
    {
        System.out.println(ANSI_CYAN + "\n========================================");
        System.out.println("|           " + ANSI_YELLOW + "Create a New Note" + ANSI_CYAN + "           |");
        System.out.println("========================================\n" + ANSI_RESET);
        System.out.println("Enter the name of the note:");
        System.out.println("----------------------------------------");
        String fileName = sc.next();
        File file = new File(directoryPath + fileName + ".txt");
        if (file.createNewFile()) {
            System.out.println(ANSI_YELLOW + "Note created successfully!" + ANSI_RESET);
        } 
        else {
            System.out.println(ANSI_RED + "Note with the same name already exists!" + ANSI_RESET);
            return;
        }
        System.out.println("---------------------------------------------------");
        System.out.println("\nEnter the new content of the note:");
        System.out.println("(Enter '#exit#' to save and go back to main menu)");
        System.out.println("---------------------------------------------------");
        FileWriter writer = new FileWriter(file);

        while (sc.hasNext())
        {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("#exit#")) {
                break;
            }
            writer.write(line);
            writer.write(System.lineSeparator());
        }
        writer.close();
        System.out.println(ANSI_YELLOW + "Note saved successfully!" + ANSI_RESET);
    }

    private static void readNote(String directoryPath, Scanner sc) throws IOException 
    {
        System.out.println(ANSI_CYAN + "\n========================================");
        System.out.println("|         " + ANSI_YELLOW + "Read an Existing Note" + ANSI_CYAN + "         |");
        System.out.println("========================================\n" + ANSI_RESET);
        System.out.println("Enter the name of the note:");
        System.out.println("----------------------------------------");
        String fileName = sc.next();
        System.out.println("");
        File file = new File(directoryPath + fileName + ".txt");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            int character;
            System.out.println(ANSI_CYAN + "----------------------------" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "The contents of the note is: " + ANSI_RESET);
            System.out.println(ANSI_CYAN + "----------------------------" + ANSI_RESET);
            System.out.println("");
            while ((character = reader.read()) != -1) {
                System.out.print((char) character);
            }
            reader.close();
        } 
        
        else {
            System.out.println(ANSI_RED + "Note with the given name does not exist!" + ANSI_RESET);
        }
    }

    private static void editNote(String directoryPath, Scanner sc) throws IOException 
    {
        System.out.println(ANSI_CYAN + "\n========================================");
        System.out.println("|         " + ANSI_YELLOW + "Read an Existing Note" + ANSI_CYAN + "         |");
        System.out.println("========================================\n" + ANSI_RESET);
        System.out.println("Enter the name of the note:");
        System.out.println("----------------------------------------");
        String fileName = sc.next();
        File file = new File(directoryPath + fileName + ".txt");
        if (file.exists()) 
        {
            FileReader reader = new FileReader(file);
            int character;
            StringBuilder content = new StringBuilder();
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            reader.close();
            System.out.println(content.toString());
            editNoteContent(file, content.toString(), sc);
        } 
        else {
            System.out.println(ANSI_RED + "Note with the given name does not exist!" + ANSI_RESET);
        }
    }


    private static void editNoteContent(File file, String content, Scanner sc) throws IOException 
    {
        FileWriter writer = new FileWriter(file);
        System.out.println("\nEnter the new content of the note:");
        System.out.println("----------------------------------------");
        System.out.println("(Enter '#exit#' to save and go back to main menu)");
        writer.write(sc.nextLine()); 
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("#exit#")) {
                break;
            }
            content = content.replaceFirst(line, "");
            writer.write(line);
            writer.write(System.lineSeparator());
        }
        writer.write(content);
        writer.close();
        System.out.println(ANSI_YELLOW + "Note edited successfully!" + ANSI_RESET);
    }


    private static void deleteNote(String directoryPath, Scanner sc) 
    {
        System.out.println(ANSI_CYAN + "\n========================================");
        System.out.println("|         " + ANSI_YELLOW + "Delete an Existing Note" + ANSI_CYAN + "       |");
        System.out.println("========================================\n" + ANSI_RESET);
        System.out.println("Enter the name of the note:");
        System.out.println("----------------------------------------");
        String fileName = sc.next();
        File file = new File(directoryPath + fileName + ".txt");
        if (file.exists()) 
        {
            if (file.delete()) {
                System.out.println(ANSI_YELLOW + "Note deleted successfully!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Failed to delete the note!" + ANSI_RESET);
            }
        } 
        else {
            System.out.println(ANSI_RED + "Note with the given name does not exist!" + ANSI_RESET);
        }
    }
}
