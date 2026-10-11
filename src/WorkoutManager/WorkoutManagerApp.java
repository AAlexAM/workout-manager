package WorkoutManager;

import java.time.LocalDate;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class WorkoutManagerApp extends Application {

    public void start(Stage stage) {
    	CatalogLoader loader = new CatalogLoader();
    	List<ExerciseReference> exercises = loader.loadFromCSV("exercises.csv");
    	Catalog theCatalog = new Catalog(exercises);
    	
        stage.setTitle("Workout Manager");
        stage.setScene(startScene(stage, theCatalog));
        stage.show();
    }
    
    private Scene startScene(Stage stage, Catalog theCatalog) {
    	Label title = new Label("Welcome !");
    	
    	Label nameLabel = new Label("Name of the program : ");
    	TextField nameField = new TextField();
    	Label weightLabel = new Label("Your weight (kg) : ");
    	TextField weightField = new TextField();
    	Button createButton = new Button("Create");
    	Label loadLabel = new Label("Or load an existing program by name:");
        TextField loadField = new TextField();
        Button loadButton = new Button("Load");
    	Label errorLabel = new Label("");
    	
    	createButton.setOnAction(event -> {
    		try {
                String programName = nameField.getText().trim();
                
                if(programName.isEmpty()) {
                	errorLabel.setText("Program name can't be empty.");
                	return;
                }
                
                double bodyWeight = Double.parseDouble(weightField.getText());

                Program program = new Program(programName);

                stage.setScene(mainMenuScene(stage, program, null, theCatalog, bodyWeight));
            } catch (NumberFormatException e) {
                errorLabel.setText("Invalid Weight");
            }
    	});
    	
    	loadButton.setOnAction(event -> {
            try {
                String nameToLoad = loadField.getText().trim();
                double bodyWeight = Double.parseDouble(weightField.getText());

                SaveManager saveManager = new SaveManager();
                Program loadedProgram = saveManager.loadProgram(nameToLoad + ".json");

                if (loadedProgram == null) {
                    errorLabel.setText("Program not found.");
                } else {
                    stage.setScene(mainMenuScene(stage, loadedProgram, null, theCatalog, bodyWeight));
                }
            } catch (NumberFormatException e) {
                errorLabel.setText("Invalid Weight");
            }
        });
    	
    	VBox root = new VBox(15);
    	root.setPadding(new Insets(20));
    	root.getChildren().addAll(title, nameLabel, nameField, weightLabel, weightField, createButton, loadLabel, loadField, loadButton, errorLabel);
    	
    	return new Scene(root, 500, 500);
    }
    
    private Scene mainMenuScene(Stage stage, Program program, Session currentSession, Catalog theCatalog, double bodyWeight) {
    	Label title = new Label("Program : "+program.getName());
    	Label sessionCount = new Label("Sessions : "+program.getSessions().size()+"/7");
    	
    	Button addSessionButton = new Button("Add a session");
    	Button showStatsButton = new Button("Show stats");
    	Button addExerciseButton = new Button("Add an exercise");
    	if(program.getSessions().isEmpty()) {
            addExerciseButton.setDisable(true);
        }
    	Button finishButton = new Button("Finish this program & start new");
    	Button saveQuitButton = new Button("Save & Quit");
    	
    	if(program.getSessions().size() >= 7) {
    		addSessionButton.setDisable(true);
    	}
    	
    	addSessionButton.setOnAction(event -> {
    		stage.setScene(addSessionScene(stage, program, theCatalog, bodyWeight));
    	});
    	
    	finishButton.setOnAction(event -> {
    	    SaveManager saveManager = new SaveManager();
    	    saveManager.saveProgram(program, program.getName() + ".json");
    	    stage.setScene(startScene(stage, theCatalog));
    	});
    	
    	addExerciseButton.setOnAction(event -> {
    		stage.setScene(selectSessionScene(stage, program, theCatalog, bodyWeight));
    	});
    	
    	saveQuitButton.setOnAction(event -> {
    		SaveManager saveManager = new SaveManager();
    		saveManager.saveProgram(program, program.getName() + ".json");
    		stage.close();
    	});
    	
    	showStatsButton.setOnAction(event -> {
    	    stage.setScene(statsScene(stage, program, currentSession, theCatalog, bodyWeight));
    	});
    	
    	VBox root = new VBox(15);
    	root.setPadding(new Insets(20));
    	root.getChildren().addAll(title, sessionCount, addSessionButton, finishButton, addExerciseButton, saveQuitButton, showStatsButton);
    	
    	return new Scene(root, 400, 300);
    }
    
    private Scene addSessionScene(Stage stage, Program program, Catalog theCatalog, double bodyWeight) {
    	Label title = new Label("New Session");
    	Label durationLabel = new Label("Session duration (in minutes) : ");
    	TextField durationField = new TextField();
    	Button sessionButton = new Button("Add Session");
    	Label errorLabel = new Label("");
    	Button backButton = new Button("Back");
    	
    	backButton.setOnAction(event -> {
    	    stage.setScene(mainMenuScene(stage, program, null, theCatalog, bodyWeight));
    	});
    	
    	sessionButton.setOnAction(event ->{
    		try {
    			int duration = Integer.parseInt(durationField.getText());
    			Session session = new Session(LocalDate.now(), duration);
    			program.addSession(session);
    			stage.setScene(mainMenuScene(stage, program, session, theCatalog,  bodyWeight));
    		}catch(NumberFormatException e) {
    			errorLabel.setText("Invalid number");
    		}
    	});
    	
    	VBox root = new VBox(15);
    	root.setPadding(new Insets(20));
    	root.getChildren().addAll(title, durationLabel, durationField, sessionButton, backButton, errorLabel);
    	
    	return new Scene(root, 400, 300);
    }
    
    private Scene selectSessionScene(Stage stage, Program program, Catalog theCatalog, double bodyWeight) {
        Label title = new Label("Select a session to add an exercise to");

        ListView<String> sessionListView = new ListView<>();
        for (Session s : program.getSessions()) {
            sessionListView.getItems().add(s.getDate() + " (" + s.getDurationMinutes() + " min)");
        }

        Button selectButton = new Button("Select");
        Button backButton = new Button("Back");
        Label errorLabel = new Label("");

        selectButton.setOnAction(event -> {
            int index = sessionListView.getSelectionModel().getSelectedIndex();
            if (index == -1) {
                errorLabel.setText("Please select a session.");
                return;
            }
            Session selectedSession = program.getSessions().get(index);
            stage.setScene(addExerciseScene(stage, program, selectedSession, theCatalog, bodyWeight));
        });

        backButton.setOnAction(event -> {
            stage.setScene(mainMenuScene(stage, program, null, theCatalog, bodyWeight));
        });

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(title, sessionListView, selectButton, backButton, errorLabel);

        return new Scene(root, 400, 400);
    }
    
    private Scene addExerciseScene(Stage stage, Program program, Session currentSession, Catalog theCatalog, double bodyWeight) {
    	Label title = new Label("Add an exercise");
    	Label sessionInfos = new Label("For session : "+currentSession.getDurationMinutes() + " min.");
    	
    	ListView<String> exerciseListView = new ListView<String>();
    	for(ExerciseReference ex : theCatalog.getExercises()) {
    		exerciseListView.getItems().add(ex.getName()+" ("+ex.getMuscleGroup()+")");
    	}
    	
    	Button confirmButton = new Button("Add this exercise");
    	Button backButton = new Button("Back");
    	Label errorLabel = new Label("");
    	
    	confirmButton.setOnAction(event -> {
    		String selected = exerciseListView.getSelectionModel().getSelectedItem();
    		
    		if(selected == null) {
    			errorLabel.setText("Please select an exercise.");
    			return;
    		}
    		
    		// The selected exercise without everything next to the name
    		String exerciseName = selected.substring(0, selected.indexOf(" ("));
    		ExerciseReference reference = theCatalog.searchByName(exerciseName);
    		ExerciseDone exerciseDone = new ExerciseDone(reference);
    		currentSession.addExercise(exerciseDone);
    		
    		stage.setScene(addRoundScene(stage,program,currentSession,exerciseDone,theCatalog,bodyWeight));
    	});
    	
    	backButton.setOnAction(event -> {
            stage.setScene(mainMenuScene(stage, program, currentSession, theCatalog, bodyWeight));
        });
    	
    	VBox root = new VBox(15);
    	root.setPadding(new Insets(20));
    	root.getChildren().addAll(title,sessionInfos,exerciseListView,confirmButton,backButton,errorLabel);
    	
    	return new Scene(root, 400,400);
    }
    
    private Scene addRoundScene(Stage stage, Program program, Session currentSession, ExerciseDone currentExercise, Catalog theCatalog, double bodyWeight) {
    	Label title = new Label("Add a round");
    	Label exerciseInfos = new Label("Exercise : "+currentExercise.getReference().getName());
    	Label repsLabel = new Label("Reps : ");
    	TextField repsField = new TextField();
    	Label weightLabel = new Label("Weight (kg) : ");
    	TextField weightField = new TextField();
    	
    	Button addRoundButton = new Button("Add round");
    	Button removeLastRoundButton = new Button("Remove last round");
    	Button doneButton = new Button("Done with this exercise");
    	Label errorLabel = new Label("");
    	
    	addRoundButton.setOnAction(event -> {
    		try {
    			int reps = Integer.parseInt(repsField.getText());
    			double weight = Double.parseDouble(weightField.getText());
    			
    			currentExercise.addRound(new Round(reps,weight));
    			errorLabel.setText("Round added successfully, Add another or click Done");
    			repsField.clear();
    			weightField.clear();
    		}catch(NumberFormatException e) {
    			errorLabel.setText("Invalid reps or weight");
    		}
    	});
    	
    	removeLastRoundButton.setOnAction(event -> {
            List<Round> rounds = currentExercise.getRounds();
            if (!rounds.isEmpty()) {
                rounds.remove(rounds.size() - 1);
                errorLabel.setText("Last round removed.");
            } else {
                errorLabel.setText("No round to remove.");
            }
        });
    	
    	doneButton.setOnAction(event -> {
    		stage.setScene(mainMenuScene(stage,program,currentSession,theCatalog,bodyWeight));
    	});
    	
    	VBox root = new VBox(15);
    	root.setPadding(new Insets(20));
    	root.getChildren().addAll(title,exerciseInfos,repsLabel,repsField,weightLabel,weightField,addRoundButton,removeLastRoundButton,doneButton,errorLabel);
    	
    	return new Scene(root, 500,400);
    }
    
    // Made with AI
    private Scene statsScene(Stage stage, Program program, Session currentSession, 
            Catalog theCatalog, double bodyWeight) {

        StatsCalculator calculator = new StatsCalculator();

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label programTitle = new Label("Program: " + program.getName());
        programTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        root.getChildren().add(programTitle);

        int sessionNumber = 1;
        for (Session session : program.getSessions()) {

            Label sessionTitle = new Label("Session " + sessionNumber + " - " + session.getDate() 
                + " (" + session.getDurationMinutes() + " min)");
            sessionTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #2e6da4;");
            root.getChildren().add(sessionTitle);

            for (ExerciseDone exerciseDone : session.getExercisesDone()) {
                ExerciseReference ref = exerciseDone.getReference();

                Label exerciseTitle = new Label("  " + ref.getName() + " (" + ref.getMuscleGroup() + ")");
                exerciseTitle.setStyle("-fx-font-weight: bold;");
                root.getChildren().add(exerciseTitle);

                List<Round> rounds = exerciseDone.getRounds();
                if (rounds.isEmpty()) {
                    Label noRound = new Label("    No round recorded.");
                    root.getChildren().add(noRound);
                } else {
                    for (int i = 0; i < rounds.size(); i++) {
                        Round r = rounds.get(i);
                        Label roundLabel = new Label("    Set " + (i + 1) + " : " 
                            + r.getReps() + " reps x " + r.getWeight() + " kg");
                        root.getChildren().add(roundLabel);
                    }
                }
            }

            double sessionTonnage = calculator.sessionTonnage(session);
            double sessionCalories = calculator.sessionCalories(session, bodyWeight);

            Label tonnageLabel = new Label("  Session tonnage: " + sessionTonnage + " kg");
            Label caloriesLabel = new Label("  Session calories: " + String.format("%.2f", sessionCalories) + " kcal");
            root.getChildren().addAll(tonnageLabel, caloriesLabel);

            sessionNumber++;
        }

        Label muscleGroupTitle = new Label("Muscle group volume (total sets)");
        muscleGroupTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #2e6da4;");
        root.getChildren().add(muscleGroupTitle);

        for (var entry : calculator.muscleGroupVolume(program).entrySet()) {
            Label line = new Label("  " + entry.getKey() + ": " + entry.getValue() + " sets");
            root.getChildren().add(line);
        }

        Label perExerciseTitle = new Label("Sets per exercise");
        perExerciseTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #2e6da4;");
        root.getChildren().add(perExerciseTitle);

        for (var entry : calculator.setsPerExercise(program).entrySet()) {
            Label line = new Label("  " + entry.getKey() + ": " + entry.getValue() + " sets");
            root.getChildren().add(line);
        }

        Button backButton = new Button("Back to menu");
        backButton.setOnAction(event -> {
            stage.setScene(mainMenuScene(stage, program, currentSession, theCatalog, bodyWeight));
        });
        root.getChildren().add(backButton);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);

        return new Scene(scrollPane, 500, 600);
    }

    public static void main(String[] args) {
        launch(args);
    }
}