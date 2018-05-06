package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Canvas canvas;

    @FXML
    MenuItem bfs;
    @FXML
    MenuItem dfs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        sampleGraph = GraphFactory.Create(GraphFactory.GraphType.Sample);
        drawNodeId = true;
        currentGraph = sampleGraph;
        DrawGraph();

        bfsSearcher = new BfsSearcher(this);
        dfsSearcher = new DfsSearcher(this);
        visitedNodeIds = new HashSet<>();
        visitedLinks = new ArrayList<>();
        bfs.setOnAction(event -> {
            visitedNodeIds.clear();
            visitedLinks.clear();
            DrawGraph();
            new Thread(() -> bfsSearcher.Search(sampleGraph, 0, 9)).start();
        });
        dfs.setOnAction(event -> {
            visitedNodeIds.clear();
            visitedLinks.clear();
            DrawGraph();
            new Thread(() -> dfsSearcher.Search(sampleGraph, 0, 9)).start();
        });
    }

    private void DrawNode(final int nodeId, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        Node node = currentGraph.GetNode(nodeId);
        gc.fillOval(node.x - NODE_RADIUS, node.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.strokeOval(node.x - NODE_RADIUS, node.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        if (drawNodeId) {
            gc.setFill(Color.BLACK);
            gc.fillText(String.format("%d", nodeId), node.x - 8, node.y + 8);
        }

        gc.setFill(DEFAULT_FILL_COLOR);
    }

    private void DrawLink(final Link link, Color color, double lineWidth) {
        Node nodeFrom = currentGraph.GetNode(link.source);
        Node nodeTo = currentGraph.GetNode(link.target);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        gc.setLineWidth(lineWidth);
        gc.strokeLine(nodeFrom.x, nodeFrom.y, nodeTo.x, nodeTo.y);

        // cleanup
        gc.setStroke(DEFAULT_STROKE_COLOR);
        gc.setLineWidth(DEFAULT_LINE_WIDTH);
    }

    public void DrawGraph() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(DEFAULT_FILL_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFont(new Font(32));
        gc.setStroke(DEFAULT_STROKE_COLOR);
        for (int i = 0; i < currentGraph.GetNodeNum(); ++i) {
            for (Link link : currentGraph.GetOutLinks(i)) {
                DrawLink(link, DEFAULT_STROKE_COLOR, DEFAULT_LINE_WIDTH);
            }
        }
        for (int i = 0; i < currentGraph.GetNodeNum(); ++i) {
            DrawNode(i, Color.WHITE);
        }
    }

    public void AddDebugInfo(Graph graph, Integer nodeId, Link link) {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            //
        }
        new Thread(() -> {
            Platform.runLater(() -> {
                for (Link link_ : visitedLinks) {
                    DrawLink(link_, Color.GRAY, 3.0);
                }
                if (link != null) {
                    DrawLink(link, Color.RED, 3.0);
                    visitedLinks.add(link);
                }

                for (int i = 0; i < currentGraph.GetNodeNum(); ++i) {
                    if (visitedNodeIds.contains(i)) {
                        DrawNode(i, Color.GRAY);
                    } else {
                        DrawNode(i, Color.WHITE);
                    }
                }

                if (nodeId != null) {
                    DrawNode(nodeId, Color.RED);
                    visitedNodeIds.add(nodeId);
                }
            });
        }).start();

    }

    Graph sampleGraph;
    Graph currentGraph;
    BfsSearcher bfsSearcher;
    DfsSearcher dfsSearcher;

    HashSet<Integer> visitedNodeIds;
    ArrayList<Link> visitedLinks;

    boolean drawNodeId;

    final private int NODE_RADIUS = 25;
    final private Color DEFAULT_STROKE_COLOR = Color.BLACK;
    final private Color DEFAULT_FILL_COLOR = Color.WHITE;
    final private double DEFAULT_LINE_WIDTH = 1.0;
}
