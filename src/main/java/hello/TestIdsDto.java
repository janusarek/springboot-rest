package hello;

import java.math.BigInteger;

class TestIdsDto {
  private final int id1;
  private final BigInteger id2;
  private final String name;

  public TestIdsDto(final int id1, final BigInteger id2, final String name) {
    this.id1 = id1;
    this.id2 = id2;
    this.name = name;
  }

  public int getId1() {
    return id1;
  }

  public BigInteger getId2() {
    return id2;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof TestIdsDto)) {
      return false;
    }

    TestIdsDto otherDto = (TestIdsDto) other;

    return (id1 == otherDto.getId1()) && (id2 == otherDto.getId2()) && (name.equals(otherDto.getName()));
  }

  public static class TestIdsDtoBuilder {
    private final int id1;
    private BigInteger id2;
    private String name;

    public TestIdsDtoBuilder(final int id1) {
      this.id1 = id1;
    }

    public TestIdsDtoBuilder setId2(final BigInteger id2) {
      this.id2 = id2;
      return this;
    }

    public TestIdsDtoBuilder setName(final String name) {
      this.name = name;
      return this;
    }

    public TestIdsDto build() {
      return new TestIdsDto(id1, id2, name);
    }
  }

}
