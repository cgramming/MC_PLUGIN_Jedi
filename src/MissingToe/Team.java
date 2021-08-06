package MissingToe;
 
import java.util.ArrayList;
import java.util.HashMap;
 
import org.bukkit.entity.Player;
 
public class Team {
// a list of all the teams that we create. Ex: a red team, blue team, etc. will all be stored in this list!
public static ArrayList<Team> teams = new ArrayList<Team>();
// This is the hashmap we use to put the player in a team. Notice, it takes in a String (player name) and a Team.
public static HashMap<String, Team> playerTeams = new HashMap<String, Team>();
// this is going to be a string that represents the team color. So for the red team, this variable would be "Red".
public String teamColor;
 
// our constructor which takes in a string. This string will be the team color.
public Team(String teamcolor){
// setting the 'teamColor' variable equal to the 'teamcolor' variable so we could use 'teamColor' throughout this class.
teamColor = teamcolor;
// adding the team that we just created to the array list that we previously created called 'teams'.
teams.add(this);
}
// Method to add the player to a team.
// Notice this takes in both a Player; p, and a Team; team.
	 public void addPlayer(Player p){
		// putting the players name in the hashmap that holds all the player teams
		playerTeams.put(p.getName(), this);
		}
// Removing the player
public static void removePlayer(Player p){
// checking if the player is in a team first. then removing the player from the hashmap.
// Look for the hasTeam method below :)
if(hasTeam(p) == true){
playerTeams.remove(p.getName());
}
}
// check if player has a team
public static boolean hasTeam(Player p){
// checking if the player is in the 'playerTeams' hashmap...if true then that player is in a
return playerTeams.containsKey(p.getName());
}
// this method returns the team of the player.
public static Team getTeam(Player p){
// if the player has a team....
if(hasTeam(p) == true){
// then return the Team he/she is in from the playerTeams hashmap!
return playerTeams.get(p);
}
// if not...
else if(hasTeam(p) == false){
// return null!
return null;
}
return null;
}
// This method returns a string. It will either return "Red" "Blue" or whatever you pass into the constructor.
public String getTeamColor(){
return teamColor;
}
 
}