package sample;

import java.util.ArrayDeque;
import java.util.Deque;

public class BfsSearcher {
    public BfsSearcher(Controller _controller) {
        this.controller = _controller;
    }

    public void Search(Graph graph, int from, int to) {
        Deque queue = new ArrayDeque<Integer>();
        boolean[] visit = new boolean[graph.GetNodeNum()];

        queue.addLast(from);
        while (!queue.isEmpty()) {
            int now = (int) queue.getFirst();
            queue.removeFirst();
            if (visit[now]) {
                continue;
            }
            controller.AddDebugInfo(graph, now, null);
            if (now == to) {
                return;
            }
            visit[now] = true;
            for (Link link : graph.GetOutLinks(now)) {
                if (!visit[link.target]) {
                    controller.AddDebugInfo(graph, now, link);
                    queue.addLast(link.target);
                }
            }
        }
    }

    Controller controller;
}
