package com.example.sudokugame.model;

public class ListAdapter<T> {
    private IList<T> adaptedList;

    public ListAdapter(IList<T> adaptedList) {
        this.adaptedList = adaptedList;
    }

    public IList<T> getAdaptedList() {
        return adaptedList;
    }
}
