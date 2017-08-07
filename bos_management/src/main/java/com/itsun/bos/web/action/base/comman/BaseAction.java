package com.itsun.bos.web.action.base.comman;

import com.itsun.bos.utils.TypeUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Pageable pageable;

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

    protected Specification<T> getSpecification() {
        return getSpecification(model);
    }

    protected Specification<T> getSpecification(Object o) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate predicate = getPredicate(o, root, query, cb);
                return predicate;
            }
        };
        return specification;
    }

    Predicate getPredicate(Object o, From root, CriteriaQuery query, CriteriaBuilder cb) {
        if (null != o) {
            List<Predicate> list = new ArrayList<>();
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(o.getClass(), Object.class);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor prop : propertyDescriptors) {
                    String name = prop.getName();
                    Class propertyType = prop.getPropertyType();
                    Method method = prop.getReadMethod();
                    Object value = method.invoke(o);
                    if (null != value) {
                        if (TypeUtils.isCollections(value)) {
                            continue;
                        }
                        if (!TypeUtils.isString(value) || (TypeUtils.isString(value) && StringUtils.isNotBlank((CharSequence) value))) {
                            if (TypeUtils.isEntity(value)) {
                                //该属性是个实体对象
                                Join<Object, Object> joinRoot = root.join(name, JoinType.INNER);
                                Predicate predicate = getPredicate(value, joinRoot, query, cb);
                                if (predicate != null) {
                                    list.add(predicate);
                                }
                            } else {
                                //该属性是基本数据类型或者字符或者字符串
                                Predicate predicate;
                                //字符串使用like方法
                                if (TypeUtils.isString(value)) {
                                    predicate = cb.like(root.get(name).as(propertyType), value + "%");
                                } else {
                                    //数字字符布尔使用equal方法
                                    predicate = cb.equal(root.get(name).as(propertyType), value);
                                }
                                list.add(predicate);
                            }
                        }
                    }
                }
                if (list.size() == 0) {
                    return null;
                }
                return cb.and(list.toArray(new Predicate[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


}



