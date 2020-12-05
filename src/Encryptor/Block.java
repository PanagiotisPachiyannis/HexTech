package Encryptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Block {

  private String string; // redundant after initialization of the binary list
  List<String> binaryCharacters;
  int flip1, flip2;

  public Block(String string) {
    this.string = string;
    toBinary();
    System.out.println(binaryCharacters);
    getFlippingPoints();
    flip();
    System.out.println(binaryCharacters);
  }

  public List<String> getBinaryCharacters() {
    return binaryCharacters;
  }

  void toBinary() { // updates the list with a binary representation of each character (stored as string)
    List<String> binaryList = new ArrayList<>();
    for (int i = 0; i < string.length(); i++) {
      int toNumber = Character.getNumericValue(string.charAt(i));
      String toBinary = Integer.toBinaryString(toNumber); // 7bits max per character
      binaryList.add(toBinary);
    }
    this.binaryCharacters = binaryList;
  }

  void getFlippingPoints() {
    Random random = new Random();
    flip1 = random.nextInt(8); // get two random chars to flip
    flip2 = random.nextInt(8);
  }

  public void setBinaryCharacterAt(String binaryCharacters, int index) {
    this.binaryCharacters.set(index, binaryCharacters);
  }

  void flip() { // flips the marked characters
    String binary1 = binaryCharacters.get(flip1);
    int toInt1 = Integer.parseInt(binary1, 2);
    int flipped1 = ~toInt1;
    String flippedStr1 = Integer.toBinaryString(flipped1);
    System.out.println("flipped char 1 " + flippedStr1);
    String subflipped1 = flippedStr1.substring(flippedStr1.length() - 7);
    System.out.println("subflipped1: " + subflipped1);
    binaryCharacters.set(flip1, subflipped1);

    String binary2 = binaryCharacters.get(flip2);
    int toInt2 = Integer.parseInt(binary2, 2);
    int flipped2 = ~toInt2;
    String flippedStr2 = Integer.toBinaryString(flipped2);
    System.out.println("flipped char 2 " + flippedStr2);
    String subflipped2 = flippedStr2.substring(flippedStr2.length() - 7);
    System.out.println("subflipped2: " + subflipped2);
    binaryCharacters.set(flip2, subflipped2);
  }
}
