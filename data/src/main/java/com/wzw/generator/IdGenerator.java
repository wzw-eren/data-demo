package com.wzw.generator;

/**
 * id 生成器
 *
 * @author erenwu
 */
public interface IdGenerator {

    /**
     * 根据前缀获取id
     *
     * @param prefix
     * @return
     */
    String nextId(String prefix);


    /**
     * 获取id
     *
     * @param prefix
     * @param noPrefixSize  不包含前缀的长度
     * @param stemNum  步进长度
     * @return
     */
    default String nextId(String prefix, Integer noPrefixSize, Long stemNum) {
        return nextId(prefix);
    }

}
