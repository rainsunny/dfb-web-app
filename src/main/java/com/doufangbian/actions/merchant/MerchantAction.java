package com.doufangbian.actions.merchant;

import com.doufangbian.actions.BaseAction;

/**
 * User: Jie
 * Date: 12/14/13
 * Time: 10:54 AM
 */

public class MerchantAction extends BaseAction {

    public String execute() {
        return "main";
    }

    public String info() {
        return "info";
    }

    public String comment() {
        return "comment";
    }

    public String album() {
        return "album";
    }

    public String message() {
        return "message";
    }

    public String talk() {
        return "talk";
    }

    public String config() {
        return "config";
    }
}
