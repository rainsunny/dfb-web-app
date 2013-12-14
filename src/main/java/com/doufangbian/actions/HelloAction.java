package com.doufangbian.actions;

import com.opensymphony.xwork2.ActionSupport;

/**
 * User: Jie
 * Date: 12/14/13
 * Time: 2:37 PM
 */
public class HelloAction extends ActionSupport {
    private String message;

    public String method1() {
        message = "in method1";
        return SUCCESS;
    }

    public String execute() {
        message = "in execute";
        return SUCCESS;
    }

    public String getMessage() {
        return message;
    }
}
