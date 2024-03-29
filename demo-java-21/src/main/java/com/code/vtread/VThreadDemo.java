package com.code.vtread;


public class VThreadDemo {


    public static void main(String[] args) throws InterruptedException {
        Thread.ofVirtual().name("v-test").start(new SimpleThread());
        Thread.sleep(1000);
    }

    public static class SimpleThread implements Runnable{

        @Override
        public void run() {
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}