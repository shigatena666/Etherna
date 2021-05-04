package Game.Utilitaries;

public class KeyValuePair<K, V> {
    private K key;
    private V value;

    //region Constructors

    /**
     * Allows you to create a Client.Map(abstract class of HashMap) of length 1 to only have 1 key and 1 value.
     *
     * @param key   The key as generic type.
     * @param value The value as generic type.
     */
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    //endregion

    //region Accessors

    /**
     * @return The value that has been set.
     */
    public V getValue() {
        return this.value;
    }

    /**
     * @param value The new value of the value to set.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * @return The key that has been set.
     */
    public K getKey() {
        return this.key;
    }

    /**
     * @param key The new value of the key to set.
     */
    public void setKey(K key) {
        this.key = key;
    }



    //endregion

    @Override
    public String toString() {
        return "KeyValuePair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

