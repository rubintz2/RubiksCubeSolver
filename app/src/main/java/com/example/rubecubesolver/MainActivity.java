package com.example.rubecubesolver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static com.example.rubecubesolver.Colors.*;
import static com.example.rubecubesolver.PermuteLastLayer.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String mix = "D B' R D' R2 B U' B U F L' F' U' F U' L2 R2 B' F' D";
//        String mix = "F D R2 D2 R D' U B' F2 R D U F2 B L2 B D2 R D L";
//        String mix = "B2 U' B2 U' R2 U B2 D2 L2 R2 U' R D L' R B R2 F' D2 L";
//        String mix = "U2 F2 L' D2 R B2 L B2 R2 U2 L' U L2 R F D' B R' U' R2 U2";
//        String mix = "F R' U' F B2 L U D R D2 F2 R' U2 R' F2 R U2 F2 R2 B2 D'";
//        String mix = "B' D L2 U' L U2 B' U' R2 U L2 U F2 U' F2 U R2 D2 L2";
//        String mix = "D2 B R2 F U2 R2 U2 F2 D2 R2 B2 L U' B D2 L' R' D2 F2 L'";
//        String mix = "U' D2 F' R' B L' B' U' R' F2 D2 L2 F2 U2 B U2 F' U2 R2 B'";
//        String mix = "D' R' F2 U' R2 U B2 D' F2 D F2 U B2 D2 F' L' B2 R' U2 B' R2";
//        String mix = "F2 R2 B' R2 D2 B2 U R2 U2 B2 U' B2 F2 D L' R2 D2 R' B L2 U'";
//        String mix = "F2 D' F2 D2 R' D2 F2 L' U2 R D2 B2 L' R' U' L U2 F R' D U'";
//        String mix = "L2 U B2 D2 F2 R2 U L2 D2 L2 F2 D' F L F2 L2 D' L B D U'";
//        String mix = "F2 R' B' D R' F' R' U L U R2 B2 L2 D2 B2 U' R2 U F2 R2 D2";
//        String mix = "U' L U2 F2 U B' L' R2 U L2 D B2 L2 U B2 D2 F2 R2 U B U";
//        String mix = "R' D' R2 U F' B R' D2 B' F2 R2 U2 F2 U B2 U L2 U' L2 D' R2";
//        String mix = "U' L B' D' F' D2 F2 L' D' R2 U2 D2 F R2 F R2 B2 L2 F2 L2 D2";
//        String mix = "R F2 R F2 D2 L D2 B2 L D2 R' U2 D' F' D2 L' U B' F R B'";
//        String mix = "B2 D2 B2 R2 F2 R D2 R' B2 R' F2 B' D R2 B2 L' F2 R2 F D";
//        String mix = "R2 F U B2 D R2 U2 L2 U' L2 F2 R B L' F2 L D2 L B2";
//        String mix = "U' L B2 D B2 L F' R2 D2 L B2 L F2 U2 B2 L2 F2 R' U2 D";
//        rubeCube.mix(mix);
//        System.out.println(rubeCube);
//        Map<Edge, ArrayList<String>> crossSolution = Cross.solve(rubeCube);
//        System.out.println(rubeCube);
//        Map<Corner, ArrayList<String>> f2LSolution = FirstTwoLayers.solve(rubeCube);
//        System.out.println(rubeCube);
//        ArrayList<String> oLLSolution = OrientLastLayer.solve(rubeCube);
//        System.out.println(rubeCube);
        tester();

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
        }
        System.out.println(" \nTotal crosses solved: " + numCrossSolved + "/" + totalTests);
        System.out.println("Total F2L solved: " + numF2LSolved + "/" + totalTests);
        System.out.println("Total OLL solved: " + numOllSolved + "/" + totalTests);
        System.out.println("Total PLL solved: " + numPllSolved + "/" + totalTests);
        System.out.println("Total correct solves: " + numSolved + "/" + totalTests);

        System.out.println("Average moves taken: " + totalMoves/totalTests + " moves");
    }
}
