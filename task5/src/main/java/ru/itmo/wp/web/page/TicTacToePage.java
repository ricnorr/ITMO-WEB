package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }

    public static class State {
        private Phase phase;
        private final int size;
        private final String[][] cells;
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

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State newstate = new State(3);
        request.getSession().setAttribute("state", newstate);
        view.put("state", newstate);
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        if (state != null) {
            view.put("state", state);
        } else {
            newGame(request, view);
        }

    }

    public void onMove(HttpServletRequest request, Map<String, Object> view) throws NotFoundException {
        Map<String, String[]> move = request.getParameterMap();
        HttpSession session = request.getSession();

        State currentState = (State) session.getAttribute("state");
        if (currentState == null) {
            newGame(request, view);
            return;
        }
        if (!currentState.canMove() || currentState.phase != Phase.RUNNING) {
            view.put("state", currentState);
            return;
        }
        currentState.inc();
        for (String key : move.keySet()) {
            if (key.matches("cell_[0-9][0-9]")) {
                String valueForMove;
                if (currentState.crossesMove) {
                    valueForMove = "X";
                } else {
                    valueForMove = "0";
                }
                currentState.setCells(key.charAt(key.length() - 2) % '0', key.charAt(key.length() - 1) % '0', valueForMove);
                currentState.switchMove();
                break;
            }
        }
        String winner = checkWinner(currentState);
        winner = winner == null ? "DEFAULT" : winner;
        switch (winner) {
            case "X":
                currentState.setPhase(Phase.WON_X);
                break;
            case "0":
                currentState.setPhase(Phase.WON_O);
                break;
            default:
                if (!currentState.canMove() && currentState.getPhase() == Phase.RUNNING) {
                    currentState.setPhase(Phase.DRAW);
                }
        }
        view.put("state", currentState);
    }

    private String checkWinner(State state) {
        String winner;
        for (int i = 0; i < state.getSize(); i++) {
            winner = checkVector(0, 1, i, 0, state);
            if (winner != null) {
                return winner;
            }
            winner = checkVector(1, 0, 0, i, state);
            if (winner != null) {
                return winner;
            }
        }
        winner = checkVector(1, 1, state.getSize() - 1, 0, state);
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