package com.itsun.web.action.comman;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


/**
 * Created by SY on 2017-07-23.
 * on BOSV20
 * on 上午 08:51
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    protected T model;

    @Override
    public T getModel() {
        return model;
    }


    public BaseAction() {
        //获取的是父类泛型 AreaAction extends
        // BaseAction<Area>
        Type baseClass = this.getClass().getGenericSuperclass();
        //将这个类型强转为一个参数化类型
        ParameterizedType parameterized = (ParameterizedType) baseClass;
        //获取所有的泛型,在BaseAction中通常只用第一个
        Type[] actualTypeArguments = parameterized.getActualTypeArguments();
        //拿到这个泛型的字节码文件
        Class aClass = (Class) actualTypeArguments[0];

        try {
            model = (T) aClass.newInstance();
        } catch (InstantiationException e) {
            System.out.println();
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    protected Integer page;
    protected Integer rows;

    protected Pageable pageable;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }


    protected Pageable getPageable() {
        if ((page != null) && (rows != null)) {
            return new PageRequest(page - 1, rows);
        }
        return null;
    }

    protected void encapsulationObject(Page<T> page) {
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getTotalElements());
        map.put("rows", page.getContent());
        ActionContext.getContext().getValueStack().push(map);
    }


}



