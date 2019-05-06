package com.kerker.practice_websocket;

public class Cstruct {

    private char roomId;
    private long command;
    private long length;

    public Cstruct() {
    }

    public Cstruct(char roomId, long command, long length) {
        this.roomId = roomId;
        this.command = command;
        this.length = length;
    }

    public char getRoomId() {
        return roomId;
    }

    public void setRoomId(char roomId) {
        this.roomId = roomId;
    }

    public long getCommand() {
        return command;
    }

    public void setCommand(long command) {
        this.command = command;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
