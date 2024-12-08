package com.mksyats.lab4.grammatics;

/**
 * Represents a sentence, which consists of a sequence of {@link SentenceItem} objects. A sentence
 * can include words and punctuation marks.
 */
public class Sentence {

  // the array of SentenceItem objects that make up the sentence
  private final SentenceItem[] items;

  // the number of leading spaces before the first sentence item
  public int leadSpaceCount = 0;

  /**
   * Constructs a {@code Sentence} object by parsing the provided text. The input text is split into
   * words and punctuation marks, and spaces are counted.
   *
   * @param sentence the {@link StringBuilder} containing the text of the sentence
   * @throws IllegalArgumentException if the input contains invalid characters
   */
  public Sentence(StringBuilder sentence) {
    char[] chars = new char[sentence.length()];
    sentence.getChars(0, sentence.length(), chars, 0);
    items = new SentenceItem[countItems(chars)];
    int curItemCount = 0;
    StringBuilder curWord = new StringBuilder();

    for (char c : chars) {
      if (Character.isLetter(c)) {
        curWord.append(c);
      } else {
        if (!curWord.isEmpty()) {
          items[curItemCount++] = new Word(curWord);
          curWord.setLength(0);
        }

        if (Punctuation.isPunctuation(c)) {
          items[curItemCount++] = new Punctuation(c);
        } else if (Character.isSpaceChar(c)) {
          if (curItemCount > 0) {
            items[curItemCount - 1].trailSpaceCount++;
          } else {
            leadSpaceCount++;
          }
        } else {
          throw new IllegalArgumentException("invalid character: '" + c + "'");
        }
      }
    }
  }

  /**
   * Constructs a {@code Sentence} from an array of {@link SentenceItem} objects. This constructor
   * is used internally to create modified or transformed sentences.
   *
   * @param items the array of {@link SentenceItem} objects making up the sentence
   */
  private Sentence(SentenceItem[] items) {
    this.items = items;
  }

  /**
   * Counts the number of {@link SentenceItem} objects that will be created from the given array of
   * characters.
   *
   * @param chars the array of characters representing the sentence text
   * @return the number of sentence items
   */
  private int countItems(char[] chars) {
    int count = 0;
    boolean isInsideWord = false;

    for (char c : chars) {
      if (Character.isLetter(c)) {
        isInsideWord = true;
      } else {
        if (isInsideWord) {
          isInsideWord = false;
          count++;
        }
        if (Punctuation.isPunctuation(c)) {
          count++;
        }
      }
    }

    return count;
  }

  /**
   * Removes the longest substring of this sentence between the specified start and end letters. The
   * search can be case-insensitive.
   *
   * @param startLetter the letter marking the start of the substring to remove
   * @param endLetter   the letter marking the end of the substring to remove
   * @param ignoreCase  if {@code true}, ignores case differences when searching for the letters
   * @return a new {@code Sentence} object with the substring removed
   */
  public Sentence withoutLongestSubstr(Letter startLetter, Letter endLetter, boolean ignoreCase) {
    var start = findLetter(startLetter, ignoreCase, false);
    var end = findLetter(endLetter, ignoreCase, true);
    return withoutSubstr(start, end);
  }

  /**
   * Represents the position of a letter within the sentence, identified by the index of the word
   * and the index of the letter within the word.
   */
  private record LetterPos(int wordIdx, int letterIdx) {

    /**
     * Checks if this position is after another position.
     *
     * @param other the other position to compare with
     * @return {@code true} if this position is after the other position, {@code false} otherwise
     */
    boolean isAfter(LetterPos other) {
      return wordIdx > other.wordIdx || (wordIdx == other.wordIdx && letterIdx > other.letterIdx);
    }
  }

  /**
   * Finds the position of the specified letter in this sentence.
   *
   * @param target     the letter to find
   * @param ignoreCase if {@code true}, ignores case differences when searching for the letter
   * @param isReversed if {@code true}, searches from the end of the sentence
   * @return the position of the letter, or {@code null} if the letter is not found
   */
  private LetterPos findLetter(Letter target, boolean ignoreCase, boolean isReversed) {
    int start = isReversed ? items.length - 1 : 0;
    int end = isReversed ? -1 : items.length;
    int step = isReversed ? -1 : 1;

    for (int i = start; i != end; i += step) {
      if (items[i] instanceof Word word) {
        int letterIdx = word.indexOf(target, ignoreCase, isReversed);
        if (letterIdx >= 0) {
          return new LetterPos(i, letterIdx);
        }
      }
    }
    return null;
  }

  /**
   * Removes the substring of this sentence between the specified positions.
   *
   * @param start the start position (inclusive) of the substring to remove
   * @param end   the end position (inclusive) of the substring to remove
   * @return a new {@code Sentence} object with the substring removed, or the original sentence if
   * the positions are invalid
   */
  private Sentence withoutSubstr(LetterPos start, LetterPos end) {
    if (start == null || end == null || start.isAfter(end)) {
      return this;
    }

    var startWord = (Word) items[start.wordIdx];
    var endWord = (Word) items[end.wordIdx];
    Word newWord = Word.concat(startWord, start.letterIdx, endWord, end.letterIdx);

    int prefixLen = start.wordIdx;
    int suffixLen = items.length - end.wordIdx - 1;
    int newSentenceLen = prefixLen + (newWord == null ? 0 : 1) + suffixLen;

    SentenceItem[] newItems = new SentenceItem[newSentenceLen];
    Sentence newSentence = new Sentence(newItems);
    newSentence.leadSpaceCount = leadSpaceCount;

    int curItemCount = 0;

    for (int i = 0; i < prefixLen; i++) {
      newItems[curItemCount++] = items[i];
    }

    if (newWord != null) {
      newItems[curItemCount++] = newWord;
    } else if (curItemCount > 0) {
      newItems[curItemCount - 1].trailSpaceCount += endWord.trailSpaceCount;
    } else {
      newSentence.leadSpaceCount += endWord.trailSpaceCount;
    }

    for (int i = end.wordIdx + 1; i < items.length; i++) {
      newItems[curItemCount++] = items[i];
    }

    return newSentence;
  }

  /**
   * Returns the string representation of the sentence, including spaces.
   *
   * @return the string containing all sentence items with proper spacing
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (SentenceItem item : items) {
      sb.append(item).append(" ".repeat(item.trailSpaceCount));
    }
    return sb.toString();
  }
}
