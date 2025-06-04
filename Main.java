import java.util.EnumMap;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * The class is responsible for a percentage calculator, which gets its input data from the user as arguments.
 * The program is designed for probability calculations regarding the college final exam topics (there are 27 of them)
 * */
public class Main {

    /**
     * The random generator used to simulate the probability percentages
     **/
    private static Random rand = new Random();

    /**
     * The input scanner, responsible for getting the values from the user
     **/
    private static Scanner sc = new Scanner(System.in);

    /**
     * The boolean responsible for the rendering of colors
     **/
    private static boolean useColors = true;


    /**
     * The main method is responsible for calling every individual method.
     * */
    public static void main(String[] args) {
        // Greeting the user
        greetings(sc);

        // Getting the crucial information of the topics' colors
        HashMap<String, Integer> topicsColored = readNumbersOfDifferentColoredTopics(sc);

        // Getting the number of times the simulation will run
        int iterations = readNumberOfIterations(sc);

        // Presenting the starting information to the user
        getInformation(topicsColored, iterations);

        // 1. Getting the final results
        // 2. Presenting the results to the user
        printResults(runSimulation(topicsColored, iterations, new HashMap<>()));


        // Exit
        System.out.println("\nNyomj Entert a kilépéshez...");
        sc.nextLine();
        sc.nextLine();
    }

    /**
     * The method gives the user crucial information at the start of the program.
     * It only does I/O stuff
     * */
    private static void greetings(Scanner sc) {
        System.out.println("\n--------------- Üdvözlet! ---------------\n");

        System.out.print("Szeretnéd színesben látni a kimenetet? (igen/nem): ");
        String answer = sc.nextLine().trim().toLowerCase();
        useColors = answer.equals("igen") || answer.equals("i") || answer.equals("y");

        System.out.println("Ez a program dzsinn megmondja, hogy milyen esélyed van " +
                TopicColor.PIROS.colorize("PIROS", useColors) + " - " +
                TopicColor.SÁRGA.colorize("SÁRGA", useColors) + " - " +
                TopicColor.ZÖLD.colorize("ZÖLD", useColors) + " - " +
                "tételeket húzni!\n");

        System.out.println(TopicColor.PIROS.colorize("PIROS: ", useColors) +"nagyon rossz tétel, 2-es kiszenvedése");
        System.out.println(TopicColor.SÁRGA.colorize("SÁRGA: ", useColors) +"nem rossz, de nem is jó tétel");
        System.out.println(TopicColor.ZÖLD.colorize("ZÖLD: ", useColors) +"kifejezetten jó tétel\n");

        System.out.println("Most pedig add meg, hogy mennyi tételed van! Az összes tételszámnak összesen 27-nek kell lennie.\n");
    }


    /**
     * The method gives coloring to the text
     * @param text: the text which needs coloring
     * @param colorCode: the color, which can be either red, green or yellow
     * @return: the modified string which contains the coloring (if colors are enabled)
     **/
    private static String colorize(String text, String colorCode) {
        if (!useColors) return text;
        return colorCode + text + "\u001B[0m";
    }


    /**
     * The method makes sure the number of topics is saved.
     * @param sc: The input scanner
     * @return: a map, containing the color names and their numbers
     * */
    private static HashMap<String, Integer> readNumbersOfDifferentColoredTopics(Scanner sc) {
        HashMap<String, Integer> values = new HashMap<>();

        while (true) {
            try {
                System.out.print(TopicColor.PIROS.colorize("PIROS darabszám: ", useColors));
                values.put("PIROS", sc.nextInt());

                System.out.print(TopicColor.SÁRGA.colorize("SÁRGA darabszám: ", useColors));
                values.put("SÁRGA", sc.nextInt());

                System.out.print(TopicColor.ZÖLD.colorize("ZÖLD darabszám: ", useColors));
                values.put("ZÖLD", sc.nextInt());

                if (values.get("PIROS") + values.get("SÁRGA") + values.get("ZÖLD") == 27) {
                    break;
                } else {
                    System.out.println("HIBA! Pontosan 27-nek kell lennie. Próbáld újra!");
                }
            } catch (Exception e) {
                System.out.println("Nem sikerült beolvasni, próbáld újra.");
                sc.next();
            }
        }
        return values;
    }


    /**
     * The method makes sure the number of iterations is saved.
     * @param sc: The input scanner
     * @return: the number of iterations the simulations will run
     * */
    private static int readNumberOfIterations(Scanner sc) {
        int iterations = 0;
        while (true) {
            try {
                System.out.print("Remek! Hányszor fusson le a szimuláció? ");
                iterations = sc.nextInt();

                if (iterations > 0) {
                    break;
                } else {
                    System.out.println("HIBA! A futások számának pozitívnak kell lennie. Próbáld újra!");
                }
            } catch (Exception e) {
                System.out.println("Nem sikerült beolvasni, próbáld újra.");
                sc.next();
            }
        }
        return iterations;
    }


    /**
     * The method sums up the user given values.
     * It only does I/O stuff
     * */
    private static void getInformation(HashMap<String, Integer> topicsColored, int iterations) {
        System.out.println("\n------------------------------");
        System.out.println(TopicColor.PIROS.colorize("-PIROS: ", useColors) + topicsColored.get("PIROS") + " db\t\t\t\t-");
        System.out.println(TopicColor.SÁRGA.colorize("-SÁRGA: ", useColors) + topicsColored.get("SÁRGA") + " db\t\t\t\t-");
        System.out.println(TopicColor.ZÖLD.colorize("-ZÖLD: ", useColors)  + topicsColored.get("ZÖLD") + " db\t\t\t\t-");
        System.out.println("-A szimuláció ennyiszer fog futni: " + iterations);
        System.out.println("------------------------------");
    }


    /**
     * The method runs the program with the given information.
     * This is the main part of the simulation
     * @param topicsColored: the map containing the colors and their number of occurances
     * @param iterations: the number of iterations
     * @param results: the map in which the results are going to be returned
     * @return: the calculated probabilities of each color category
     * */
    private static HashMap<String, Integer> runSimulation(HashMap<String, Integer> topicsColored, int iterations, HashMap<String, Integer> results) {
        for (int i = 0; i < iterations; i++) {
            int randomNumber = rand.nextInt(27);

            if (randomNumber < topicsColored.get("PIROS")) {
                results.put("PIROS", results.getOrDefault("PIROS", 0) + 1);
            } else if (randomNumber < topicsColored.get("PIROS") + topicsColored.get("SÁRGA")) {
                results.put("SÁRGA", results.getOrDefault("SÁRGA", 0) + 1);
            } else {
                results.put("ZÖLD", results.getOrDefault("ZÖLD", 0) + 1);
            }
        }
        return results;
    }


    /**
     * The method prints out the result of the simulation. It also gives a verdict
     * @param results: the map which stores the results of the simulation
     * */
    private static void printResults(HashMap<String, Integer> results) {
        System.out.println("\nEREDMÉNY");

        int total = results.values().stream().mapToInt(Integer::intValue).sum();

        double redPercentage = (results.getOrDefault("PIROS", 0) / (double) total) * 100;
        double yellowPercentage = (results.getOrDefault("SÁRGA", 0) / (double) total) * 100;
        double greenPercentage = (results.getOrDefault("ZÖLD", 0) / (double) total) * 100;

        for (String key : results.keySet()) {
            double percentage = (results.get(key) / (double) total) * 100;

            String color;
            if (key.equals("PIROS")) {
                color = "\u001B[31m";
            } else if (key.equals("SÁRGA")) {
                color = "\u001B[33m";
            } else {
                color = "\u001B[32m";
            }

            System.out.println(colorize(key + ": " + results.get(key) + " db, " + String.format("%.2f", percentage) + "%", color));
        }

        verdict(greenPercentage, redPercentage, yellowPercentage);
    }


    /**
     * The method prints out the verdict
     * @param greenPercentage: the percentage of the green topics
     * @param redPercentage: the percentage of the red topics
     * @param yellowPercentage: the percentage of the yellow topics
     * */
    private static void verdict(double greenPercentage, double redPercentage, double yellowPercentage) {
        System.out.println("---------------");
        System.out.println("Ítélet");
        if (greenPercentage >= 75) {
            System.out.println(colorize("Jó esélyed van! Legyen szerencséd!", "\u001B[32m"));
        } else if (redPercentage > 20) {
            System.out.println(colorize("Ajjaj, ez kemény menet lesz...", "\u001B[31m"));
        } else if (yellowPercentage > 30) {
            System.out.println(colorize("Sárgul! Hátha nem kapsz annyira rosszat!", "\u001B[33m"));
        } else {
            System.out.println(colorize("Vegyes ", "\u001B[33m")
                    + colorize("felvágott! ", "\u001B[31m")
                    + colorize("Reméljük ", "\u001B[33m")
                    + colorize("a ", "\u001B[31m")
                    + colorize("legjobbakat!", "\u001B[32m"));
        }
    }
}
