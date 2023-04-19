package edu.hitsz.prop.factory;


import edu.hitsz.prop.BaseProp;

import java.util.Random;

/**
 * @author leng
 */
public interface PropFactory {
    /**
     * 创建道具对象
     * @param locationX
     * @param locationY
     * @return BaseProp
     */
    public abstract BaseProp createProp(int locationX, int locationY);

}
