package com.hysea.hyseaappapi.util;


public class MatcherIndex {
    private int start;
    private int end;

    public MatcherIndex(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "MatcherIndex{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }


}