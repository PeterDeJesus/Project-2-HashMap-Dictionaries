package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author Peter De Jesus
 * 		   CS310 - Data Structures
 * 		   Assignment 2
 *
 * This code is the Hash class of which will use chaining
 * where an array is created that will contain a linked list for
 * each indexes.
 *
 * @param <K> the key element
 * @param <V> the value element corresponding with the key
 */

public class Hash<K, V> implements HashI<K, V> {
	
	@SuppressWarnings("hiding")
	class HashElement<K, V> implements Comparable<HashElement<K, V>> {
		K key;
		V value;
		
		public HashElement(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		@SuppressWarnings("unchecked")
		public int compareTo(HashElement<K, V> obj) {
			return (((Comparable<K>)obj.key).compareTo(this.key));
		}
	}//HashELement inner class

	LinkedList<HashElement<K, V>> [] array;
	int tableSize;
	int numElements;
	double maxLoadFactor;
	
	/**
	 * Construct a hash and then assign a linked list for each slot
	 * @param size the size of the array hash
	 */
	@SuppressWarnings("unchecked")
	public Hash(int size) {
		this.tableSize = size;
		array = (LinkedList<HashElement<K ,V>> []) new LinkedList[size];
		
		//create linked list for each empty array index
		for (int i = 0; i < size; i++)
			array[i] = new LinkedList<HashElement<K, V>>();
		
		maxLoadFactor = 0.75;
	}
	
	/**
	 * Iterator class that deals with the hash elements:
	 * keys and values.
	 *
	 * @param <E> generic type E
	 */
	abstract class IteratorHelper<E> implements Iterator<E> {
		protected HashElement<K, V>[] node;
		protected int index;
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public IteratorHelper() {
			node = new HashElement[numElements];
			index = 0;
			int j = 0;
			for (int i = 0; i < tableSize; i++)
				for (HashElement n : array[i])
					node[j++] = n;
		}
		
		public boolean hasNext() {
			return index < numElements;
		}
		
		public abstract E next();
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Iterator that deals with the key
	 * @param <K> key of the hash
	 */
	@SuppressWarnings("hiding")
	class KeyIteratorHelper<K> extends IteratorHelper<K> {
		public KeyIteratorHelper() {
			super();
			}
		
		@SuppressWarnings("unchecked")
		public K next() {
			return (K) node[index++].key;
		}
	}
	
	/**
	 * Iterator that deals with the values
	 * @param <V> values of the hash
	 */
	@SuppressWarnings("hiding")
	class ValueIteratorHelper<V> extends IteratorHelper<V> {
		public ValueIteratorHelper() {
			super();
			}
		
		@SuppressWarnings("unchecked")
		public V next() {
			return (V) node[index++].value;
		}
	}
	
	/**
	 * This method will store the key/value into the
	 * dictionary with linked list.
	 * @param key the key value
	 * @param value the corresponding value of the key
	 * @return always true since our dictionary will never get full
	 */
	public boolean add(K key, V value) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HashElement<K, V> he = new HashElement(key, value);
		
		if (loadFactor() > maxLoadFactor) {
			int newSize = tableSize * 2;
			resize(newSize);
		}
		
		int hashcode = key.hashCode();
		hashcode = hashcode & 0x7FFFFFFF;
		hashcode = hashcode % tableSize;		//mod it to the tablesize
		
		array[hashcode].add(he);
		
		numElements++;
		return true;
	}//add method
	
	/**
	 * This method will remove the desired key/value pair in the
	 * dictionary.
	 * @param key the key that is to be remove
	 * @return return true if remove is successful else return false or if
	 * 			list is empty
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(K key) {
		
		int hashcode = key.hashCode();
		hashcode = hashcode & 0x7FFFFFFF;
		hashcode = hashcode % tableSize;
		
		HashElement<K,V> tempNode = new HashElement<K,V>(key, null);
		
		
		if (array[hashcode].remove(tempNode)==true) {
			numElements--;
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method will get the value of the corresponding given key
	 * @param key the key that is to be remove
	 * @return the value that correspond to the given key.
	 */
	@SuppressWarnings("unchecked")
	public V getValue(K key) {
		
		//if (isEmpty()==true)
			//return null;
		
		int hashcode = key.hashCode();
		hashcode = hashcode & 0x7FFFFFFF;
		hashcode = hashcode % tableSize;

		HashElement<K,V> node = new HashElement<K, V>(key, null);
		
		HashElement<K,V> temp = array[hashcode].get(node);
		
		if (temp==null)
			return null;
		return temp.value;
	}
	
	public int size() {
		return numElements;
	}
	
	/**
	 * determine if the dictionary is empty
	 * @return true is empty, else false
	 */
	public boolean isEmpty() {
		if (numElements==0)
			return true;
		return false;
	}
	
	/**
	 * Calculate the load factor
	 * @return the current load factor of the dictionary
	 */
	@Override
	public double loadFactor() {
		return (double) numElements / tableSize;
	}
	
	/**
	 * This method will create a new array that is double the old array
	 * @param newSize the size of the new array
	 */
	@SuppressWarnings("unchecked")
	public void resize(int newSize) {
		
		LinkedList<HashElement<K, V>> [] newArray;
		newArray = (LinkedList<HashElement<K ,V>> []) new LinkedList[newSize];
		
		//create linked list for each empty array index
		for (int i = 0; i < newSize; i++)
			newArray[i] = new LinkedList<HashElement<K, V>>();
		
		int tempSize = tableSize;
		tableSize = newSize;
		
		HashElement<K, V> temp;
		
		for (int i = 0; i < tempSize; i++) {
			for(int j = 0; j < array[i].size(); j++) {
				temp = (HashElement<K, V>) array[i].removeFirst();
				int newHash = temp.key.hashCode();
				newHash = newHash & 0x7FFFFFFF;
				newHash = newHash % tableSize;
				newArray[newHash].add(temp);
			}
		}
		
		//assign the new array to the name of the original name "array"
		array = newArray;		
	}

	/**
	 * return an iterator in the dictionary
	 */
	@Override
	public Iterator<K> iterator() {
		return new KeyIteratorHelper();
	}

	/**
	 * returns an key iterator of keys in the dictionary
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Iterator<K> keys() {
		return new KeyIteratorHelper();
	}

	/**
	 * returns an value iterator of values in the dictionary
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}

}
