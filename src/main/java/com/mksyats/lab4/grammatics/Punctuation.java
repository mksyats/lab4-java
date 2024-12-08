package com.mksyats.lab4.grammatics;

/**
 * Represents a punctuation mark in a text.
 */
public class Punctuation extends SentenceItem {

  // the character value of the punctuation
  private final char value;

  /**
   * Constructs a new {@code Punctuation} with the specified character.
   *
   * @param c the character value of the punctuation
   */
  public Punctuation(char c) {
    super();
    this.value = c;
  }

  /**
   * Checks if the specified character is a punctuation mark.
   *
   * @param c the character to check
   * @return {@code true} if the character is a punctuation mark, {@code false} otherwise
   */
  public static boolean isPunctuation(char c) {
    return String.valueOf(c).matches("\\p{Punct}");
  }

  /**
   * Checks if the specified character is a sentence-ending punctuation mark.
   *
   * @param c the character to check
   * @return {@code true} if the character ends a sentence, {@code false} otherwise
   */
  public static boolean isSentenceEnd(char c) {
    return ".!?".indexOf(c) != -1;
  }

  /**
   * Returns the string representation of this punctuation.
   *
   * @return the string containing the character value of the punctuation
   */
  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
