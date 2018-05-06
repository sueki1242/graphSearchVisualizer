package sample;

import java.util.ArrayList;

public class Graph {
    public Graph(ArrayList<Link> links, ArrayList<Node> _nodes) {
        this.nodes = (ArrayList<Node>) _nodes.clone();
        outLinks = new ArrayList<>(nodes.size());
        for(int i=0; i<nodes.size(); ++i){
            outLinks.add(new ArrayList<>());
        }
        for (Link link : links) {
            outLinks.get(link.source).add(link);
        }
    }

    int GetNodeNum() {
        return nodes.size();
    }

    ArrayList<Link> GetOutLinks(final int nodeId) {
        return outLinks.get(nodeId);
    }

    Node GetNode(final int nodeId) {
        return nodes.get(nodeId);
    }

    private ArrayList<Node> nodes;
    private ArrayList<ArrayList<Link>> outLinks;
}
