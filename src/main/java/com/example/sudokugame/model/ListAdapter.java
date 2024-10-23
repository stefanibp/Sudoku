package com.example.sudokugame.model;

/**
 * The ListAdapter class serves as an adapter to encapsulate an instance of an {@link IList} implementation.
 * It provides a way to access and interact with the underlying list through the adapted object.
 *
 * @param <T> The type of elements in the list.
 */
public class ListAdapter<T> {
    /**
     * The adapted list, an instance of {@link IList}.
     */
    private IList<T> adaptedList;

    /**
     * Constructs a ListAdapter with the specified list.
     *
     * @param adaptedList The list to be adapted, an instance of {@link IList}.
     */
    public ListAdapter(IList<T> adaptedList) {
        this.adaptedList = adaptedList;
    }

    /**
     * Returns the adapted list.
     *
     * @return The adapted list, an instance of {@link IList}.
     */
    public IList<T> getAdaptedList() {
        return adaptedList;
    }
}
