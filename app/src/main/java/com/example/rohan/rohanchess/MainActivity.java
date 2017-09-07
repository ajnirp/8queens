package com.example.rohan.rohanchess;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = new boolean[64];
    }

    // Representation of the board.
    // true if and only if a queen is located at that square.
    private boolean[] board;

    // Indices start from 0 (bottom left) and grow from left to right
    // and bottom to top. So, a1 is 0 and b1 is 8 and h8 is 63.
    private int idToIndex(String id) {
        int col = (int)id.charAt(0) - (int)'a';
        int row = (int)id.charAt(1) - (int)'1';
        return (row * 8) + col;
    }

    // true if the square is black, false if it is white.
    private boolean isBlack(int index) {
        int row_parity = (index / 8) % 2;
        int col_parity = index % 2;
        return (row_parity ^ col_parity) == 0;
    }

    // Debug method used for displaying a Toast indicating
    // the square on the board that was just tapped.
    private void showSelectedSquare(String id) {
        Context ctx = getApplicationContext();
        int index = idToIndex(id);
        String total = id + ' ' + index;
        CharSequence text = (CharSequence) total;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(ctx, text, duration);
        toast.show();
    }

    // Update the display of a square for when a queen is
    // added or removed by the user.
    private void redrawSquare(View v) {
        int index = viewToIndex(v);
        if (isBlack(index))
            if (board[index])
                v.setBackgroundResource(R.drawable.qqdark);
            else
                v.setBackgroundResource(R.drawable.dark);
        else
            if (board[index])
                v.setBackgroundResource(R.drawable.qqlight);
            else
                v.setBackgroundResource(R.drawable.light);
    }

    // Convert a View for a square on the board to the index
    // for that square, from 0 to 63.
    private int viewToIndex(View v) {
        String id = viewToString(v);
        return idToIndex(id);
    }

    // Convert a View to a String representing the name of the
    // square corresponding to that View, like a1 or d7 or f3.
    private String viewToString(View v) {
        String id = v.getResources().getResourceName(v.getId());
        return id.substring(id.length()-2, id.length());
    }

    // Convert an index from 0 to 63 to the name of a square on the
    // board, from a1 to h8.
    private String indexToSquareName(int index) {
        int row = index / 8;
        int col = index % 8;
        return String.valueOf("abcdefgh".charAt(col)) + String.valueOf("12345678".charAt(row));
    }

    // Display a Toast indicating that two queens are attacking
    private void displayConflict(int idx1, int idx2) {
        Context ctx = getApplicationContext();
        String id1 = indexToSquareName(idx1);
        String id2 = indexToSquareName(idx2);
        String total = id1 + " is attacking " + id2 + "!";
        CharSequence text = (CharSequence) total;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(ctx, text, duration);
        toast.show();
    }

    // i col, j row
    private int ijToIndex(int i, int j) {
        return i + j*8;
    }

    // For a given index, check if it is capturing
    // some other queen on the board.
    private void checkConflict(int index) {
        HashSet<Integer> locs = new HashSet<Integer>();
        for (int i = 0; i < 64; i++) {
            if (board[i])
                locs.add(i);
        }
        for (int i : locs) {
            if (i != index && attacks(i, index)) {
                displayConflict(i, index);
            }
        }
    }

    // Place a queen at the square represented by View v
    public void toggleQueen(View v) {
        String id = viewToString(v);
        int index = idToIndex(id);
//        showSelectedSquare(id);
        board[index] = !board[index];
        redrawSquare(v);
        if (board[index])
            checkConflict(index);
    }

    // Convert an index from 0 to 63 to an int
    // corresponding to a View
    private int indexToViewId(int index) {
        switch (index) {
            case 0: return R.id.a1;
            case 1: return R.id.b1;
            case 2: return R.id.c1;
            case 3: return R.id.d1;
            case 4: return R.id.e1;
            case 5: return R.id.f1;
            case 6: return R.id.g1;
            case 7: return R.id.h1;
            case 8: return R.id.a2;
            case 9: return R.id.b2;
            case 10: return R.id.c2;
            case 11: return R.id.d2;
            case 12: return R.id.e2;
            case 13: return R.id.f2;
            case 14: return R.id.g2;
            case 15: return R.id.h2;
            case 16: return R.id.a3;
            case 17: return R.id.b3;
            case 18: return R.id.c3;
            case 19: return R.id.d3;
            case 20: return R.id.e3;
            case 21: return R.id.f3;
            case 22: return R.id.g3;
            case 23: return R.id.h3;
            case 24: return R.id.a4;
            case 25: return R.id.b4;
            case 26: return R.id.c4;
            case 27: return R.id.d4;
            case 28: return R.id.e4;
            case 29: return R.id.f4;
            case 30: return R.id.g4;
            case 31: return R.id.h4;
            case 32: return R.id.a5;
            case 33: return R.id.b5;
            case 34: return R.id.c5;
            case 35: return R.id.d5;
            case 36: return R.id.e5;
            case 37: return R.id.f5;
            case 38: return R.id.g5;
            case 39: return R.id.h5;
            case 40: return R.id.a6;
            case 41: return R.id.b6;
            case 42: return R.id.c6;
            case 43: return R.id.d6;
            case 44: return R.id.e6;
            case 45: return R.id.f6;
            case 46: return R.id.g6;
            case 47: return R.id.h6;
            case 48: return R.id.a7;
            case 49: return R.id.b7;
            case 50: return R.id.c7;
            case 51: return R.id.d7;
            case 52: return R.id.e7;
            case 53: return R.id.f7;
            case 54: return R.id.g7;
            case 55: return R.id.h7;
            case 56: return R.id.a8;
            case 57: return R.id.b8;
            case 58: return R.id.c8;
            case 59: return R.id.d8;
            case 60: return R.id.e8;
            case 61: return R.id.f8;
            case 62: return R.id.g8;
            case 63: return R.id.h8;
            default: return R.id.a1;
        }
    }

    // Event handler for when the user presses the Restart button.
    public void restart(View v) {
        for (int i = 0; i < 64; i++) {
            if (board[i]) {
                board[i] = false;
                View _v = findViewById(indexToViewId(i));
                redrawSquare(_v);
            }
        }
    }

    // Returns true if and only if square i attacks square j.
    // i and j are between 0 and 63 inclusive.
    private boolean attacks(int i, int j) {
        int r_i = i % 8;
        int c_i = i / 8;
        int r_j = j % 8;
        int c_j = j / 8;
        int dr = r_i - r_j;
        int dc = c_i - c_j;
        return (r_i == r_j || c_i == c_j || dr == dc || dr == -dc);
    }

    // Returns true if and only if there are no conflicts.
    private boolean noConflicts(Set<Integer> locations) {
        if (locations.size() > 8)
            return false;
        if (locations.size() == 0)
            return true;
        ArrayList<Integer> locs = new ArrayList<Integer>(locations);
        for (int i = 0; i < locs.size(); i++) {
            for (int j = i+1; j < locs.size(); j++) {
                if (attacks(i, j))
                    return false;
            }
        }
        return true;
    }

    // Use backtracking to find a solution from the current board state.
    private HashSet<Integer> findSolution(HashSet<Integer> locations) {
        if (locations.size() == 8)
            return locations;
        HashSet<Integer> empty = new HashSet<Integer>();
        for (int i = 0; i < 64; i++) {
            // check that it does not attack any of the existing locations
            boolean safeToAdd = true;
            for (int ii : locations) {
                if (attacks(i, ii))
                    safeToAdd = false;
            }
            if (!safeToAdd)
                continue;
            // safe to add, so add it and recurse
            HashSet<Integer> new_locations = new HashSet<Integer>(locations);
            new_locations.add(i);
            HashSet<Integer> solution = findSolution(new_locations);
            if (solution.size() == 8)
                return solution;
        }
        return empty;
    }

    private void findSolutionAndDisplay() {
        HashSet<Integer> locations = new HashSet<Integer>();
        for (int i = 0; i < 64; i++) {
            if (board[i])
                locations.add(i);
        }
        HashSet<Integer> solution = findSolution(locations);
        if (solution.size() == 0) {
            Context ctx = getApplicationContext();
            String report = "No solution could be found";
            CharSequence text = (CharSequence) report;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(ctx, text, duration);
            toast.show();
            return;
        }
        if (solution.size() == 8) {
            for (int i = 0 ; i < 64; i++)
                board[i] = false;
            for (int i : solution) {
                int v_id = indexToViewId(i);
                View v = findViewById(v_id);
                toggleQueen(v);
            }
        }
    }

    private boolean unsolvableBoard() {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        for (int i = 0; i < 64; i++) {
            if (board[i])
                locs.add(i);
        }
        for (int i : locs) {
            for (int j : locs) {
                if (i != j && attacks(i, j))
                    return true;
            }
        }
        return false;
    }

    // Event handler for when the user presses the Give Up button.
    public void giveUp(View v) {
        if (unsolvableBoard()) {
            Context ctx = getApplicationContext();
            String report = "No solution possible - some queens are attacking each other";
            CharSequence text = (CharSequence) report;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(ctx, text, duration);
            toast.show();
            return;
        }
        findSolutionAndDisplay();
    }
}
