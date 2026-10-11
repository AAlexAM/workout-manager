package WorkoutManager;

import java.util.List;

public class Catalog {
	private List<ExerciseReference> exercises;
	
	public Catalog(List<ExerciseReference> exercises) {
		this.exercises=exercises;
	}
	
	public ExerciseReference searchByName(String name) {
		for(ExerciseReference ex : exercises) {
			if(ex.getName().equalsIgnoreCase(name)) {
				return ex;
			}
		}
		return null;
	}
	
	public List<ExerciseReference> getExercises() {
        return exercises;
    }
	
}
