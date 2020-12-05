package Encryptor;

import Decryptor.Decrypt;

import java.util.ArrayList;
import java.util.List;

public class Encrypt {

  String message; //the message to be encrypted
  List<String> blocks; // list with 8-character strings
  String ungrouped; //TODO: if there is a block without a pair it is stored here
  static List<Integer> key = new ArrayList<>(); // the key (probably will have to move elsewhere)

  public Encrypt(String message) {
    this.message = message;
    blocks = new ArrayList<>();
  }

  // break up the message into 8 character strings and put them in the list
  void breakUpMessage(int length) { // length of strings
    for (int i = 0; i < message.length(); i += length){ // use i += 8 for 8-character blocks
      if (i + 8 < message.length()) {
        blocks.add(message.substring(i, i + length));
      } else {
        blocks.add(message.substring(i)); // I just add the smaller string for now (no hashtags or anything in the end)
        //TODO: figure out what to do with the last block of code if it's sorter
        //TODO: figure out a mock block for odd block number case (check complexity here again)
      }
    }
  }

  List<Pair> pairUp() { // pair up the blocks of characters in a list of pairs
    List<Pair> pairs = new ArrayList<>();
    for (int i = 0; i < blocks.size(); i += 2) { // 2 is used to pair up consecutive blocks
      if (i+1 < blocks.size()) {
        Pair pair = new Pair(blocks.get(i), blocks.get(i + 1));
        pairs.add(pair);
      } else {
        ungrouped = blocks.get(i); // for now if the last block has no pair we just store it here
      }
    }

    return pairs;
  }



  public static void main(String[] args) {
    String message = args[0]; // get the message from the arguments of the program

    Encrypt encrypt = new Encrypt(message);
    encrypt.breakUpMessage(8); // break up the message into 8-char long strings

    List<Pair> pairs;
    pairs = encrypt.pairUp();

    for (Pair pair : pairs) {
      int numberOfSwaps = 16;
      key.add(numberOfSwaps);
      for (int i = 0; i < numberOfSwaps; i++) { // swap characters 16 times (16 is arbitrarily chosen)
        List<Integer> indexes = pair.swapSubBlocks();
        key.addAll(indexes);
      }
      key.add(pair.first.flip1);
      key.add(pair.first.flip2);
      key.add(pair.second.flip1);
      key.add(pair.second.flip2);
      System.out.println("\n\n");
    }

    System.out.println(pairs);
    System.out.println("\nKey: " + key + " length: " + key.size()+"\n\n");

    Decrypt decrypt = new Decrypt(pairs, key);

    decrypt.undoSwapping();
    decrypt.undoFlipping();

    System.out.println(decrypt.getPairs());
  }
}
