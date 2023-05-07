package com.app.competence_project_app.model;

public class Measurement {

      private String datetime;

      private double result;

      public Measurement(String datetime, double result) {
            this.datetime = datetime;
            this.result = result;
      }

      public String getDatetime() {
            return datetime;
      }

      public void setDatetime(String datetime) {
            this.datetime = datetime;
      }

      public double getResult() {
            return result;
      }

      public void setResult(double result) {
            this.result = result;
      }
}
