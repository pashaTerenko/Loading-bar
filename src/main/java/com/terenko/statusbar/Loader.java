package com.terenko.statusbar;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Loader {
  private   ConcurrentHashMap<Load, Integer> threads = new ConcurrentHashMap<>();
   private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public void addLoad(String URLs) {
        try {
            Load r1 = new Load(this, URLs);
            threads.put(r1, 0);
            threadPool.submit(r1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // @Scheduled(fixedRate=2000)
    public List<FileDTO> Monitor() {
        List<FileDTO> ret = new ArrayList<>();
        for (Map.Entry entry : threads.entrySet()) {
            Load key = (Load) entry.getKey();
            int value = (int) entry.getValue();
            int procent = Math.round(((float) key.getComplect() / (float) key.getSize()) * (float) 100);
            editMap(key, procent);
            ret.add(new FileDTO(key.getUrl().toString(), Integer.toString(value),key.getUuid()));
        }
        return ret;
    }
    public  void delete(List<String> list){
        List<Load> toDelete=new ArrayList<>();
        for (Map.Entry entry : threads.entrySet()) {
            Load key = (Load) entry.getKey();
            if (list.contains(key.getUuid())){
                toDelete.add(key);
            }

        }
        for(Load l:toDelete) {
            l.interrupt();
            threads.remove(l);
        }
    }
    public  void stop(List<String> list){
        List<Load> toStop=new ArrayList<>();
        for (Map.Entry entry : threads.entrySet()) {
            Load key = (Load) entry.getKey();
            if (list.contains(key.getUuid())){
                toStop.add(key);
            }

        }
        for(Load l:toStop) {
            l.Stop();

        }
    }
    public  void  resume(List<String> list){
        List<Load> toResume=new ArrayList<>();
        for (Map.Entry entry : threads.entrySet()) {
            Load key = (Load) entry.getKey();
            if (list.contains(key.getUuid())){
                toResume.add(key);
            }

        }
        for(Load l:toResume) {
            l.resume();

        }
    }
    public synchronized void editMap(Load l, int b) {
        threads.put(l, b);
    }
}
