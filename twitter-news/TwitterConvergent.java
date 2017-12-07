
/*
 *  Back end code for Twitter monetization project
 *  
 *  Our approach to monetizing Twitter and making it a profitable enterprise involves improving the way that Twitter
 *  conveys the news to others, so that more people will use Twitter as a legitimate news source, driving in more
 *  traffic from more and more backgrounds, and thus increasing traffic and ad revenue.
 *  
 *  To do this, we took the moments tab in Twitter and replaced it with a newspaper tab. The newspaper tab is different
 *  from moments in Twitter in that it consistently has 10 news stories everyday and thus works more like a
 *  news aggregator (example - Google News). The stories are always major, relevant and trending. In addition,
 *  the types stories the user sees in his/her newspaper can adapt to his preferences which is what this back end
 *  code mainly deals with.
 *  
 *  The back end code takes in a variety of factors to determine which types of stories the user is interested in such
 *  as which stories he/she selects on the newspaper, which tweets he/she favorites and retweets, which hashtags 
 *  he/she uses, and who he/she follows. The timeline and hashtag functionalities of Twitter have been recreated and 
 *  incorporated in our code here to demonstrate how factors from those tabs affect what the user's personalized newspaper 
 *  looks like.
 *  
 *  Authors: Leon Cai, Nathan Hoang, Bernardo Chuecos
 *  
 */

import java.util.ArrayList;
import java.util.Scanner;

public class TwitterConvergent {
	final static int NUM_STORIES = 10; // The user's newspaper will always
										// contain ten stories
	final static int NUM_TYPES_OF_STORIES = 6;
	final static int NUM_OF_MODES = 4;
	static ArrayList<Story> stories = new ArrayList<Story>();

	/*
	 * The frequency that each type of story appears on the newspaper is
	 * determined by a points system. The number of points that category has
	 * over the number of total points determines how many of the ten news
	 * stores are of that category. To start the program, politics stories are
	 * automatically given 20 points, science stories are given 10 points, and
	 * the rest of the stories are given 5 points. The default newspaper setting
	 * is therefore 4 Politics stories, 2 Science stories, 1 Entertainment
	 * story, 1 Finance story, and 1 Local story.
	 * 
	 */
	public static void main(String[] args) {
		int[] totalPoints = { 50 };
		int[] access = new int[NUM_OF_MODES];
		Story.StoryTypes[] types = { Story.StoryTypes.POLITICS, Story.StoryTypes.SCIENCE, Story.StoryTypes.SPORTS,
				Story.StoryTypes.ENTERTAINMENT, Story.StoryTypes.FINANCE, Story.StoryTypes.LOCAL };
		String input = "";
		String mode = "timeline";

		int[] points = new int[NUM_TYPES_OF_STORIES];
		/*
		 * The types of stories are represented by number values from 0 to 5. 0
		 * = Politics, 1 = Science, 2 = Sports, 3 = Entertainment, 4 = Finance
		 * and 5 = Local
		 */
		points[0] = 20;
		points[1] = 10;
		points[2] = 5;
		points[3] = 5;
		points[4] = 5;
		points[5] = 5;

		for (int i = 0; i < 4; i++) {
			stories.add(new Story(Story.StoryTypes.POLITICS));
		}
		stories.add(new Story(Story.StoryTypes.SCIENCE));
		stories.add(new Story(Story.StoryTypes.SCIENCE));
		stories.add(new Story(Story.StoryTypes.SPORTS));
		stories.add(new Story(Story.StoryTypes.ENTERTAINMENT));
		stories.add(new Story(Story.StoryTypes.FINANCE));
		stories.add(new Story(Story.StoryTypes.LOCAL));

		Scanner keyboard = new Scanner(System.in);

		System.out.println("Welcome to Twitter!");
		System.out.println();
		// The Twitter simulator contains 4 modes: Timeline, newspaper, hastags
		// and follow people.
		// Each of these modes can be accessed
		// by typing in their names after the select mode prompt.
		System.out.print("Select mode (timeline, newspaper, hashtags, followPeople): ");
		input = keyboard.nextLine();

		changeMode(input, points, totalPoints, keyboard, mode, access);
	}

	/*
	 * The timeline contains a variety of content from many different twitter
	 * users, each of which are associated with a particular news category.
	 * Favoriting a tweet will add one point to the category that the tweet is
	 * associated with, and retweeting a tweet will add two points to the
	 * category that the tweet is associated with.
	 */
	public static void timeline(int[] points, int[] totalPoints, Scanner keyboard, String mode, int access[]) {
		if (access[0] == 0) {
			System.out.println();
			System.out.println("My timeline");
			access[0]++;
		}
		int storyInput;
		mode = "timeline";
		String interactInput;

		// The names of each twitter user featured in the timeline are storied
		// in a string array,
		// and a parallel array of ints stories the numerical categories they
		// are associated with
		// for the purpose of determining that category's points
		String[] tweets = { "1: 7h: Donald J Trump", "2: 5h: Bernie Sanders", "3: 9h: NASA", "4: 2h: Yahoo! Finance",
				"5: 33m: TMZ", "6: 4H: MLB", "7: 9h: GrenBay Packers", "8: 22m: ESPN",
				"9: 1h: Austin-American Statesman", "10: 10h: Wired", "11: 11h: Hollywood Access",
				"12: 6m: Senate Maj. Leader" };
		int[] storyTypes = { 0, 0, 0, 1, 4, 3, 2, 2, 2, 5, 1, 3, 0 };

		System.out.println();
		for (String tweet : tweets) {
			System.out.println(tweet);
		}

		System.out.println();
		System.out.print("Select tweet: ");
		storyInput = Integer.parseInt(keyboard.nextLine());

		System.out.println();
		System.out.print("Favorite or Retweet: Type FAV for favorite, RT for retweet: ");

		interactInput = keyboard.nextLine();

		if (interactInput.equals("FAV")) {
			totalPoints[0] = totalPoints[0] + 1;
			points[storyTypes[storyInput]] = points[storyTypes[storyInput]] + 1;
		} else if (interactInput.equals("RT")) {
			totalPoints[0] = totalPoints[0] + 2;
			points[storyTypes[storyInput]] = points[storyTypes[storyInput]] + 2;
		}

		System.out.println();
		System.out.print("Input mode. Press enter to stay in current mode or exit to quit: ");
		String input = keyboard.nextLine();

		changeMode(input, points, totalPoints, keyboard, mode, access);

	}

	/*
	 * The hashtags tab contains a variety of hashtags from many different
	 * topics. Each of the hashtags are associated with one of the 6 news
	 * categories. A string array contains the names of the hashtags, and a
	 * parallel int array contains the numerical categories that each of these
	 * hashtags are associated with. Clicking on a hashtag adds one point to the
	 * category that hashtag is associated with. And tweeting about the hashtag
	 * adds another two points to the category that hashtag is associated with.
	 */
	public static void hashtags(int[] points, int[] totalPoints, Scanner keyboard, String mode, int[] access) {
		if (access[2] == 0) {
			System.out.println();
			System.out.println("Trending Hastags");
			access[2]++;
		}
		mode = "hashtags";
		String[] hashtags = { "1. #MeToo", "2. #TXvsOU", "3. #SolarEclipse", "4. #AustinMarathon", "5. #NetNeutrality",
				"6. #Thor" };
		int[] storyTypes = { 0, 0, 2, 1, 5, 2, 3 };
		System.out.println();
		for (String hashtag : hashtags) {
			System.out.println(hashtag);
		}

		System.out.println();
		System.out.print("Select hashtag: ");
		int storyInput = Integer.parseInt(keyboard.nextLine());
		points[storyTypes[storyInput]] = points[storyTypes[storyInput]] + 1;
		totalPoints[0] = totalPoints[0] + 1;

		System.out.println();
		System.out.print("Tweet about it? (y/n): ");
		String interactInput = keyboard.nextLine();
		if (interactInput.equals("y"))
			points[storyTypes[storyInput]] = points[storyTypes[storyInput]] + 2;
		totalPoints[0] = totalPoints[0] + 2;

		System.out.println();
		System.out.print("Input mode. Press enter to stay in current mode or type exit to quit: ");
		String input = keyboard.nextLine();

		changeMode(input, points, totalPoints, keyboard, mode, access);

	}

	// The follow people tab gives the user a wide variety of accounts to follow
	// at his/her choosing. Following
	// an account adds 1 point to the category in which that account is
	// associated with
	public static void followPeople(int[] points, int[] totalPoints, Scanner keyboard, String mode, int[] access) {
		if (access[3] == 0) {
			System.out.println();
			System.out.println("Accounts you may be interested in");
			access[3]++;
		}
		mode = "followPeople";
		String[] people = { "1. Barack Obama", "2. The University of Texas at Austin", "3. The Economist", "4. People",
				"5. How Things Work", "6. Rotten Tomatoes", "7. Greenpeace", "8. ACL Fest", "9. TechCrunch",
				"10. Golden State Warriors", "11. Atlanta Falcons", "12. The Daily Texan",
				"13. House Majority Leader" };
		int[] storyTypes = { 0, 0, 5, 4, 3, 1, 3, 0, 5, 1, 2, 2, 5, 0 };
		int[] capacity = new int[13];
		System.out.println();
		for (String user : people) {
			System.out.println(user);
		}
		System.out.println();
		System.out.print("Select an account to follow: ");
		int storyInput = Integer.parseInt(keyboard.nextLine());
		points[storyTypes[storyInput]] = points[storyTypes[storyInput]] + 1;
		totalPoints[0] = totalPoints[0] + 1;

		System.out.println();
		System.out.print("Input mode. Press enter to stay in current mode or type exit to quit: ");
		String input = keyboard.nextLine();

		changeMode(input, points, totalPoints, keyboard, mode, access);
	}

	/*
	 * All of the actions that have happened under the timeline and hashtags
	 * classes have a direct effect on the types of stores that appear in the
	 * newspaper. In addition, reading a newspaper story of a particular type
	 * will add one point to the category that the story is associated with.
	 */
	public static void newspaper(int[] points, int[] totalPoints, Scanner keyboard, String mode, int[] access) {
		if (access[1] == 0) {
			System.out.println();
			System.out.println("Today's News");
			access[1]++;
		}
		mode = "newspaper";
		update(points, totalPoints);
		String input;

		System.out.println();
		for (int i = 0; i < NUM_STORIES; i++) {
			System.out.println("Story #" + (i + 1) + ": " + stories.get(i).getType());
		}
		System.out.println();
		System.out.print("Read Story: ");

		input = keyboard.nextLine();

		// Updating preferences
		int choice = Integer.parseInt(input) - 1;
		Story.StoryTypes t = stories.get(choice).getType();
		if (t.equals(Story.StoryTypes.POLITICS))
			points[0]++;
		else if (t.equals(Story.StoryTypes.SCIENCE))
			points[1]++;
		else if (t.equals(Story.StoryTypes.SPORTS))
			points[2]++;
		else if (t.equals(Story.StoryTypes.ENTERTAINMENT))
			points[3]++;
		else if (t.equals(Story.StoryTypes.FINANCE))
			points[4]++;
		else if (t.equals(Story.StoryTypes.LOCAL))
			points[5]++;

		totalPoints[0] = totalPoints[0] + 1;

		System.out.println();
		System.out.print("Input mode. Press enter to stay in current mode or type exit to quit: ");
		input = keyboard.nextLine();
		update(points, totalPoints);
		changeMode(input, points, totalPoints, keyboard, mode, access);

	}

	// changes the mode to the name of the tab that the user enters.
	public static void changeMode(String input, int[] points, int[] totalPoints, Scanner keyboard, String mode,
			int[] access) {
		if (input.equals("newspaper")) {
			access[1] = 0;
			newspaper(points, totalPoints, keyboard, mode, access);
		} else if (input.equals("timeline")) {
			access[0] = 0;
			timeline(points, totalPoints, keyboard, mode, access);
		} else if (input.equals("hashtags")) {
			access[2] = 0;
			hashtags(points, totalPoints, keyboard, mode, access);
		} else if (input.equals("followPeople")) {
			access[3] = 0;
			followPeople(points, totalPoints, keyboard, mode, access);
		} else if (input.equals("exit")) {
			;
			// By default, Twitter stays in the current mode if the
			// user does not enter a vaild mode name or presses
			// the enter key. If this is the first time the user
			// is prompted and a valid input is not given,
			// then timeline is opened
		} else {
			if (mode.equals("timeline")) {
				timeline(points, totalPoints, keyboard, mode, access);
			} else if (mode.equals("newspaper")) {
				newspaper(points, totalPoints, keyboard, mode, access);
			} else if (mode.equals("hashtags")) {
				hashtags(points, totalPoints, keyboard, mode, access);
			} else {
				followPeople(points, totalPoints, keyboard, mode, access);
			}
		}
	}

	// Updates the point totals for each of the of news categories
	public static void update(int[] points, int[] totalPoints) {
		stories.clear();
		for (int x = 0; x < points.length; x++) {
			int newNumber = (int) Math.round((10 * (float) points[x] / (float) totalPoints[0]));

			Story newStory = new Story();
			if (x == 0)
				newStory.setType(Story.StoryTypes.POLITICS);
			if (x == 1)
				newStory.setType(Story.StoryTypes.SCIENCE);
			if (x == 2)
				newStory.setType(Story.StoryTypes.SPORTS);
			if (x == 3)
				newStory.setType(Story.StoryTypes.ENTERTAINMENT);
			if (x == 4)
				newStory.setType(Story.StoryTypes.FINANCE);
			if (x == 5)
				newStory.setType(Story.StoryTypes.LOCAL);

			for (int i = 0; i < newNumber; i++) {
				stories.add(newStory);

			}

		}

	}

}
