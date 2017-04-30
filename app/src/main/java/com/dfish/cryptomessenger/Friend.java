package com.dfish.cryptomessenger;

/**
 * Created by duncanfisher on 4/30/17.
 */

public class Friend {

    public Integer thread_id;
    public String address;
    public String snippet;

    public Friend(int id, String address, String snippet) {
        this.thread_id = id;
        this.address = address;
        this.snippet = snippet;
    }
}
