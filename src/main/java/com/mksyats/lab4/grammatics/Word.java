package com.mksyats.lab4.grammatics;

/**
 * Represents a word in a sentence, which is a sequence of {@code Letter} objects.
 */
public class Word extends SentenceItem {

  // the array of letters composing this word
  private final Letter[] letters;

  /**
   * Constructs a new {@code Word} from the specified {@code StringBuilder}.
   *
   * @param word the {@code StringBuilder} containing the characters of the word
   */
  public Word(StringBuilder word) {
    super();

    char[] chars = new char[word.length()];
    word.getChars(0, word.length(), chars, 0);
    letters = new Letter[chars.length];
    for (int i = 0; i < chars.length; i++) {
      letters[i] = new Letter(chars[i]);
    }
  }

  /**
   * Constructs a {@code Word} directly from an array of {@code Letter} objects. This constructor is
   * intended for internal use, allowing for efficient creation of a {@code Word} when the letters
   * are already available in an array format.
   *
   * @param letters the array of {@code Letter} objects representing the word
   */
  private Word(Letter[] letters) {
    super();
    this.letters = letters;
  }

  /**
   * Finds the index of the specified letter in this word.
   *
   * @param target     the letter to find
   * @param ignoreCase if {@code true}, ignores case differences during comparison
   * @param isReversed if {@code true}, searches from the end of the word
   * @return the index of the letter if found, or {@code -1} otherwise
   */
  public int indexOf(Letter target, boolean ignoreCase, boolean isReversed) {
    int start = isReversed ? letters.length - 1 : 0;
    int end = isReversed ? -1 : letters.length;
    int step = isReversed ? -1 : 1;

    for (int i = start; i != end; i += step) {
      if (letters[i].equals(target, ignoreCase)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the number of letters in this word.
   *
   * @return the length of the word
   */
  public int length() {
    return letters.length;
  }

  /**
   * Concatenates portions of two words to create a new word.
   *
   * @param startWord the starting word
   * @param startTo   the index in the starting word up to which letters are taken
   * @param endWord   the ending word
   * @param endFrom   the index in the ending word from which letters are taken
   * @return the newly created word, or {@code null} if the parameters are invalid
   */
  public static Word concat(Word startWord, int startTo, Word endWord, int endFrom) {
    if (startTo >= startWord.length() || endFrom < 0) {
      return null;
    }

    int suffixLen = endWord.length() - endFrom - 1;
    int newWordLen = startTo + suffixLen;
    if (newWordLen == 0) {
      return null;
    }

    Letter[] newWordLetters = new Letter[newWordLen];
    System.arraycopy(startWord.letters, 0, newWordLetters, 0, startTo);
    System.arraycopy(endWord.letters, endFrom + 1, newWordLetters, startTo, suffixLen);

    Word newWord = new Word(newWordLetters);
    newWord.trailSpaceCount = endWord.trailSpaceCount;
    return newWord;
  }

  /**
   * Returns the string representation of this word.
   *
   * @return the string containing all the letters in the word
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Letter letter : letters) {
      sb.append(letter.toString());
    }
    return sb.toString();
  }
}
