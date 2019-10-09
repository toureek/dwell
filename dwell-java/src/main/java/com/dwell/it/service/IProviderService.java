package com.dwell.it.service;

import com.dwell.it.entities.Provider;

public interface IProviderService {

    // Basic
    boolean addNewProviderOne(Provider provider);

    Provider queryForSingleProvidertById(int id);

    boolean modifyProvider(Provider provider);

    boolean removeProviderById(int id);


    // ForeignKey Handler
    Integer isAllowedInsertNewOne(String name);
}
