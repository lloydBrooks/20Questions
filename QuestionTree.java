import java.util.*;
import java.io.*;

public class QuestionTree
{
   private UserInterface myUI; 
   private QuestionTreeNode myRoot;
   private int gameCount;
   private int winCount;
   
   //constructor makes new tree with a default "computer" answer node.
   public QuestionTree (UserInterface ui) {
      myUI = ui;
      myRoot = new QuestionTreeNode();
      myRoot.setPayloadA("computer");
      gameCount = 0;
      winCount = 0;   
   }
   
   /*Does the actual work, playing a round of 20 questions with the user. Calls 
   the private version of play which uses recusion to come to a final guess and
   process the results of the guess acordingly.
   */
   public void play() { 
      play(myRoot);
   }
   /*
   If current node is a Question node ask the question, else guess and record 
   result.
   */
   private void play(QuestionTreeNode currNode) {
      if (currNode.isQuestion()) {
         //ask the question.
         myUI.println(currNode.toString());
         // if answer yes recurse down yes. if answer no recurse down no.
         if (myUI.nextBoolean()) {
            play(currNode.getYes());
         } else {
            play(currNode.getNo());
         }
      } else { //currNode is an answer
         //guess.
         myUI.println("Would your object happen to be " + currNode + "? ");
         gameCount++;//incroment game count.
         // if answer yes computer win, if answer no computer looses.
         if (myUI.nextBoolean()) { //answer yes
            winCount++; //just need to update the winCount.
            myUI.println("I win!\n");
         } else { //answer no
            // get correct object and new question:
            myUI.println("I loose. What is your object?");
            String correct = myUI.nextLine(); //the player's object.
            myUI.println("Type a (y/n) question to distinguish you objet from " 
               + currNode + ": ");
            String newQ = myUI.nextLine(); //the player's new quesiton.
            myUI.println("And what is the answer (y/n) for your question?");
            boolean newQA = myUI.nextBoolean(); //the answer to the player's q.
            //update the tree with the new object, question, and answer:
            update(currNode, correct, newQ, newQA);
         }
      }
   }
   /*
   Private method to update the tree with a new object when the computer fails
   to guess the object and gathers the info from the user to get to this object.
   Must be passed an answer node otherwise it could loose a part of the tree.
   */
   private void update(QuestionTreeNode currNode, String newObject 
         ,String question, boolean answer) {
      //check that the currNode is an answer not a question.
      if (currNode.isQuestion()) {
          throw new IllegalArgumentException("Passed question node to update!");
      } 
      //create new child nodes and add payloads:
      QuestionTreeNode newObjectNode = new QuestionTreeNode();
      newObjectNode.setPayloadA(newObject);
      QuestionTreeNode oldObjectNode = new QuestionTreeNode();
      oldObjectNode.setPayloadA(currNode.toString());
      currNode.setPayloadQ(question);
      //reorganize tree:
      if (answer) { //if the answer to the new question is yes:
         currNode.setNo(oldObjectNode);
         currNode.setYes(newObjectNode);
      } else { //if the answer to the new question is no:
         currNode.setNo(newObjectNode);
         currNode.setYes(oldObjectNode);
      }
   }
   
   /*
   Saves the contents of the QuestionTree to the provided printStream.
   */
   public void save(PrintStream output) {
      myUI.println("Saving...");
      save(myRoot, output);//call private recursive save method.
      myUI.println("Saved the current game.");
   }
   /*
   Private recursive helper for save which saves the tree in a preorder 
   traversal using the supplied PrintStream. 
   */
   private void save(QuestionTreeNode currNode, PrintStream output) {
      output.append(currNode.getPayload()+"\n");
      QuestionTreeNode left = currNode.getYes();
      QuestionTreeNode right = currNode.getNo();
      if(left != null) {
         save(left, output);
      }
      if (right != null) {
         save(right, output);
      }
   }

   /*
      Loads a save.txt file into the QuestionTree.
   */
   public void load(Scanner input) {
      myUI.println("Loading...");
      load(myRoot, input); //call private recursive load method.
      myUI.println("Loaded old save.");
   }
   /*
   Private recursive helper for load which builds a tree using a preorder 
   traversal of the .txt save file from the Scanner.
   */
   private void load(QuestionTreeNode currNode, Scanner input) { 
      if(input.hasNextLine()) {
         String current = input.nextLine();
         if(QuestionTreeNode.QFLAG.equals(current.substring(0,2))) {
            currNode.setPayloadQ(current.substring(2)); 
            QuestionTreeNode newYes = new QuestionTreeNode();
            QuestionTreeNode newNo = new QuestionTreeNode();
            currNode.setYes(newYes);
            currNode.setNo(newNo);
            load(newYes, input); //recurse down yes subtree.
            load(newNo, input); // then recurse down no subtree.
         } else if(QuestionTreeNode.AFLAG.equals(current.substring(0,2))) {
            currNode.setPayloadA(current.substring(2));
         } else {
            throw new IllegalStateException("save file not properly formated!");
         }
      }
   }
   
   //simply returns the curent number of games that have been played
   public int totalGames() {
      return gameCount;
   }
   
   //returns the number of games the computer has won
   public int gamesWon() {
      return winCount;
   }
}