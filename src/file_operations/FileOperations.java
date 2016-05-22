package file_operations;// figure out your imports

import java.io.*;
import java.util.*;

public class FileOperations {
    StringTokenizer parseCommand;

    String fileForAction = "";
    File path;


    public void delete() {
        boolean returnCode = false;
        if (path.exists()) {
            System.out.println("Deleting: " + path.getAbsolutePath());
            returnCode = path.delete();

        } else {

            System.out.println("File does not exist: " + path);
        }

        if (returnCode == true) {
            System.out.println("Successful Deletion");
        } else {
            System.out.println("Failed to Delete.");
        }

        printSeperator();

        // code for handling the delete command
        // Make sure you check the return code from the
        // File delete method to print out success/failure status
    }

    public void rename(ArrayList<String> commandArguments) {

        File newPath = new File(commandArguments.get(0));
        boolean returnCode = false;
        if (path.exists()) {
            System.out.println("Renaming: " + path.getAbsolutePath());
            returnCode = path.renameTo(newPath);
            System.out.println(newPath);

        } else {
            System.out.println("File does not exist.");
        }
        if (returnCode == true) {
            System.out.println("Successful Rename");
        } else {
            System.out.println("Failed to Rename.");
        }

        printSeperator();
    }

    public void list() {
        if (path.exists()) {
            if (path.isDirectory()) {
                System.out.println("Listing: " + path.getAbsolutePath());
                for (int i = 0; i < path.list().length; i++) {
                    System.out.println(path.list()[i]);
                }
            } else
                System.out.println("Not a directory:" + path);
        } else

        {
            System.out.println("Directory does not exist.");
        }


        printSeperator();

    }

    public void size() {
        if (path.exists()) {
            System.out.println("Size for: " + path.getAbsolutePath());
            System.out.println("Size in bytes: " + path.length());
        } else {
            System.out.println("File does not exist.");
        }
        printSeperator();
    }

    public void lastModified() {


        if (path.exists() && path.isFile()) {
            System.out.println("Last modified for: " + path.getAbsolutePath());
            System.out.println(new Date(path.lastModified()));
        } else {
            System.out.println("File does not exist.");
        }

        printSeperator();
    }

    public void mkdir() {

        boolean returnCode = path.mkdir();

        if (returnCode == true) {
            System.out.println("Successful directory creation.");
        } else {
            System.out.println("Failed to create directory");
        }
    }

    public void createFile(ArrayList<String> commandArguments) {

        try {
            PrintStream printer = new PrintStream(new FileOutputStream(path));
            for (int i = 0; i < commandArguments.size(); i++) {
                printer.println(commandArguments.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Created file for: " + path.getAbsolutePath());
            printSeperator();
        }

    }


    public void printFile() {

        Scanner fileIn = null;

        try {
            fileIn = new Scanner(new FileInputStream((path)));
            while (fileIn.hasNextLine()) {
                System.out.println(fileIn.nextLine());
            }
            System.out.println("Printed file for: " + path.getAbsolutePath());

        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found." + e);
        } finally {
            if (fileIn != null)
                fileIn.close();
            printSeperator();

        }

    }

    void printSeperator() {
        System.out.println("************************");
    }

    void printUsage() {
        // process the "?" command

        System.out.println("Valid Commands: ");

        String[] validCommands = {"?", "createfile", "printFile", "lastModified", "size", "rename", "mkdir", "delete", "list", "quit"};

        for (int i = 0; i < validCommands.length; i++) {
            System.out.println(validCommands[i]);
        }

        printSeperator();
    }

    public void processCommandLine(String line) {

        try {
            StringTokenizer parseCommand = new StringTokenizer(line);
            String command = parseCommand.nextToken();
            System.out.println("Command is: " + command);

            ArrayList<String> commandArguments = new ArrayList<>();


            while (parseCommand.hasMoreTokens()) {
                commandArguments.add(parseCommand.nextToken());
            }

            if (commandArguments.size() != 0) {
                fileForAction = commandArguments.get(0);
                path = new File(fileForAction);
                commandArguments.remove(0);
            }


            switch (command) {
                case "?":
                    printUsage();
                    break;
                case "createFile":
                    createFile(commandArguments);
                    break;
                case "printFile":
                    printFile();
                    break;
                case "lastModified":
                    lastModified();
                    break;
                case "size":
                    size();
                    break;
                case "rename":
                    rename(commandArguments);
                    break;
                case "mkdir":
                    mkdir();
                    break;
                case "delete":
                    delete();
                    break;
                case "list":
                    list();
                    break;
                case "quit":
                    System.out.println("Bye.");
                    System.exit(0);
                default:
                    System.out.println("Invalid command: " + line);
                    printSeperator();
                    break;
            }


        } catch (NullPointerException e) {

            System.out.println("File argument is missing. Exception is: " + e);
            printSeperator();

        } catch (NoSuchElementException e) {
            System.out.println("Command is missing. Exception is: " + e);
            printSeperator();
        }

    }

    void processCommandFile(String commandFile) {

        Scanner fileIn = null;

        try {
            fileIn = new Scanner(new FileInputStream((commandFile)));
            while (fileIn.hasNextLine()) {
                processCommandLine(fileIn.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error. File not found." + e);
        } finally {
            if (fileIn != null)
                fileIn.close();

        }
    }

    public static void main(String[] args) {


        FileOperations fo = new FileOperations();

        for (int i = 0; i < args.length; i++) {
            System.out.println("\n\n============  Processing " + args[i] + " =======================\n");
            fo.processCommandFile(args[i]);
        }

        System.out.println("Done with file_operations.FileOperations");
    }
}