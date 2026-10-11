package WorkoutManager;

public class ExerciseReference {
	private String name, muscleGroup;
	private double coefMET;
	
	public ExerciseReference(String name, String muscleGroup, double coefMET) {
		this.name=name;
		this.muscleGroup=muscleGroup;
		this.coefMET=coefMET;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMuscleGroup() {
		return muscleGroup;
	}

	public void setMuscleGroup(String muscleGroup) {
		this.muscleGroup = muscleGroup;
	}

	public double getCoefMET() {
		return coefMET;
	}

	public void setCoefMET(double coefMET) {
		this.coefMET = coefMET;
	}
	
}
