package WorkoutManager;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Session {
	private LocalDate date;
	private int durationMinutes;
	private List<ExerciseDone> exercisesDone;
	
	public Session(LocalDate date, int durationMinutes) {
		this.date=date;
		this.durationMinutes = durationMinutes;
		this.exercisesDone = new ArrayList<ExerciseDone>();
	}
	
	public void addExercise(ExerciseDone exercise) {
		exercisesDone.add(exercise);
	}
	
	public int getDurationMinutes() {
        return durationMinutes;
    }

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<ExerciseDone> getExercisesDone() {
		return exercisesDone;
	}

	public void setExercisesDone(List<ExerciseDone> exercisesDone) {
		this.exercisesDone = exercisesDone;
	}
	
}
