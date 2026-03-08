import java.util.*;

public class ProjectMain 
  
    static HashMap<String, HashSet<String>> prereqs = new HashMap<>();

    
    static HashMap<String, HashSet<String>> completed = new HashMap<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Course Enrollment Planner — Commands:");
        printHelp();

        
        while (true) {

            System.out.print("> ");

            
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            
            String[] parts = input.split("\\s+");

            
            String command = parts[0].toUpperCase();

            switch (command) {

                case "HELP":
                    printHelp();
                    break;

                case "ADD_COURSE":
                    addCourse(parts);
                    break;

                case "ADD_PREREQ":
                    addPrereq(parts);
                    break;

                case "PREREQS":
                    showPrereqs(parts);
                    break;

                case "COMPLETE":
                    completeCourse(parts);
                    break;

                case "DONE":
                    showCompleted(parts);
                    break;

                case "CAN_TAKE":
                    canTake(parts);
                    break;

                case "EXIT":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Unknown command. Type HELP.");
            }
        }
    }

    
    static void printHelp() {

        System.out.println("HELP");
        System.out.println("ADD_COURSE <course>");
        System.out.println("ADD_PREREQ <course> <prereq>");
        System.out.println("PREREQS <course>");
        System.out.println("COMPLETE <student> <course>");
        System.out.println("DONE <student>");
        System.out.println("CAN_TAKE <student> <course>");
        System.out.println("EXIT");
    }

    
    static void addCourse(String[] parts) {

        if (parts.length < 2) {
            System.out.println("Usage: ADD_COURSE <course>");
            return;
        }

        String course = parts[1];
      
        prereqs.putIfAbsent(course, new HashSet<>());

        System.out.println("Added course: " + course);
    }

    
    static void addPrereq(String[] parts) {

        if (parts.length < 3) {
            System.out.println("Usage: ADD_PREREQ <course> <prereq>");
            return;
        }

        String course = parts[1];
        String prereq = parts[2];

        
        if (course.equals(prereq)) {
            System.out.println("A course cannot be its own prerequisite");
            return;
        }

        
        prereqs.putIfAbsent(course, new HashSet<>());
        prereqs.putIfAbsent(prereq, new HashSet<>());

        
        prereqs.get(course).add(prereq);

        System.out.println("Added prereq: " + prereq + " -> " + course);
    }

    
    static void showPrereqs(String[] parts) {

        if (parts.length < 2) {
            System.out.println("Usage: PREREQS <course>");
            return;
        }

        String course = parts[1];

        if (!prereqs.containsKey(course)) {
            System.out.println("Course not found");
            return;
        }

        System.out.println("Prereqs for " + course + ": " + prereqs.get(course));
    }

    
    static void completeCourse(String[] parts) {

        if (parts.length < 3) {
            System.out.println("Usage: COMPLETE <student> <course>");
            return;
        }

        String student = parts[1];
        String course = parts[2];

        
        completed.putIfAbsent(student, new HashSet<>());

        completed.get(student).add(course);

        System.out.println(student + " completed " + course);
    }

    
    static void showCompleted(String[] parts) {

        if (parts.length < 2) {
            System.out.println("Usage: DONE <student>");
            return;
        }

        String student = parts[1];

        if (!completed.containsKey(student)) {
            System.out.println("No record");
            return;
        }

        System.out.println(completed.get(student));
    }

    
    static void canTake(String[] parts) {

        if (parts.length < 3) {
            System.out.println("Usage: CAN_TAKE <student> <course>");
            return;
        }

        String student = parts[1];
        String course = parts[2];

        
        HashSet<String> coursePrereqs = prereqs.getOrDefault(course, new HashSet<>());

        
        HashSet<String> studentCourses = completed.getOrDefault(student, new HashSet<>());

        
        if (coursePrereqs.isEmpty()) {
            System.out.println("YES");
            return;
        }

        
        if (studentCourses.containsAll(coursePrereqs)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
