
/**
 * This class produces a pair.
 * 
 * @author Anica
 *
 * @param <A> type of the first element of the pair.
 * @param <B> type of the second element of the pair.
 */

	public class Pair<A, B> {
		/**
		 * Containers for the elements of the pair.
		 */
	    private A first;
	    private B second;

	    /**
	     * Constructor of the class, produces a pair containing the elements first and second
	     * @param first first element of the pair
	     * @param second second element of the pair
	     */
	    public Pair(A first, B second) {
	        super();
	        this.first = first;
	        this.second = second;
	    }

	    public int hashCode() {
	        int hashFirst = first != null ? first.hashCode() : 0;
	        int hashSecond = second != null ? second.hashCode() : 0;

	        return (hashFirst + hashSecond) * hashSecond + hashFirst;
	    }

	    /**
	     * Getter for the first element of the pair.
	     * @return first element of the pair.
	     */
	    public A getFirst() {
	        return first;
	    }

	    /**
	     * Setter for the first element of the pair.
	     * @param first element of the pair.
	     */
	    public void setFirst(A first) {
	        this.first = first;
	    }

	    /**
	     * Getter for the second element of the pair.
	     * @return second element of the pair.
	     */
	    public B getSecond() {
	        return second;
	    }

	    /**
	     * Setter for the second element of the pair.
	     * @param second element of the pair.
	     */
	    public void setSecond(B second) {
	        this.second = second;
	    }
	}
