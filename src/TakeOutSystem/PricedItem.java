package TakeOutSystem;

/**This is a generic interface called PricedItem. This interface extends
 * Number which means it is a subclass of anything Number, i.e. Integer,
 * floats etc. This interface was created as a means for practice with
 * interfaces as getters and setters could easily be created in classes where
 * they are both needed. It was also created to prevent having to rewrite
 * code if another item different from food is added to takeout system. This
 * interface defines two method to be implemented getPrice(), and setPrice();
 * DRY. setPrice() was not used in this scope of project, so it was commented
 * out. There was no avenue where price of an item needed to change yet. If
 * needed, it can be simply commented out an implemented in necessary class
 *
 * @param <T> (generic class that takes anything number ot its subclasses)
 */
public interface PricedItem<T extends Number> {
    T getPrice();// returns price of object in which it is implemented

//    void setPrice(T price);// sets price of object in which it is implemented
}
