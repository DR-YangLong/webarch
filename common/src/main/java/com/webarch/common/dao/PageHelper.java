/*
 * Copyright  DR.YangLong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webarch.common.dao;

/**
 * page helper
 */
public class PageHelper {
    private static final int DEFAULT_PAGE_SIZE=10;
    private int totalRows;
    private int pageCount;
    private int currentPage;
    private int startRow;
    private int pageSize=DEFAULT_PAGE_SIZE;

    public PageHelper() {
    }

    /**
     * @param totalRows total rows in db
     * @param pageSize the size of one page
     */
    public PageHelper(int totalRows, int pageSize){
        this.currentPage=1;
        this.startRow=0;
        this.pageSize=pageSize;
        this.totalRows=totalRows;
        this.pageCount=totalRows/pageSize;
        if(totalRows%pageSize!=0)this.pageCount++;
    }

    public PageHelper(int totalRows){
        this.currentPage=1;
        this.startRow=0;
        this.totalRows=totalRows;
        this.pageCount=totalRows/pageSize;
        if(totalRows%pageSize!=0)this.pageCount++;
    }

    /**
     * get next page with gavin page No.
     * @param currentPage
     * @return
     */
    public int next(int currentPage){
        this.setCurrentPage(currentPage);
        if(isHasNext(currentPage))this.currentPage++;
        return this.currentPage;
    }


    /**
     * get previous page with gavin page No.
     * @param currentPage
     * @return
     */
    public int previous(int currentPage){
        this.setCurrentPage(currentPage);
        if(isHasPrevious(currentPage))this.currentPage--;
        return this.currentPage;
    }


    /**
     * calculate the start row with gavin page No.
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        currentPage=currentPage>=pageCount?pageCount:currentPage;
        this.currentPage = currentPage;
        if(currentPage>1){
        this.startRow=(currentPage-1)*pageSize;
        return ;
        }
        this.startRow=0;
    }

    /**
     * re calculate the page
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        if(this.totalRows!=0){
            this.pageCount=totalRows/pageSize;
            if(totalRows%pageSize!=0)this.pageCount++;
            int currentPage=this.currentPage>=pageCount?pageCount:this.currentPage;
            if(currentPage>1){
                this.startRow=(currentPage-1)*pageSize;
            }else {
                this.startRow=0;
            }
        }
        this.pageSize = pageSize;
    }

    /**
     * if the gavin page has next return true,otherwise return false
     * @param page
     * @return
     */
    public boolean isHasNext(final int page) {
        return page<this.pageSize;
    }


    /**
     * if the gavin page has previous return true,otherwise return false
     * @param page
     * @return
     */
    public boolean isHasPrevious(final int page) {
        return page>1;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getPageSize() {
        return pageSize;
    }
}
