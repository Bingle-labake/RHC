package com.example.wanghy.restauranthygienechecker.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghy on 18/3/3.
 */

public class SearchList implements Serializable{
    public List<Business> getSearchlist() {
        return searchlist;
    }

    public void setSearchlist(List<Business> searchlist) {
        this.searchlist = searchlist;
    }

    List<Business> searchlist = null;

    public SearchList(List<Business> searchlist) {
        this.searchlist = searchlist;
    }
}
