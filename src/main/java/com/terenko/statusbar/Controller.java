package com.terenko.statusbar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    Loader loader;
    @PostMapping("load")
    public ResponseEntity addLoad(@RequestParam String URLs){
        try {

            loader.addLoad(URLs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("monitor")
    public List<FileDTO> monitor(){
        List<FileDTO> d=loader.Monitor();
        return loader.Monitor();
    }
    @PostMapping("delete")
    public void delete(@RequestParam(name = "toDelete[]", required = false) String[] uuidList){
        loader.delete(Arrays.asList(uuidList));
    }
    @PostMapping("stop")
    public void stop(@RequestParam(name = "toDelete[]", required = false) String[] uuidList){
        loader.stop(Arrays.asList(uuidList));
    }
    @PostMapping("resume")
    public void resume(@RequestParam(name = "toDelete[]", required = false) String[] uuidList){
        loader.resume(Arrays.asList(uuidList));
    }
}
