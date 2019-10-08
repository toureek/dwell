package com.dwell.it.dao;

import com.dwell.it.entities.Provider;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProviderDao {

    //  Basic CRUD Operations
    int insertNewProvider(Provider provider);
    Provider fetchSingleProviderById(int id);
    int updateProvider(Provider provider);
    int deleteProviderById(int id);


    // Foreign-Key handler
    Provider searchTargetProvider(String name);

}
