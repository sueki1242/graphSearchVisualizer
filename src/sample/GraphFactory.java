package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphFactory {
    public enum GraphType {
        Sample,
    }

    static Graph Create(GraphType type) {
        switch (type) {
            case Sample:
                return ReadGraph("data/sampleGraph/nodes.csv", "data/sampleGraph/links.csv");
        }
        return null;
    }

    static Graph ReadGraph(final String nodeFile, final String linkFile) {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Link> links = new ArrayList<>();

        try {
            File fNode = new File(nodeFile);
            BufferedReader brNode = new BufferedReader(new FileReader(fNode));
            String line;
            while ((line = brNode.readLine()) != null) {
                String[] data = line.split(",", 0);
                nodes.add(new Node(Integer.parseInt(data[0]),
                        Integer.parseInt(data[1])));
            }

            File fLink = new File(linkFile);
            BufferedReader brLink = new BufferedReader(new FileReader(fLink));
            while ((line = brLink.readLine()) != null) {
                String[] data = line.split(",", 0);
                links.add(new Link(Integer.parseInt(data[0]),
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2])));
            }
        } catch (IOException e) {
            System.out.println("file read error");
        }
        return new Graph(links, nodes);
    }
}
