package com.code.demowebflux;

/**
 * @author ccy
 * @description
 * @time 2022/4/29 4:27 PM
 */
public class PAction {
    public String action(){
        return "PAction";
    }

    /**
     * @author ccy
     * @description
     * @time 2022/4/29 4:28 PM
     */
    public static class Sub1Action extends PAction{

    }

    /**
     * @author ccy
     * @description
     * @time 2022/4/29 4:29 PM
     */
    public static class Sub2Action extends PAction{

    }


    public static void main(String[] args) {
        PAction.Sub1Action sub1Action = new PAction.Sub1Action();
        String action1 = sub1Action.action();
        System.out.println(action1);
        PAction.Sub2Action sub2Action = new PAction.Sub2Action();
        String action2 = sub2Action.action();
        System.out.println(action2);
    }
}
