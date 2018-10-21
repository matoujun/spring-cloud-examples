package org.matoujun.cloud.api.dao;

/**
 * @author matoujun

 */
public interface NativeDao {
    <T> T loadById(Class<T> clz, long id);

}
