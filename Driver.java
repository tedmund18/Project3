import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;

//driver file for project 3 "who to trust?"
//input is bitcoin reputation rating 
//each line represents one user
// 6,2,4 <- this represents source,target,reputation given(-10 - 10)


//compile: javac Driver.java
//run with example: java Driver soc-sign-bitcoinotc_notime.csv


public class Driver {
	
	public static void main(String[] args)
									throws FileNotFoundException {

		//check to see if name of file was entered
		if(args.length != 1) {					
			System.out.println("usage: java FileInput <filename>");
			System.exit(1);				
			}


		//create file path
		String filePath = System.getProperty("user.dir") + File.separator + args[0];
		//sending file path back to user
		System.out.println("using input from - " + filePath);
		System.out.println("Please enter target ID: ");
		Scanner values = new Scanner(System.in);
		int id = values.nextInt();
		System.out.println("Please enter minimum acceptable rating (-10 to 10(most trustworthy)): ");
		int filter = values.nextInt();


		System.out.println("Finding connections with ID: " + id + " with minimum rating of " + filter);




		File file = new File(filePath);
		Scanner sc = new Scanner(file);
		int num_reviews = 0; 

		//find min and max of source and target ids; 
		//create array of objects created
		Review[] find_min_max = new Review[35592];



		while(sc.hasNext()){
			String operatingLine = sc.nextLine();
			String delim = ",";
			String[] tokens = operatingLine.split(delim);
			//grab the parts into variables
			int source_id = Integer.parseInt(tokens[0]);
			int target_id = Integer.parseInt(tokens[1]);
			int rating = Integer.parseInt(tokens[2]);
			//create object
			Review incoming_review = new Review(source_id, target_id, rating);
			//add object to array
			//System.out.println(incoming_review);
			find_min_max[num_reviews] = incoming_review;

			num_reviews ++;
		}
		//System.out.println(num_reviews);
		//find min and max
		//creating variables to track
		int min_source = find_min_max[0].get_my_node();
		int max_source = find_min_max[0].get_my_node();
		int min_target = find_min_max[0].get_incoming_node();
		int max_target = find_min_max[0].get_incoming_node();


		for(int i = 1; i < num_reviews; i++){
			//update the values for source
    		int find_range_source = find_min_max[i].get_my_node();
    		//check if source to be compared to is less than min if so update min of source
    		if (find_range_source < min_source){
    			min_source = find_range_source;
    		}    		
    		//then check if source to be compared to is > max if so update max of source
    		if (find_range_source > max_source){
    			max_source = find_range_source;
    		}
    		//update the values for target
    		int find_range_target = find_min_max[i].get_incoming_node();
    		//check if this product to be compared to is less than min if so update min of target
    		if (find_range_target < min_target){
    			min_target = find_range_target;
    		}    		
    		//then check if product to be compared to is > max if so update max of target
    		if (find_range_target > max_target){
    			max_target = find_range_target;
    		}
		}

		//review sources will contain all the sources and the reviews they have given out (users)
		//review target will contain all the targets and reviews that have recieved and from who (stores)
		//creating array of size max - min for source and target
		Review[] review_sources = new Review[max_source + 1];
		Review[] review_targets = new Review[max_target + 1];

		for (int i = 0; i < num_reviews; i++){
			//create object to gather numbers from
			Review gathering = find_min_max[i];


			//creating map for sources
			if(review_sources[gathering.get_my_node()] != null){
				//if the corresponding indices isnt empty it means that the node rated more than one store or norde (store) has been reviewed by more than one user
				//this will call the update on that node that isnt null; accepting object to be added to arrays
				// System.out.println("attempting to addd to " + gathering.get_my_node());
				review_sources[gathering.get_my_node()].update_node(gathering);

			}
			else{
				//if no rating in place then the user is a new user / first time seeing the user rate / first time seeing store being rated
				// System.out.println("adding to " + find_min_max[i].get_my_node());
				review_sources[gathering.get_my_node()] = gathering;
			}
			//creating map for target
			if(review_targets[gathering.get_incoming_node()] != null){
				//if the corresponding indices isnt empty it means that the node rated more than one store or norde (store) has been reviewed by more than one user
				//this will call the update on that node that isnt null; accepting object to be added to arrays
				// System.out.println("attemptimg second second adding to " + gathering.get_incoming_node());
				// System.out.println("my i val is " + i);
				// System.out.println(gathering);
				review_targets[gathering.get_incoming_node()].update_node(gathering);

			}
			else{
				//if no rating in place then the user is a new user / first time seeing the user rate / first time seeing store being rated
				// System.out.println("second adding to " + gathering.get_incoming_node());
				review_targets[gathering.get_incoming_node()] = gathering;
			}
		}

		//find and return the eligible ratings connected based on input from user
		review_targets[id].get_eligible(filter);






	}

}
