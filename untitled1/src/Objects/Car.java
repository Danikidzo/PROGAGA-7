package Objects;


import java.util.Objects;

public final class Car {
    private boolean cool;

    public Car(boolean cool) {this.cool = cool;}
    public boolean isCool() {return cool;}

    @Override
    public String toString() {
        return "Car" +
                "\ncool=" + cool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return cool == car.cool;
    }

    @Override
    public int hashCode() {return Objects.hashCode(cool);}
}