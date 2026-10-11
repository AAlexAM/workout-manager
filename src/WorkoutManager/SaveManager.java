package WorkoutManager;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveManager {
	
	public void saveProgram(Program program, String filePath) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try(FileWriter writer = new FileWriter(filePath)){
			gson.toJson(program, writer);
			System.out.println("Program saved to " + filePath);
		}catch (IOException e) {
            System.out.println("Error saving program: " + e.getMessage());
        }
	}
	
	public Program loadProgram(String filePath) {
		Gson gson = new Gson();
		
		try(FileReader reader = new FileReader(filePath)){
			return gson.fromJson(reader, Program.class);
		}catch (IOException e){
	        System.out.println("Error loading program: " + e.getMessage());
	        return null;
	    }
	}

}
