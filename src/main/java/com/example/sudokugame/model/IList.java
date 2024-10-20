package com.example.sudokugame.model;

/**
 * Interface for a generic list data structure, providing methods to manipulate and retrieve elements.
 *
 * @param <T> the type of elements in this list
 */
public interface IList<T> extends Iterable<T> {

    /**
     * Adds an element to the beginning of the list.
     *
     * @param elem the element to be added
     */
    void addFirst(final T elem);

    /**
     * Adds an element to the end of the list.
     *
     * @param elem the element to be added
     */
    void addLast(final T elem);

    /**
     * Retrieves the first element of the list.
     *
     * @return the first element of the list, or {@code null} if the list is empty
     */
    T getFirst();

    /**
     * Retrieves the last element of the list.
     *
     * @return the last element of the list, or {@code null} if the list is empty
     */
    T getLast();

    /**
     * Removes the first element of the list.
     */
    void removeFirst();

    /**
     * Removes the last element of the list.
     */
    void removeLast();

    /**
     * Checks whether the list contains the specified element.
     *
     * @param elem the element to check for presence in the list
     * @return {@code true} if the list contains the specified element, {@code false} otherwise
     */
    Boolean contains(final T elem);

    /**
     * Removes the first occurrence of the specified element from the list, if it is present.
     *
     * @param elem the element to be removed
     */
    void removeElement(final T elem);

    /**
     * Removes all elements from the list.
     */
    void clear();

    /**
     * Retrieves the element at the specified position in the list.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified position
     */
    T get(Integer index);

    /**
     * Replaces the element at the specified position in the list with the specified element.
     *
     * @param index the index of the element to replace
     * @param elem the element to be stored at the specified position
     */
    void set(Integer index, final T elem);

    /**
     * Inserts the specified element at the specified position in the list.
     *
     * @param index the index at which the element should be inserted
     * @param elem the element to be inserted
     */
    void add(Integer index, final T elem);

    /**
     * Removes the element at the specified position in the list.
     *
     * @param index the index of the element to remove
     */
    void removeElementByIndex(Integer index);

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or {@code -1} if this list does not contain the element.
     *
     * @param elem the element to search for
     * @return the index of the first occurrence of the specified element, or {@code -1} if not found
     */
    Integer indexOf(final T elem);

    /**
     * Returns the index of the last occurrence of the specified element in the list,
     * or {@code -1} if this list does not contain the element.
     *
     * @param elem the element to search for
     * @return the index of the last occurrence of the specified element, or {@code -1} if not found
     */
    Integer lastIndexOf(final T elem);

    /**
     * Checks if the list is empty.
     *
     * @return {@code true} if the list contains no elements, {@code false} otherwise
     */
    Boolean isEmpty();

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    Integer size();

    /**
     * Creates a deep copy of this list.
     *
     * @return a new list that is a deep copy of this list
     */
    IList<T> deepCopy();
}
