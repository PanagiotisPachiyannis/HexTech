package Decryptor;

import Encryptor.Block;
import Encryptor.Pair;

import java.util.ArrayList;
import java.util.List;

public class Decrypt {

  final List<Integer> key;
  List<Pair> pairs;
  int numberOfBlocks;

  public Decrypt(List<Pair> pairs, List<Integer> key) {
    this.key = key;
    this.pairs = pairs;
    this.numberOfBlocks = (key.size() - 1) / 18;
  }

  public List<Pair> getPairs() {
    return pairs;
  }

  public void undoFlipping() {
    int startFlipKey = key.size() - pairs.size() * 4;
    for (int i = 0; i < pairs.size(); i++) {
        int firstFirstIndex = key.get(startFlipKey + i * 4);
        int firstSecondIndex = key.get((startFlipKey + i * 4) + 1);
        int secondFirstIndex = key.get((startFlipKey + i * 4) + 2);
        int secondSecondIndex = key.get((startFlipKey + i * 4) + 3);
        undoPairFlip(pairs.get(i),firstFirstIndex,firstSecondIndex,secondFirstIndex,secondSecondIndex);
    }
  }

  private void undoPairFlip(Pair pair, int firstFirstIndex, int firstSecondIndex, int secondFirstIndex, int secondSecondIndex) {
    pair.setFirst(undoBlockFlip(pair.getFirst(), firstFirstIndex, firstSecondIndex));
    pair.setSecond(undoBlockFlip(pair.getSecond(), secondFirstIndex, secondSecondIndex));
  }

  private Block undoBlockFlip(Block block, int firstIndex, int secondIndex) {
    String binary1 = block.getBinaryCharacters().get(firstIndex);
    int toInt1 = Integer.parseInt(binary1, 2);
    int flipped1 = ~toInt1;
    String flippedStr1 = Integer.toBinaryString(flipped1);
    block.setBinaryCharacterAt(flippedStr1.substring(flippedStr1.length() - 7), firstIndex);

    String binary2 = block.getBinaryCharacters().get(secondIndex);
    int toInt2 = Integer.parseInt(binary2, 2);
    int flipped2 = ~toInt2;
    String flippedStr2 = Integer.toBinaryString(flipped2);
    block.setBinaryCharacterAt(flippedStr2.substring(flippedStr2.length() - 7), secondIndex);

    return block;
  }

  public void undoSwapping() {
    int numOfIterations = key.get(0);
    for (int p = 0; p < pairs.size(); p++) { // dodgy
      for (int i = numOfIterations; i > 0; i--) {
        pairs.set(p, undoPairSwap((key.get(i * 2 - 1) + numOfIterations * 2 * p),
            key.get((i * 2) + numOfIterations * 2 * p), pairs.get(p)));
      }
    }
  }

  private Pair undoPairSwap(int firstIndex, int secondIndex, Pair pair) {
    Block first = pair.getFirst();
    Block second = pair.getSecond();

    List<String> copy1 = new ArrayList<>(first.getBinaryCharacters());
    List<String> copy2 = new ArrayList<>(second.getBinaryCharacters());

    for (int i = 0; i < first.getBinaryCharacters().size(); i++) { // make the swap using the copies
      if (i >= firstIndex && i <= secondIndex) {
        first.getBinaryCharacters().set(i, copy2.get(i));
        second.getBinaryCharacters().set(i, copy1.get(i));
      }
    }
    return pair;
  }



}
