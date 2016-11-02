// Akash Arora
// CSE 143
// 3/11/16
// Stuart Reges
// Assignment 8: Huffman Tree
//
//
// This class compresses a text file using a coding scheme based on the frequency
// of characters. Instead of the usual seven or eight bits per character,
// this class employs the Huffman method, and uses only a few bits for characters that
// are used often, and more bits for those that rarely used. This class can also
// encode to a standard format, and decode files in standard format to text files.
import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode overallRoot;
   private Queue<HuffmanNode> treeDeveloper;
   
   // pre: takes in an array of int in which the index corresponds
   //      to the int value of a character and the respective elements
   //      are the number of occurances of the particular character
   // post: constructs a Huffman tree using the given array of frequencies.
   public HuffmanTree(int[] count) {
      treeDeveloper = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            treeDeveloper.add(new HuffmanNode(i, count[i]));
         }
      }
      treeDeveloper.add(new HuffmanNode(count.length, 1));
      while (treeDeveloper.size() != 1) {
         HuffmanNode temp = treeDeveloper.remove();
         HuffmanNode temp2 = treeDeveloper.remove();
         treeDeveloper.add(new HuffmanNode(temp.frequency+temp2.frequency, temp, temp2));
      }
      overallRoot = treeDeveloper.remove();
   }
   
   // pre: takes in a Scanner input file stored in standard format
   // post: reconstructs the tree from the file
   public HuffmanTree(Scanner input) {
      overallRoot = new HuffmanNode(-1);
      while (input.hasNext()) {
         int n = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = treeHelper(overallRoot, n, code);
      }
   }
   
   // pre: takes in a PrintStream output
   // post: writes this object's tree to the given output stream
   //       in standard format.
   public void write (PrintStream output) {
      writeHelper(output, overallRoot, "");
   }
   
   // pre: takes in a BitInputStream input, PrintStream output, and
   //      int pseudo eof. input stream contains a legal encoding of
   //      characters for this tree's Huffman code
   // post: reads individual bits from the input stream and writes
   //       the corresponding characters to the output. stops reading
   //       when it encounters a character with value equal to the eof
   //       parameter. (and does not write this pseud-eof character to
   //       PrintStream)
   public void decode(BitInputStream input, PrintStream output, int eof) {
      int data = decodeHelper(input, overallRoot);;
      while (data != eof) {
         output.write(data);
         data = decodeHelper(input, overallRoot);
      }
   }
   
   // pre: takes in a HuffmanNode root, int  value of character,
   //      and String code
   // post: reconstructs the tree by creating a leaf with desired
   //       character value. helper method to HuffmanTree(Scanner input)
   //       constructor
   private HuffmanNode treeHelper (HuffmanNode root, int n, String code) {
      char current = code.charAt(0);
      if (code.length() == 1) {
         if (current == '0') {
            root.left = new HuffmanNode(n, -1);
         } else {
            root.right = new HuffmanNode(n, -1);
         }
      }
      else {
         if (current == '0') {
            if (root.left == null) {
               root.left = new HuffmanNode(-1);
            }
            root.left = treeHelper(root.left, n, code.substring(1));
         } else {
            if (root.right == null) {
               root.right = new HuffmanNode(-1);
            }
            root.right = treeHelper(root.right, n, code.substring(1));
         }
      }
      return root;
   }
   
   // pre: takes in a PrintStream output, HuffmanNode root, and String result
   // post: writes (this) tree to the given output streamin standard format.
   //       Helper method to write
   private void writeHelper (PrintStream output, HuffmanNode root, String result) {
      if (root != null) {
         if (root.left == null && root.right == null) {
            output.println(root.data);
            output.println(result);
         } else {
            writeHelper(output, root.left, result + "0");
            writeHelper(output, root.right, result + "1");
         }
      }
   }
   
   // pre: takes in a BitInputStream input and HuffmanNode root. input
   //      stream contains a legal encoding of characters for this tree's
   //      Huffman code
   // post: reads individual bits from the input stream and navigates
   //       this Huffman tree accordingly until a character
   //       is reached. returns the int value of the reached character.
   //       helper method to decode
   private int decodeHelper(BitInputStream input, HuffmanNode root) {
      if (root.left != null && root.right != null) {
         if (input.readBit() == 0) {
            return decodeHelper(input, root.left);
         } else {
            return decodeHelper(input, root.right);
         }
      }
      return root.data;
   }
}