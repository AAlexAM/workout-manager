package WorkoutManager;

import java.util.HashMap;
import java.util.Map;

public class StatsCalculator {

    public double sessionTonnage(Session session) {
        double total = 0.0;
        for (ExerciseDone ex : session.getExercisesDone()) {
            for (Round r : ex.getRounds()) {
                total += r.getTonnage();
            }
        }
        return total;
    }

    public double sessionCalories(Session session, double bodyWeightKg) {
        var exercises = session.getExercisesDone();
        if (exercises.isEmpty()) return 0.0;

        double totalMET = 0.0;
        for (ExerciseDone ex : exercises) {
            totalMET += ex.getReference().getCoefMET();
        }
        double averageMET = totalMET / exercises.size();
        double durationHours = session.getDurationMinutes() / 60.0;

        return averageMET * bodyWeightKg * durationHours;
    }

    public Map<String, Integer> muscleGroupVolume(Program program) {
        Map<String, Integer> volume = new HashMap<String, Integer>();
        for (Session session : program.getSessions()) {
            for (ExerciseDone ex : session.getExercisesDone()) {
                String group = ex.getReference().getMuscleGroup();
                int sets = ex.getRounds().size();
                volume.merge(group, sets, Integer::sum);
            }
        }
        return volume;
    }

    public Map<String, Integer> setsPerExercise(Program program) {
        Map<String, Integer> sets = new HashMap<String, Integer>();
        for (Session session : program.getSessions()) {
            for (ExerciseDone ex : session.getExercisesDone()) {
                String name = ex.getReference().getName();
                sets.merge(name, ex.getRounds().size(), Integer::sum);
            }
        }
        return sets;
    }
}