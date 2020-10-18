package ru.itmo.wp.web.page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private enum Phase {
        RUNNING, WON_X, WON_0, DRAW
    }

    private static class Pair {
        private int a;
        private int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }
    }

    public static class State {
        private Phase phase;
        private int size;
        private String[][] cells;
        private boolean crossesMove;
        private int inc;

        private State(int size) {
            inc = 0;
            this.phase = Phase.RUNNING;
            this.size = size;
            this.cells = new String[size][size];
            this.crossesMove = true;
        }

        public Phase getPhase() {
            return phase;
        }

        public int getSize() {
            return size;
        }

        public String[][] getCells() {
            return cells;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        private void setCells(int a, int b, String s) {
            cells[a][b] = s;
        }

        private void switchMove() {
            crossesMove = !crossesMove;
        }

        private void setPhase(Phase phase) {
            this.phase = phase;
        }

        private void inc() {
            this.inc++;
        }

        private boolean canMove() {
            return inc < size * size;
        }
    }

    private  void newGame(HttpServletRequest request, Map<String, Object> view) {
        State newstate = new State(3);
        request.getSession().setAttribute("state", newstate);
        view.put("state", newstate);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        State newstate = new State(3);
        request.getSession().setAttribute("state", newstate);
        view.put("state", newstate);
    }

    public void onMove(HttpServletRequest request, Map<String, Object> view) throws ServletException {
        Map<String, String[]> move = request.getParameterMap();
        HttpSession session = request.getSession();

        State curstate = (State) session.getAttribute("state");
        if (curstate == null) {
            throw new ServletException("TicTac not inited for the session");
        }
        if (!curstate.canMove() || curstate.phase != Phase.RUNNING) {
            view.put("state", curstate);
            return;
        }
        curstate.inc();
        for (String key : move.keySet()) {
            if (key.matches("cell_[0-9][0-9]")) {
                String valueForMove;
                if (curstate.crossesMove) {
                    valueForMove = "X";
                } else {
                    valueForMove = "0";
                }
                curstate.setCells(key.charAt(key.length() - 2) % '0', key.charAt(key.length() - 1) % '0', valueForMove);
                curstate.switchMove();
                break;
            }
        }
        String winner = checkWinner(curstate);
        if (winner != null && winner.equals("X")) {
            curstate.setPhase(Phase.WON_X);
        }
        if (winner != null && winner.equals("0")) {
            curstate.setPhase(Phase.WON_0);
        }
        if (!curstate.canMove() && curstate.getPhase() == Phase.RUNNING) {
            curstate.setPhase(Phase.DRAW);
        }
        view.put("state", curstate);
    }

    private String checkWinner (State state) {
        String winner;
        for (int i  = 0; i < state.getSize(); i++) {
            winner = checkVector(0, 1,  i, 0, state);
            if (winner != null) {
                return winner;
            }
        }
        for (int i  = 0; i < state.getSize(); i++) {
            winner = checkVector(1, 0,  0, i, state);
            if (winner != null) {
                return winner;
            }
        }
        winner = checkVector(1, 1,  state.getSize() - 1, 0, state);
        if (winner != null) {
            return winner;
        }
        return checkVector(-1, 1, state.getSize() - 1, 0, state);
    }

    private String checkVector(int x, int y, int i, int j, State state) {
        String startValue = state.getCells()[i][j];
        if (startValue == null) {
            return null;
        }
        int curi = i;
        int curj = j;
        int cnt = 0;
        while (curi > -1 && curj > -1 && curi < state.getSize() && curj < state.getSize() && startValue.equals(state.getCells()[curi][curj])) {
            curi += x;
            curj += y;
            cnt++;
        }
        if (cnt != state.getSize()) {
            return null;
        }
        return startValue;
    }
}
