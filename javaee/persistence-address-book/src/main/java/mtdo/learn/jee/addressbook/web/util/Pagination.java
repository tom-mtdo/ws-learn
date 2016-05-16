/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mtdo.learn.jee.addressbook.web.util;

import javax.faces.model.DataModel;

/**
 *
 * @author thangdo
 */
public abstract class Pagination {
    private int pageSize;
    private int pageNo = 0;

    public Pagination(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public abstract int getItemsCount();
    public abstract DataModel createPageDataModel();
    
    public int getPageFirstItem(){
        return pageSize * pageNo;
    }
    
    public int getPageLastItem(){
        int index = getPageFirstItem() + pageSize -1;
        int count = getItemsCount() - 1;
        if (index > count) {
            index = count;
        }
        if (index < 0 ){
            index = 0;
        }
        
        return index;
    }
    
    public boolean isHasNextPage(){
//        return getPageLastItem() + 1 < (getItemsCount() - 1);
        return (pageNo+1) * pageSize < getItemsCount();
    }
    public boolean isHasPerviousPage(){
        return pageNo > 0;
    }
    
    public void nextPage(){
        if (isHasNextPage()){
            pageNo++;
        }
    }
    
    public void previousPage(){
        if (isHasPerviousPage()){
            pageNo--;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    
}
