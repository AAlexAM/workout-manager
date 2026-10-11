package WorkoutManager;

public class Round {
	private int reps;
	private double weight;
	
	public Round(int reps, double weight) {
		this.reps = reps;
		this.weight = weight;
	}
	
	public double getTonnage() {
		return reps * weight;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public String toString() {
		return reps + " reps x " + weight + " kg";
	}
	
}
