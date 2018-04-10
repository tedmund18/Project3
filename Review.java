public class Review {
	// create object review for each target node and then each source node with same info

	//int my_node , int incoming_node, int rating 
	private int my_node;
	int array_size = 10;
	private int[] incoming_node = new int[array_size];
	private int[] rating = new int[array_size];
	private int count = 0;

	//constructors
	public Review(int my_node, int incoming_node, int rating){
		this.my_node = my_node;
		this.incoming_node[this.count] = incoming_node;
		this.rating[this.count] = rating;
		this.count++;
	}

	//get functions
	public int get_my_node(){
		return this.my_node;
	}

	public int get_incoming_node(){
		return this.incoming_node[0];
	}

	public int get_rating(){
		return this.rating[0];
	}
	//resizing operations for rating and incoming node arrays
	public void resize(){
		int[] incoming_resized = new int[array_size * 2];
		int[] rating_resized = new int[array_size * 2];
		for (int i = 0; i < array_size; i++){
			incoming_resized[i] = incoming_node[i];
			rating_resized[i] = rating_resized[i];
		}
		incoming_node = incoming_resized;
		rating = rating_resized;
		array_size = array_size * 2;

	}

	//get elibile takes filter as filter to only print vals
	public void get_eligible(int filter){
		for (int i = 0; i < count; i++){
			if(rating[i] >= filter){
				System.out.println("Recieved rating from " + this.incoming_node[i] + " with a rating of : " + this.rating[i]);
			}
		}
	}

	//review update 
	//this is to take into account multiple reviews from either source or target nodes
	//public void update_node()
	public void update_node(Review adding){
		if (count >= array_size){
			resize();
		}
		this.incoming_node[count] = adding.get_incoming_node();
		this.rating[count] = adding.get_rating();
		count ++;
	}

	@Override
	public String toString() {
		return ("My my_node value is : " + my_node + " My incoming_node value is: " + this.incoming_node[this.count - 1] + " My rating value is : " + this.rating[this.count - 1]);
	}

}
