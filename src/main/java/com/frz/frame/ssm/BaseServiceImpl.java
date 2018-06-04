package com.frz.frame.ssm;

import java.io.Serializable;
import java.util.List;

/**
 * @author GongTengPangYi
 */
public abstract class BaseServiceImpl<T extends Serializable, E extends Serializable> implements BaseService<T, E> {

    public abstract BaseMapper<T, E> getMapper();

    @Override
    public int deleteById(E id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int save(T record) {
        return getMapper().insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return getMapper().insertSelective(record);
    }

    @Override
    public T findById(E id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateById(T record) {
        return getMapper().updateByPrimaryKey(record);
    }

}
