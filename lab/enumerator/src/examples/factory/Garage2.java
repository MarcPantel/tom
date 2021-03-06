package examples.factory;

import java.util.ArrayList;
import java.util.List;

import tom.library.factory.Enumerate;
import tom.library.factory.EnumerateGenerator;

public class Garage2 {
	// a car can appear twice!
	// use Set if otherwise and change the Factory accordingly
	private List<List<Car>> cars;
	private List<Car> cars2;

	public Garage2() {
		cars = new ArrayList<List<Car>>();
	}

	@Enumerate(canBeNull = false)
	public Garage2(@Enumerate(canBeNull = false) List<List<Car>> cars,@Enumerate(canBeNull = false) List<Car> cars2) {
		this.cars = cars;
		this.cars2=cars2;
	}

	@Override
	public String toString() {
		return "Garage [cars=" + cars + ", cars2=" + cars2 + "]";
	}


}
