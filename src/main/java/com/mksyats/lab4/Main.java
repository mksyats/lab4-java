package com.mksyats.lab4;

import com.mksyats.lab4.grammatics.Letter;
import com.mksyats.lab4.grammatics.Sentence;
import com.mksyats.lab4.grammatics.Text;

/**
 * Entry point for the application that processes a textual input by modifying each sentence.
 *
 * <p>The program performs the following operations:</p>
 * <ol>
 *   <li>Creates a {@link Text} object from a predefined text.</li>
 *   <li>Iterates through each {@link Sentence} in the text.</li>
 *   <li>Removes the longest substring between two specified {@link Letter} characters
 *       in each sentence (inclusive of the start and end letters).</li>
 *   <li>Outputs the modified text to the console.</li>
 * </ol>
 *
 * <p>In case of invalid input, the program handles exceptions gracefully by displaying an error
 * message and exiting with an error code.</p>
 */
public class Main {

  /**
   * The main method, which serves as the entry point of the application.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    final StringBuilder TEXT = new StringBuilder("""
        Велика вежа стояла на вершині гори, і вид з неї був просто неймовірний! Як далеко \
        тягнеться цей туман, що оповив все навколо? Ранкове сонце теплом обіймало землю, чи може \
        бути щось прекрасніше? Вітер дув настільки легко, що здавалось, ніби він лагідно торкався \
        кожного листочка. Десь далеко чулося тихе дзюрчання струмка, а чи могли туристи оминути це \
        місце? Вони зупинялися тут, щоб відчути гармонію природи. Кожен знаходив тут свій спокій.\
        """);
    final Letter START_LETTER = new Letter('в'), END_LETTER = new Letter('т');
    final boolean IGNORE_CASE = true;

    try {
      var txt = new Text(TEXT);
      for (int i = 0; i < txt.sentences.length; i++) {
        Sentence sentence = txt.sentences[i];
        txt.sentences[i] = sentence.withoutLongestSubstr(START_LETTER, END_LETTER, IGNORE_CASE);
      }
      System.out.println(txt);
    } catch (IllegalArgumentException e) {
      System.err.println("Error: " + e.getMessage() + '.');
      System.exit(1);
    }
  }
}
