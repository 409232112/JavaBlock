package wyc.block.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import wyc.block.constant.BlockConstant;
import wyc.block.entity.Block;

import java.util.Set;

public final class RedisUtil {
    private static JedisPool jedisPool = null;

    // Redis服务器IP
    private static String ADDR;

    // Redis的端口号
    private static int PORT;

    // 访问密码
    private static String AUTH;

    /**
     * 初始化Redis连接池
     */
    static {
        PropertiesUtil propertiesUtil=new PropertiesUtil("config.properties");
        ADDR=propertiesUtil.getPropertyValue("redis.host");
        PORT=new Integer(propertiesUtil.getPropertyValue("redis.port"));
        AUTH=propertiesUtil.getPropertyValue("redis.pass");
        try {
            JedisPoolConfig config = new JedisPoolConfig();

            // 连接耗尽时是否阻塞, false报异常, true 阻塞直到超时, 默认true
            config.setBlockWhenExhausted(true);

            // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

            // 是否启用pool的jmx管理功能, 默认true
            config.setJmxEnabled(true);

            // 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的实例。
            config.setMaxIdle(8);

            // 最大连接数, 默认8个
            config.setMaxTotal(200);

            // 表示当borrow(引入)一个redis实例时，最大的等待时间，如果超过等待时间，抛出异常
            config.setMaxWaitMillis(1000 * 100);

            // 在borrow一个redis实例时，是否提前进行validate操作；如果为true，则得到的redis实例均是可用的
            config.setTestOnBorrow(true);
            jedisPool = new JedisPool(config, ADDR, PORT, 3000, AUTH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void close(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 保存数据
     * @param key 键
     * @param value 值
     * @param index
     */
    public static void set(int index,String key,String value){
        if (key==null||value==null){
            return ;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return ;
        }
        jedis.select(index);
        jedis.set(key,value);

        //释放资源
        close(jedis);
    }

    public static void set(int index,String key,byte[] value){
        if (key==null||value==null){
            return ;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return ;
        }
        jedis.select(index);
        jedis.set(key.getBytes(),value);

        //释放资源
        close(jedis);
    }

    public static void set(int index,byte[] key,byte[] value){
        if (key==null||value==null){
            return ;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return ;
        }
        jedis.select(index);
        jedis.set(key,value);

        //释放资源
        close(jedis);
    }

    /**
     * 保存数据(设置有效时间)
     * @param key 键
     * @param value 值
     * @param index
     */
    public static void set(int index,String key,String value,int seconds){
        if (key==null||value==null){
            return ;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return ;
        }
        jedis.select(index);
        jedis.setex(key,seconds,value);

        //释放资源
        close(jedis);
    }


    /**
     * 获取数据
     * @param key 键
     * @return  返回key对应的数据
     */
    public static byte[] get(int index, String key){
        if (key==null){
            return null;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return null;
        }
        jedis.select(index);
        byte[] bytes=jedis.get(key.getBytes());

        //释放资源
        close(jedis);

        return bytes;

    }
    public static byte[] get(int index, byte[] key){
        if (key==null){
            return null;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return null;
        }
        jedis.select(index);
        byte[] bytes=jedis.get(key);

        //释放资源
        close(jedis);

        return bytes;

    }

    /**
     * 获取key
     * @return  返回key list
     */
    public static Set<String> getKeys(int index){

        Jedis jedis=getJedis();
        if (jedis==null){
            return null;
        }
        jedis.select(index);
        Set<String> keyList= jedis.keys("*");

        //释放资源
        close(jedis);

        return keyList;

    }

    /**
     * 获取数据
     * @param key 键
     * @return  返回key对应的数据
     */
    public static String getStr(int index,String key){
        if (key==null){
            return null;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return null;
        }
        jedis.select(index);
        String value=jedis.get(key);
        //释放资源
        close(jedis);

        return value;
    }


    /**
     * 如果键不存在的时候设置键值
     * @param key 键
     * @param value 值
     * @param index
     */
    public static void setNX(int index,String key,String value){
        if (key==null||value==null){
            return ;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return ;
        }
        jedis.select(index);
        jedis.setnx(key,value);

        //释放资源
        close(jedis);
    }


    /**
     * 获取并设置数据
     * @param key 键
     * @param value 值
     * @param index
     */
    public static String getSet(int index,String key,String value){
        if (key==null){
            return null;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return null;
        }
        jedis.select(index);
        String result = jedis.getSet(key,value);

        //释放资源
        close(jedis);

        return result;
    }

    /**
     * 查询该key的过期时间
     * @param index
     * @param key
     * @return
     */
    public static Long ttl(int index,String key){
        if (key == null){
            return null;
        }
        Jedis jedis = getJedis();
        if ( jedis == null){
            return null;
        }
        jedis.select(index);
        Long expire = jedis.ttl(key);

        //释放资源
        close(jedis);

        return expire;
    }

    public static Long expire(int index,String key,int time){
        if (key == null){
            return null;
        }
        Jedis jedis = getJedis();
        if (jedis == null){
            return null;
        }
        jedis.select(index);
        Long expire = jedis.expire(key,time);

        close(jedis);

        return expire;
    }


    /**
     * 清除数据
     * @param key 键
     * @return  删除出错-1  删除成功则返回redis返回值
     */
    public static long deleteStr(int index,String key){
        if (key==null){
            return -1;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return -1;
        }

        jedis.select(index);

        long ret=jedis.del(key);
        //释放资源
        close(jedis);

        return ret;
    }


    /**
     * 条件查询key
     * 比如 查询所有以"session"开头的key的value集合
     * pattern : session*
     */
    public static Set<byte[]> keys(String pattern){
        Set<byte[]> keys = null;
        Jedis jedis=getJedis();
        keys = jedis.keys(pattern.getBytes());
        close(jedis);
        return keys;
    }

    /**
     * set
     * @param index 几号库
     * @param key
     * @param value
     * @param expire 有效时间
     * @return
     */
    public static void set(int index,byte[] key,byte[] value,int expire){
        if (key==null||value==null){
            return ;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return ;
        }
        jedis.select(index);
        if(expire==-1){
            jedis.set(key,value);
        }else{
            jedis.setex(key,expire,value);
        }


        close(jedis);
    }

    /**
     * del
     * @param index 几号库
     * @param key
     */
    public static void del(int index,byte[] key){
        if (key==null){
            return ;
        }
        Jedis jedis=getJedis();
        if (jedis==null){
            return ;
        }
        jedis.select(index);
        jedis.del(key);
        close(jedis);
    }

    public static void main(String args[]){


    }
}