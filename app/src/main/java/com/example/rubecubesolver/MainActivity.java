package com.example.rubecubesolver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static com.example.rubecubesolver.Colors.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(getSolution(generateScramble(20)));
    }
    public boolean crossSolved(Cube cube) {
        Edge[] crossEdges = cube.getEdges(cube.getFace(WHITE).getEdgeLocs());
        boolean crossSolved = true;
        for (Edge e : crossEdges) {
            if (!e.solved()) {
                crossSolved = false;
            }
        }
        System.out.println("Cross solved? " + crossSolved);
        return crossSolved;
    }
    public String getSolution(String scramble) {
        Cube cube = new Cube();
        String solution = "";
        cube.mix(scramble);
        solution += "\nScramble: " + scramble;
        Map<Edge, ArrayList<String>> crossSolution = Cross.solve(cube);
        solution += "\nCROSS SOLUTION\n";
        solution += "Orange cross piece solution:\n";
        solution += crossSolution.get(cube.getEdge(WHITE, ORANGE));
        solution += "\nGreen cross piece solution:\n";
        solution += crossSolution.get(cube.getEdge(WHITE, GREEN));
        solution += "\nBlue cross piece solution:\n";
        solution += crossSolution.get(cube.getEdge(WHITE, BLUE));
        solution += "\nRed cross piece solution:\n";
        solution += crossSolution.get(cube.getEdge(WHITE, RED));
        Map<Corner, ArrayList<String>> f2LSolution = FirstTwoLayers.solve(cube);
        solution += "\nF2L SOLUTION\n";
        solution += "Blue, red pair solution:\n";
        solution += f2LSolution.get(cube.getCorner(BLUE, WHITE, RED));
        solution += "\nBlue, orange pair solution:\n";
        solution += f2LSolution.get(cube.getCorner(BLUE, WHITE, ORANGE));
        solution += "\nGreen, orange pair solution:\n";
        solution += f2LSolution.get(cube.getCorner(GREEN, WHITE, ORANGE));
        solution += "\nGreen, red pair solution:\n";
        solution += f2LSolution.get(cube.getCorner(GREEN, WHITE, RED));
        solution += "\nOLL SOLUTION:\n" ;
        solution += OrientLastLayer.solve(cube);
        solution += "\nPLL SOLUTION:\n" ;
        solution += PermuteLastLayer.solve(cube);
        return solution;
    }
    public boolean f2lSolved(Cube cube) {
        int[] f2lEdgeLocs = {3,4,7,8};
        Edge[] f2LEdges = cube.getEdges(f2lEdgeLocs);
        Corner[] f2LCorners = cube.getCorners(cube.getFace(WHITE).getCorLocs(),
                cube.getFace(WHITE).getCorSides());
        boolean f2LSolved = true;
        for (Edge e : f2LEdges) {
            if (!e.solved()) {
                f2LSolved = false;
            }
        }
        for (Corner c : f2LCorners) {
            if (!c.solved()) {
                f2LSolved = false;
            }
        }
        System.out.println("First two layers solved? " + f2LSolved);
        return f2LSolved;
    }
    public boolean oLLSolved(Cube cube) {
        Edge[] oLLEdges = cube.getEdges(cube.getFace(YELLOW).getEdgeLocs());
        Corner[] oLLCorners = cube.getCorners(cube.getFace(YELLOW).getCorLocs(),
                cube.getFace(YELLOW).getCorSides());
        boolean lLOriented = true;
        for (Edge e : oLLEdges) {
            if (e.getOrient() != 1) {
                lLOriented = false;
            }
        }
        for (Corner c : oLLCorners) {
            if (c.getOrient() != 0) {
                lLOriented = false;
            }
        }
        System.out.println("Last layer oriented? " + lLOriented);
        return lLOriented;
    }
    public boolean pLLSolved(Cube cube) {
        Edge[] pLLEdges = cube.getEdges(cube.getFace(YELLOW).getEdgeLocs());
        Corner[] pLLCorners = cube.getCorners(cube.getFace(YELLOW).getCorLocs(),
                cube.getFace(YELLOW).getCorSides());
        boolean lLSolved = true;
        for (Edge e : pLLEdges) {
            if (!e.solved()) {
                lLSolved = false;
            }
        }
        for (Corner c : pLLCorners) {
            if (!c.solved()) {
                lLSolved = false;
            }
        }
        System.out.println("Last layer solved? " + lLSolved);
        return lLSolved;
    }
    public boolean isSolved(Cube cube) {
        boolean cross = crossSolved(cube);
        boolean f2l = f2lSolved(cube);
        boolean pll = pLLSolved(cube);
        return cross && f2l && pll;
    }
    public String generateScramble(int length) {
        String scramble = "";
        String[] moves = {"R", "R'", "R2",
                        "L", "L'", "L2",
                        "F", "F'", "F2",
                        "B", "B'", "B2",
                        "U", "U'", "U2",
                        "D", "D'", "D2"};
        String last = " ";
        String secondLast = " ";
        for (int i = 0; i < length; i++) {
            Random rand = new Random();
            String move = moves[rand.nextInt(moves.length)];
            while (move.substring(0,1).equals(last.substring(0,1))
                    || (move.substring(0,1).equals(secondLast.substring(0,1)))) {
                move = moves[rand.nextInt(moves.length)];
            }
            scramble += move;
            if (i != length - 1) {
                scramble += " ";
            }
            secondLast = last;
            last = move;
        }
        return scramble;
    }
    public void tester() {

        int numCrossSolved = 0;
        int numF2LSolved = 0;
        int numOllSolved = 0;
        int numPllSolved = 0;
        int numSolved = 0;

        double totalMoves = 0;
        int moves;

        int correctAlgs = 0;

        int totalTests = 1000;
        for (int i = 1; i <= totalTests; i++) {
            Cube cube1 = new Cube();
            moves = 0;

            String scramble = generateScramble(20);
            cube1.mix(scramble);
            System.out.println(" \nScramble: " + scramble);

            Map<Edge, ArrayList<String>> crossSolution = Cross.solve(cube1);
            System.out.println("Orange cross piece solution:");
            System.out.println(crossSolution.get(cube1.getEdge(WHITE, ORANGE)));
            System.out.println("Green cross piece solution:");
            System.out.println(crossSolution.get(cube1.getEdge(WHITE, GREEN)));
            System.out.println("Blue cross piece solution:");
            System.out.println(crossSolution.get(cube1.getEdge(WHITE, BLUE)));
            System.out.println("Red cross piece solution:");
            System.out.println(crossSolution.get(cube1.getEdge(WHITE, RED)));
            if (crossSolved(cube1)) {
                numCrossSolved++;
            }

            Map<Corner, ArrayList<String>> f2LSolution = FirstTwoLayers.solve(cube1);
            System.out.println("Blue, Red Pair Solution:");
            System.out.println(f2LSolution.get(cube1.getCorner(BLUE, WHITE, RED)));
            System.out.println("Blue, Orange Pair Solution:");
            System.out.println(f2LSolution.get(cube1.getCorner(BLUE, WHITE, ORANGE)));
            System.out.println("Green, Orange Pair Solution:");
            System.out.println(f2LSolution.get(cube1.getCorner(GREEN, WHITE, ORANGE)));
            System.out.println("Green, Red Pair Solution:");
            System.out.println(f2LSolution.get(cube1.getCorner(GREEN, WHITE, RED)));
            if (f2lSolved(cube1)) {
                numF2LSolved++;
            }

            ArrayList<String> oLLSolution = OrientLastLayer.solve(cube1);
            System.out.println("Last Layer Orientation Solution:");
            System.out.println(oLLSolution);
            if (oLLSolved(cube1)) {
                numOllSolved++;
            }

            ArrayList<String> pLLSolution = PermuteLastLayer.solve(cube1);
            System.out.println("Last Layer Permutation Solution:");
            System.out.println(pLLSolution);
            if (pLLSolved(cube1)) {
                numPllSolved++;
            }

            System.out.println("Cube #" + i + ".0 Solved? " + cube1.solved());
            if (cube1.solved()) {
                numSolved++;
            }

            for (Map.Entry<Edge, ArrayList<String>> entry : crossSolution.entrySet()) {
                totalMoves += entry.getValue().size();
                moves += entry.getValue().size();
            }
            for (Map.Entry<Corner, ArrayList<String>> entry : f2LSolution.entrySet()) {
                totalMoves += entry.getValue().size();
                moves += entry.getValue().size();
            }
            totalMoves += oLLSolution.size() + pLLSolution.size();
            moves += oLLSolution.size() + pLLSolution.size();
            System.out.println("Moves taken: " + moves + " moves");

            Cube cube2 = new Cube();
            cube2.mix(scramble);
            cube2.mix(crossSolution.get(cube1.getEdge(WHITE, ORANGE)));
            cube2.mix(crossSolution.get(cube1.getEdge(WHITE, GREEN)));
            cube2.mix(crossSolution.get(cube1.getEdge(WHITE, BLUE)));
            cube2.mix(crossSolution.get(cube1.getEdge(WHITE, RED)));
            cube2.mix(f2LSolution.get(cube1.getCorner(BLUE, WHITE, RED)));
            cube2.mix(f2LSolution.get(cube1.getCorner(BLUE, WHITE, ORANGE)));
            cube2.mix(f2LSolution.get(cube1.getCorner(GREEN, WHITE, ORANGE)));
            cube2.mix(f2LSolution.get(cube1.getCorner(GREEN, WHITE, RED)));
            cube2.mix(oLLSolution);
            cube2.mix(pLLSolution);
            if (cube2.solved()) {
                correctAlgs++;
            }
            System.out.println("Cube #" + i + ".0 Solved? " + cube2.solved());
        }
        System.out.println(" \nTotal crosses solved: " + numCrossSolved + "/" + totalTests);
        System.out.println("Total F2L solved: " + numF2LSolved + "/" + totalTests);
        System.out.println("Total OLL solved: " + numOllSolved + "/" + totalTests);
        System.out.println("Total PLL solved: " + numPllSolved + "/" + totalTests);
        System.out.println("Total correct solves: " + numSolved + "/" + totalTests);

        System.out.println("Average moves taken: " + totalMoves/totalTests + " moves");
        System.out.println("Total correct algs: " + correctAlgs + "/" + totalTests);
    }
}
