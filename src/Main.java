import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String[][] matrix = new String[100][100];

        System.out.print("Number of states: ");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = scanner.next();

        System.out.print("Start state: ");
        int start = scanner.nextInt();

        System.out.print("Final states: ");
        ArrayList<String> endList = new ArrayList();
        for (String itr : scanner.next().split(",")) endList.add(itr);

        DirGraph graph = new DirGraph(size, matrix, start, endList);

        System.out.println("\nGraph created\n");

        String command = scanner.next();
        while (!command.equals("exit")) {
            if (command.equals("CC")) {
                if (graph.hasCycle()) System.out.println("Found cycle(s)");
                else System.out.println("No cycle found");
            }
            if (command.equals("RC")) {
                graph.removeCycles();
                System.out.println("Cycle(s) successfully removed");
            }
            if (command.equals("SL")) {
                graph.getAdjacencyList();
            }
            if (command.equals("CS")) {
                System.out.print("Enter the string: ");
                if (graph.accepted(scanner.next())) System.out.println("Valid string");
                else System.out.println("Invalid string");
            }
            if (command.equals("D")) {
                graph.display();
                System.out.println("Graph image generated");
            }
            command = scanner.next();
        }
    }
}
