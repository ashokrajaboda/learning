package com.gagana.hospital.api;

public class Test {
    int i=0;
    public static void main(String[] args) {
        Test test = new Test();
        for(int i= 1; i<=40; i++) {
            test.handle();
        }

    }

    public Object handle() {
        Object object = null;
        //System.out.println(i+" : "+(i%10)+" : "+(i/10));

        if(i%10 <= 10 && ((i/10)%2 == 0)) {
            try {
                object = someOtherFn();
                i = 1;
            } catch(Exception e) {
                System.out.println(e.getMessage());
                i++;
            }
        } else {
            i++;
        }
        return object;
    }

    private Object someOtherFn() {
        throw new RuntimeException("Test Exception:"+i);
    }


}
