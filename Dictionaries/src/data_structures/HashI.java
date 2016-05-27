package data_structures;

import java.util.Iterator;

public interface HashI<K, V> extends Iterable<K> {

	/**
	 * key/value pair is added to the dictionary
	 * @param key key value of the object
	 * @param value
	 * @return true if addition is successful, else false
	 */
	public boolean add(K key, V value);
	
	/**
	 * Remove the object in the dictionaries
	 * @param key keys to find the object in the dictionary
	 * @return true if remove is successful, else false
	 */
	public boolean remove(K key);
	
	/**
	 * Retrieve the value of the object in the dictionary
	 * using key value
	 * @param key used to find the object
	 * @return the value associated with the object
	 */
	public V getValue(K key);
	
	/**
	 * The amount of key/value pairs in the dictionary
	 * @return how many objects there are in the dictionary
	 */
	public int size();
	
	/**
	 * Determine if dictionary is empty
	 * @return true if empty, else false
	 */
	public boolean isEmpty();
	
	/**
	 * Load Factor of the dictionary
	 * @return load factor represented with a double
	 */
	public double loadFactor();
	
	/**
	 * Resize method for the dictionary
	 * @param newSize double the size of the original size
	 */
	public void resize(int newSize);
	
	/**
	 * Returns an iterator of the keys in the dictionary in a 
	 * sorted order
	 */
	public Iterator<K> iterator();
	
	/**
	 * Iterator of the keys in the dictionary
	 * @return iterator of the keys in the dictionary sorted
	 */
	public Iterator<K> keys();
	
	/**
	 * Iterator of the values in the dictionary
	 * @return iterator of the values in the dictionary sorted
	 */
	public Iterator<V> values();
	
}
