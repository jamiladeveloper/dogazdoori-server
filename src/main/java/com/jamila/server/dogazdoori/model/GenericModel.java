package com.jamila.server.dogazdoori.model;

public interface GenericModel<E,D> {
    public E toEntity(D d);
    public D toDTO(E e);
}
