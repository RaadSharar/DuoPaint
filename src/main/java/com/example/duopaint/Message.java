package com.example.duopaint;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    public enum Type {
        JOIN,
        PLAYER_LIST,
        CHAT,
        DRAW_CMD,
        NAME_PROMT,
        START,
        CLEAR_CMD,
        ROUND_STARTS,
        ROUND_ENDS, RESULT, TIME_REM, WORD_CHOSEN,

    }

    public Type type;
    public String sender;
    public Object payload;

    public Message(Type type, String sender, Object payload) {
        this.type    = type;
        this.sender  = sender;
        this.payload = payload;
    }
}