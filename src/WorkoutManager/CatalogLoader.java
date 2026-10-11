package WorkoutManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatalogLoader {
	
	public List<ExerciseReference> loadFromCSV(String filePath){
		List<ExerciseReference> exercises = new ArrayList<ExerciseReference>();
		
		try (BufferedReader bfr = new BufferedReader(new FileReader(filePath))){
			String line = bfr.readLine(); // Read the header.
			int lineID = 1;
			
			while(line != null) { //readLine() return null at the end of the file.
				if(lineID > 1) {
					ExerciseReference exercise = createExerciseFromLine(line);
					exercises.add(exercise);
				}
				line = bfr.readLine(); // Read the next line, for the next iteration.
				lineID++;
			}
			
		}catch (IOException e) {
			System.out.println("Error " + e.getMessage());
		}
		return exercises;
	}
	
	private ExerciseReference createExerciseFromLine(String line) {
		String [] values = line.split(",");
		String name = values[0];
		String group = values[1];
		double MET = Double.parseDouble(values[2]);
		return new ExerciseReference(name, group, MET);
	}
	
}
