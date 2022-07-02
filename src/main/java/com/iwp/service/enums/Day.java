package com.iwp.service.enums;

public enum Day {
  SATURDAY(1),
  SUNDAY(2),
  MONDAY(3),
  TUESDAY(4),
  WEDNESDAY(5),
  THURSDAY(6),
  FRIDAY(7);

  private final Integer label;

  Day(Integer label) {
    this.label = label;
  }

  public Integer getLabel() {
    return label;
  }
}
