package com.ocdxsunnah.oxs.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImsakModels {
    @SerializedName("results")
    @Expose
    private Datetime results;

    public Datetime getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MainModel{" +
                "results=" + results +
                '}';
    }

    public class Datetime {
        @SerializedName("datetime")
        @Expose
        private ArrayList<Times> datetime;

        public ArrayList<Times> getDatetime() {
            return datetime;
        }

        @Override
        public String toString() {
            return "Datetime{" +
                    "datetime=" + datetime +
                    '}';
        }
    }

    public class Times{
        @SerializedName("times")
        @Expose
        private Imsak times;

        public Imsak getTimes() {
            return times;
        }

        @Override
        public String toString() {
            return "Times{" +
                    "times=" + times +
                    '}';
        }
    }

    public class Imsak{
        private String Imsak;

        public String getImsak() {
            return Imsak;
        }

        @Override
        public String toString() {
            return "Imsak{" +
                    "Imsak='" + Imsak + '\'' +
                    '}';
        }
    }
}

