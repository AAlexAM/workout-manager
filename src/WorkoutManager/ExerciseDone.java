package WorkoutManager;

import java.util.List;
import java.util.ArrayList;

public class ExerciseDone {
	private ExerciseReference reference;
	private List<Round> rounds;
	
	public ExerciseDone(ExerciseReference reference) {
		this.reference = reference;
		this.rounds = new ArrayList<Round>();
	}
	
	public void addRound(Round round) {
		rounds.add(round);
	}

	public ExerciseReference getReference() {
		return reference;
	}

	public void setReference(ExerciseReference reference) {
		this.reference = reference;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}
	
}
