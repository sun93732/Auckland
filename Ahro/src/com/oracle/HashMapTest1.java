package com.oracle;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HashMapTest1  
{  
    /** 
     * 找出一个数组中一个数字出现次数最多的数字 
     * 用HashMap的key来存放数组中存在的数字，value存放该数字在数组中出现的次数 
     * @author xiaoluo  
     */  
   public static void main(String[] args)  
    {  
        int[] array = {2, 1, 2, 3, 4, 5, 2, 2, 2, 2};  
          
        //map的key存放数组中存在的数字，value存放该数字在数组中出现的次数  
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();  
          
        for(int i = 0; i < array.length; i++)  
       {  
            if(map.containsKey(array[i]))  
            {  
               int temp = map.get(array[i]);  
                
               map.put(array[i], temp + 1);  
           }  
           else  
           {  
              map.put(array[i], 1);  
            }  
        }  
          
       Collection<Integer> count = map.values();  
        
       //找出map的value中最大的数字，也就是数组中数字出现最多的次数  
       int maxCount = Collections.max(count);  
         
       int maxNumber = 0;  
         
       for(Map.Entry<Integer, Integer> entry : map.entrySet())  
        {  
            //得到value为maxCount的key，也就是数组中出现次数最多的数字  
          if(maxCount == entry.getValue())  
           {  
               maxNumber = entry.getKey();  
            }  
        }  
          
       System.out.println("出现次数最多的数字为：" + maxNumber);  
        System.out.println("该数字一共出现" + maxCount + "次");  
    }  
}  
