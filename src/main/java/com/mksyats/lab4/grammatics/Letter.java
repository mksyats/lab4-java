package com.mksyats.lab4.grammatics;

/**
 * Represents a single letter in a text.
 */
public class Letter {

  // the character value of the letter
  private final char value;

  /**
   * Constructs a new {@code Letter} with the specified character.
   *
   * @param c the character value of the letter
   */
  public Letter(char c) {
    this.value = c;
  }

  /**
   * Compares this letter with another letter for equality.
   *
   * @param other      the other letter to compare
   * @param ignoreCase if {@code true}, ignores case differences during comparison
   * @return {@code true} if the letters are equal considering the case sensitivity, {@code false}
   * otherwise
   */
  public boolean equals(Letter other, boolean ignoreCase) {
    return ignoreCase
        ? Character.toLowerCase(value) == Character.toLowerCase(other.value)
        : value == other.value;
  }

  /**
   * Returns the string representation of the letter.
   *
   * @return the string containing the character value of this letter
   */
  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
