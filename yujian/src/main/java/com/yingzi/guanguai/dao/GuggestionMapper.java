package com.yingzi.guanguai.dao;

import com.yingzi.guanguai.model.Guggestion;
import java.util.List;

public interface GuggestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Guggestion record);

    Guggestion selectByPrimaryKey(Long id);

    List<Guggestion> selectAll();

    int updateByPrimaryKey(Guggestion record);
}