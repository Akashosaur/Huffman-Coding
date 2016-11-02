// Akash Arora
// CSE 143
// 3/11/16
// Stuart Reges
// Assignment 8: Huffman Tree
//
//
// This class is used to store a single, comparable node of
// a binary tree of int data (an int representation of
// a character) and int frequency (frequency of character in
// a text). 
public class HuffmanNode implements Comparable<HuffmanNode> {
   public int data;
   public int frequency;
   public HuffmanNode left;
   public HuffmanNode right;
   
   // pre: takes in int data and int frequency
   // post: constructs a leaf node with given data and frequency
   public HuffmanNode(int data, int frequency) {
      this.data = data;
      this.frequency = frequency;
   }
   
   // pre: takes in int frequency
   // post: constructs a leaf node with given frequency
   public HuffmanNode(int frequency) {
      this(frequency, null, null);
   }
   
   // pre: takes in int frequency, and two HuffmanNodes
   // post: constructs a HuffmanNode with given frequency, left subtree
   //       (first HuffmanNode passed), and right subtree (second
   //       HuffmanNode passed)
   public HuffmanNode(int frequency, HuffmanNode left,
   HuffmanNode right) {
      this.frequency = frequency;
      this.left = left;
      this.right = right;
   }
   
   // takes in another HuffmanNode and compares it to this
   // HuffmanNode. Comapred to each other based on frequency. 
   // A node with higher frequency is "bigger" than its counterpart.
   // If they have the same frequency, they are considered equal.
   public int compareTo(HuffmanNode other) {
      return frequency - other.frequency;
   }
}