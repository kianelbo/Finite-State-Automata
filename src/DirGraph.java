import GraphViz.GraphDrawer;

import java.util.ArrayList;

public class DirGraph {
    private int Size;
    private Vertex[] V;
    private Vertex Start;
    private ArrayList<Vertex> End = new ArrayList<Vertex>();

    public  DirGraph (int size, String[][] matrix, int start, ArrayList<String > end) {
        this.Size = size;
        V = new Vertex[Size];
        for (int v = 0; v < Size; v++)
            V[v] = new Vertex(v);

        for (int i=0; i<Size; i++)
            for (int j=0; j<Size; j++)
                if (!matrix[i][j].equals("-"))
                    for (String itr: matrix[i][j].split(",")) {
                        Edge e = new Edge(itr.charAt(0), V[j]);
                        V[i].addEdge(e);
                    }

        Start = V[start];

        for (int e=0; e<end.size(); e++)
            End.add(V[Integer.valueOf(end.get(e))]);
    }


    public void getAdjacencyList() {
        for (int i=0; i<Size; i++)
            System.out.println("State " + i + " -> " + V[i].toString());
    }

    public boolean hasCycle() {
        for (int b=0; b<Size; b++) V[b].marked = false;
        return hasCycle(Start);
    }
    private boolean hasCycle(Vertex v) {
        boolean result = false;
        v.marked = true;
        for (int b=0; b<v.Edges.size(); b++) {
            if (!v.Edges.get(b).EndPoint.marked) result = hasCycle(v.Edges.get(b).EndPoint);
            else if (v.Edges.get(b).EndPoint != v) result = true;
            if (result == true) break;
        }
        return result;
    }

    public void removeCycles() {
        for (int b=0; b<Size; b++) V[b].marked = false;
        removeCycle(Start);
    }
    private void removeCycle(Vertex v) {
        v.marked = true;
        for (int b=0; b<v.Edges.size(); b++) {
            if (!v.Edges.get(b).EndPoint.marked) removeCycle(v.Edges.get(b).EndPoint);
            else if (v.Edges.get(b).EndPoint != v) v.Edges.remove(b);
        }
    }

    public boolean accepted (String s) {
        return accepted(s, Start);
    }
    private boolean accepted (String s, Vertex startPoint) {
        boolean result = false;
        if (s.length() == 0) result = End.contains(startPoint);
        else {
            int f = 0;
            while (f < startPoint.findPath(s.charAt(0)).size()) {
                result = accepted(s.substring(1), startPoint.findPath(s.charAt(0)).get(f));
                if (result) break;
                f++;
            }
        }
        return  result;
    }

    public void display() {
        String dot = new String("");
        for (int s=0; s<Size; s++) {
            int a=0;
            while (a < V[s].Edges.size()) {
                dot = dot + s + "->" + V[s].Edges.get(a).EndPoint.ID + "[label=" + V[s].Edges.get(a).Name + "] ";
                a++;
            }
        }
        GraphDrawer gd = new GraphDrawer();
        gd.draw("GraphDisplay.", dot);
    }

}


class Vertex {
    int ID;
    ArrayList<Edge> Edges = new ArrayList<Edge>();
    boolean marked;

    Vertex (int id) {
        this.ID = id;
    }

    public void addEdge(Edge e) {
        Edges.add(e);
    }

    public String toString() {
        String str = new String("");
        for (int i=0; i<Edges.size(); i++)
            str = str + ("(" + Edges.get(i).EndPoint.ID + "," + Edges.get(i).Name + ")");
        return str;
    }

    public ArrayList<Vertex> findPath (char edgeName) {
        ArrayList<Vertex> paths = new ArrayList<>();
        for (int w=0; w<Edges.size(); w++)
            if (Edges.get(w).Name == edgeName) paths.add(Edges.get(w).EndPoint);
        return paths;
    }
}


class Edge {
    char Name;
    Vertex EndPoint;
    Edge (char name, Vertex ep) {
        this.Name = name;
        this.EndPoint = ep;
    }
}