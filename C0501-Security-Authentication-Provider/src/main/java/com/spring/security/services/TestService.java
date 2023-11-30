package com.spring.security.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TestService {

    @Async
    public void testNewThread(){
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null){
            System.out.println(context.getAuthentication().getName());
        } else {
            System.out.println("null object");
        }
    }

    @Async
    public void testDelegateSecurityContext(){
        Callable<String> task = () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };

        ExecutorService e = Executors.newCachedThreadPool();
        try {
            var contextTask = new DelegatingSecurityContextCallable<>(task);
            System.out.println("Ciao, " + e.submit(contextTask).get() + "!");
        } catch (ExecutionException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            e.shutdown();
        }
    }


}
