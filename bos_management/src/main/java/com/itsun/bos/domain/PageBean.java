package com.itsun.bos.domain;

import com.itsun.bos.domain.take_delivery.Promotion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by SY on 2017-07-30.
 * on BOSV20
 * on 20:01
 */
@XmlRootElement(name = "pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {
    private Long totalCount;
    private List<T> pageDate;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getPageDate() {
        return pageDate;
    }

    public void setPageDate(List<T> pageDate) {
        this.pageDate = pageDate;
    }
}
