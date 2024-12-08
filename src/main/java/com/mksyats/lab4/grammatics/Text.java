package com.mksyats.lab4.grammatics;

/**
 * Represents a text, which is composed of multiple {@link Sentence} objects. The class provides
 * functionality to parse a given text into sentences and normalize its structure.
 */
public class Text {

  /**
   * The array of {@link Sentence} objects that make up the text.
   */
  public Sentence[] sentences;

  /**
   * Constructs a {@code Text} object by parsing the provided text. The input text is split into
   * sentences based on sentence-ending punctuation marks. Whitespaces are normalized during
   * parsing.
   *
   * @param text the {@link StringBuilder} containing the text to be parsed
   * @throws IllegalArgumentException if the input is {@code null}, empty, does not start with an
   *                                  uppercase letter, or does not end with a sentence-ending
   *                                  punctuation mark
   */
  public Text(StringBuilder text) {
    if (text == null || text.isEmpty()) {
      throw new IllegalArgumentException("text must be a non-empty StringBuilder instance");
    }

    if (!Character.isUpperCase(text.charAt(0))) {
      throw new IllegalArgumentException("text should start with an uppercase letter");
    }

    if (!Punctuation.isSentenceEnd(text.charAt(text.length() - 1))) {
      throw new IllegalArgumentException(
          "the last character of the text should be a sentence-ending punctuation mark");
    }

    normalizeWhitespaces(text);

    char[] chars = new char[text.length()];
    text.getChars(0, text.length(), chars, 0);
    sentences = new Sentence[countSentences(chars)];
    int curSentenceCount = 0;
    StringBuilder curSentence = new StringBuilder();

    for (char curChar : chars) {
      curSentence.append(curChar);
      if (Punctuation.isSentenceEnd(curChar)) {
        sentences[curSentenceCount++] = new Sentence(curSentence);
        curSentence.setLength(0);
      }
    }
  }

  /**
   * Normalizes whitespaces in the given {@link StringBuilder}. Consecutive whitespace characters
   * are replaced with a single space.
   *
   * @param sb the {@link StringBuilder} whose whitespaces need to be normalized
   */
  private void normalizeWhitespaces(StringBuilder sb) {
    for (int i = sb.length() - 1; i > 1; i--) {
      if (Character.isWhitespace(sb.charAt(i - 1))) {
        sb.setCharAt(i - 1, ' ');

        if (Character.isWhitespace(sb.charAt(i))) {
          sb.deleteCharAt(i);
        }
      }
    }
  }

  /**
   * Counts the number of sentences in the provided array of characters. Sentences are identified by
   * sentence-ending punctuation marks.
   *
   * @param chars the array of characters representing the text
   * @return the number of sentences in the text
   */
  private int countSentences(char[] chars) {
    int count = 0;
    for (char c : chars) {
      if (Punctuation.isSentenceEnd(c)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Returns the string representation of the text, with all sentences properly formatted and
   * spaced.
   *
   * @return a formatted string containing all the sentences of the text
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Sentence sentence : sentences) {
      sb.append(" ".repeat(sentence.leadSpaceCount)).append(sentence);
    }
    return sb.toString();
  }
}
