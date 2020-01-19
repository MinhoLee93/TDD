package com.pratice.isbnvalidation;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloResource {

    @GetMapping
    public String helloWorld(){
        return "Hello World!";
    }


    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Hello json(){
        return new Hello("Greetings", "Hello World");
    }

    @PostMapping(value ="/post", consumes = MediaType.APPLICATION_JSON_VALUE
                , produces = MediaType.APPLICATION_JSON_VALUE)
    public Hello post(@RequestBody Hello hello){
        return hello;
    }

    public static class Hello {
        private String title;
        private String value;

        public Hello(){

        }

        public Hello(String title, String value) {
            this.title = title;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTitle(){
            return title;
        }

        public void setTitle(String title){
            this.title = title;
        }
    }
}
