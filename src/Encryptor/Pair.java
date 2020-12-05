package Encryptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pair {

  Block first, second;
  static private Integer totalChars = 16; // the sum of the characters in both strings of the pair


  public Pair(String first, String second) {
    System.out.println("\n\n\nfirst before and after flipping:");
    Block block_first = new Block(first);
    System.out.println("\nsecond before and after flipping:");
    Block block_second = new Block(second);
    this.first = block_first;
    this.second = block_second;
  }

  public Block getFirst() {
    return first;
  }

  public Block getSecond() {
    return second;
  }

  public void setFirst(Block first) {
    this.first = first;
  }

  public void setSecond(Block second) {
    this.second = second;
  }

  List<Integer> swapSubBlocks() { // crossing over
    Random random = new Random();
    int firstIndex = random.nextInt(8); // get two random indexes
    int secondIndex = random.nextInt(8);
    int temp;
    if (firstIndex > secondIndex) { // sort indexes;
      temp = firstIndex;
      firstIndex = secondIndex;
      secondIndex = temp;
    }
    List<Integer> indexes = new ArrayList<>(); // create a list and add the indexes that got swapped (used for key)
    indexes.add(firstIndex);
    indexes.add(secondIndex);
    System.out.println(firstIndex + " " + secondIndex);

    // make copies of the lists
    List<String> copy1 = new ArrayList<>(first.binaryCharacters);
    List<String> copy2 = new ArrayList<>(second.binaryCharacters);

    for (int i = 0; i < first.binaryCharacters.size(); i++) { // make the swap using the copies
      if (i >= firstIndex && i <= secondIndex) {
        first.binaryCharacters.set(i, copy2.get(i));
        second.binaryCharacters.set(i, copy1.get(i));
      }
    }

    return indexes;
  }

  @Override
  public String toString() {
    return "\n\n\nflipped:\nfirst.flip1= " + first.flip1 +
        "    first.flip2= " + first.flip2 +
        "\nsecond.flip1= " + second.flip1 +
        "    second.flip2= " + second.flip2 +
        "\n\nEncryptor.Pair:\n" +
        "first=  " + first.binaryCharacters +
        ",\nsecond= " + second.binaryCharacters + "\n\n";
  }
}
