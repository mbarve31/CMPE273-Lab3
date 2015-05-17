package edu.sjsu.cmpe.cache.client;

/**
 * Created by Madhura on 5/08/15.
 */

import edu.sjsu.cmpe.cache.client.consistentcache.ConsistentHash;

import java.util.ArrayList;

public class ConsistentCacheClient {

    public static void main(String[] args) throws Exception {
        System.out.println("\n****************Consistent Cache Client********************\n");


        String cache1 = "http://localhost:3000";
        String cache2 = "http://localhost:3001";
        String cache3 = "http://localhost:3002";


        ArrayList collection = new ArrayList();
        collection.add(cache1);
        collection.add(cache2);
        collection.add(cache3);



        System.out.println("\n****************Adding to Distributed Caches***************\n");



        ConsistentHash consistentHash = new ConsistentHash(collection);

        for (int i = 1; i <= 10; i++) {

            addToCache(i, String.valueOf((char) (i + 96)), consistentHash);
        }
        System.out.println("\n*************Retrieve from Distributed Caches*****************\n");

        for (int i = 1; i <= 10; i++) {
            String value = (String) getFromCache(i, consistentHash);

        }

        System.out.println("\n*********************Exit Cache Client************************\n");
    }

    public static void addToCache(int toAddKey, String toAddValue, ConsistentHash consistentHash) {

        String cacheUrl = (String) consistentHash.getCache(toAddKey);
        CacheServiceInterface cache = new DistributedCacheService(cacheUrl);
        cache.put(toAddKey, toAddValue);
        System.out.println("put(" + toAddKey + " => " + toAddValue + ")");

    }

    public static Object getFromCache(int key, ConsistentHash consistentHash) {
        String cacheUrl = (String) consistentHash.getCache(key);
        CacheServiceInterface cache = new DistributedCacheService(cacheUrl);
        String value = cache.get(key);
        System.out.println("get(" + key + ") => " + value);

        return value;
    }

}
