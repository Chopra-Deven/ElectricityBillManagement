package com.billmanagemtsystem.repository;

public interface RepositoryService
{

    Object get(int id);

    Object get();

    Object update(int id, Object obj);

    Object add(int id, Object obj);

}
