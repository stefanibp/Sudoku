package com.example.sudokugame.model;

public class ListAdapter<T> {
    private IList<T> adaptedList;

    // Constructor: recibe una instancia de IList<T> para adaptarla
    public ListAdapter(IList<T> adaptedList) {
        this.adaptedList = adaptedList;
    }

    // Método para obtener la lista adaptada (si se necesita interactuar con ella)
    public IList<T> getAdaptedList() {
        return adaptedList;
    }
}
