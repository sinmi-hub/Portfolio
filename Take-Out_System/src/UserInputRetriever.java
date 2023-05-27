package TakeOutSystem;

/**This is an interface that take in user input in the Take-out system in order
 * for customers to order food. The method defined takes in an int parameter
 * which represents a user input. We’ll be working with int type user input to
 * drive various prompts in our take-out system. This interface create a
 * generic user input method to keep code DRY and reusable
 *
 * @param <T>
 */
public interface UserInputRetriever<T> {
    /* This method creates output based on user input (parameter, selection).
     It also throws IllegalArgumentException if parameter is not an int type*/
     T produceOutputOnIntUserInput (int selection) throws IllegalArgumentException;
}
