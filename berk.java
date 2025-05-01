import java.util.Scanner;
import java.time.LocalTime;

public class BerkeleyImproved {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); 

        // Step 1: Input number of processes (including master)
        System.out.print("Enter number of processes (including master process): ");
        int numProcesses = input.nextInt();

        int[] hours = new int[numProcesses];
.
        int[] minutes = new int[numProcesses];
        int[] seconds = new int[numProcesses];

        int[] timeDifferences = new int[numProcesses]; // Differences from master (in seconds)

        // Step 2: Get real time for master process (process 0)
        LocalTime masterTime = LocalTime.now();
        hours[0] = masterTime.getHour();
        minutes[0] = masterTime.getMinute();
        seconds[0] = masterTime.getSecond();

        System.out.println("\nMaster (Process 0) current time: " + hours[0] + " : " + minutes[0] + " : " + seconds[0]);

        // Step 3: Input current clock times of all slave processes
        for (int i = 1; i < numProcesses; i++) {
            System.out.println("\nEnter time for Process " + i + ":");
            System.out.print("Hours (0-23): ");
            hours[i] = input.nextInt();
            System.out.print("Minutes (0-59): ");
            minutes[i] = input.nextInt();
            System.out.print("Seconds (0-59): ");
            seconds[i] = input.nextInt();
        }

        // Step 4: Calculate time differences in seconds
        int masterInSeconds = hours[0] * 3600 + minutes[0] * 60 + seconds[0];
        int totalDifference = 0;

        for (int i = 1; i < numProcesses; i++) {
            int nodeInSeconds = hours[i] * 3600 + minutes[i] * 60 + seconds[i];
            timeDifferences[i] = nodeInSeconds - masterInSeconds;
            totalDifference += timeDifferences[i];
            System.out.println("Process " + i + " sent time difference of " + timeDifferences[i] + " seconds to master.");
        }

        // Step 5: Compute average difference
        int averageDifference = totalDifference / (numProcesses - 1);
        System.out.println("\nTotal difference (excluding master): " + totalDifference + " seconds");
        System.out.println("Average adjustment: " + averageDifference + " seconds\n");

        // Step 6: Compute synchronized times
        System.out.println("Synchronized clock times:");
        for (int i = 0; i < numProcesses; i++) {
            int currentInSeconds;
            if (i == 0) {
                // Adjust master clock
                currentInSeconds = masterInSeconds + averageDifference;
            } else {
                int nodeInSeconds = hours[i] * 3600 + minutes[i] * 60 + seconds[i];
                currentInSeconds = nodeInSeconds + (averageDifference - timeDifferences[i]);
            }

            // Convert back to H:M:S
            hours[i] = (currentInSeconds / 3600) % 24;
            minutes[i] = (currentInSeconds % 3600) / 60;
            seconds[i] = currentInSeconds % 60;

            System.out.println("Process " + i + " ---> " + hours[i] + " : " + minutes[i] + " : " + seconds[i]);
        }

        input.close();
    }
}
