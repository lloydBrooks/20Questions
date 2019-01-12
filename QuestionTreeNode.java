/*QuestionTreeNode.java
   The building block of a QuestionTree. Each node has a payload which contains
   a String content either a question or an answer to it's parent's question,
   and two pointers to child QuestionNodes modeling the yes and no sub-trees.
*/

public class QuestionTreeNode {
   static String QFLAG = "Q:";
   static String AFLAG = "A:";
   private String myPayload; // payload of this node.
   private QuestionTreeNode myYes; // pointer to the yes sub-tree.
   private QuestionTreeNode myNo; // pointer to the no sub-tree.

   // constructor for new empty QuestionNode.
   public QuestionTreeNode() {
      myPayload = null;
      myYes = null;
      myNo = null;
   }

   // Setters
   public void setPayloadQ(String question) {
      if (question == null) throw new IllegalArgumentException();
      myPayload = QFLAG + question;
   }
   public void setPayloadA(String answer) {
      if (answer == null) throw new IllegalArgumentException();
      myPayload = AFLAG + answer;
   }
   public void setYes(QuestionTreeNode yesChild) {
      if (yesChild == null) throw new IllegalArgumentException();
      myYes = yesChild; 
   }
   public void setNo(QuestionTreeNode noChild) {
      if (noChild == null) throw new IllegalArgumentException();
      myNo = noChild;
   }

   // Getters 
   public String getPayload() {
      return myPayload;
   }

   public QuestionTreeNode getYes() {
      return myYes;
   }

   public QuestionTreeNode getNo() {
      return myNo;
   }

   // Returns ture if the payload is a question.
   public boolean isQuestion() {
      if ("Q:".equals(myPayload.substring(0, 2))) {
         return true;
      } else {
         return false;
      }
   }

   // returns the payload with the question/answer flag removed
   public String toString() {
      return myPayload.substring(2);
   }

}