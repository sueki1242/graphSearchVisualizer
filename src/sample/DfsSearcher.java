package sample;

import java.util.ArrayDeque;
import java.util.Deque;

public class DfsSearcher {
    public DfsSearcher(Controller _controller) {
        this.controller = _controller;
    }

    public void Search(Graph graph, int from, int to) {
        Deque stack = new ArrayDeque<Integer>();
        boolean[] visit = new boolean[graph.GetNodeNum()];

        stack.addLast(from);
        while (!stack.isEmpty()) {
            int now = (int) stack.getLast();
            stack.removeLast();
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
                    stack.addLast(link.target);
                }
            }
        }
    }

    Controller controller;
}