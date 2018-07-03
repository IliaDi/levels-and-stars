# levels-and-stars
Problem description:
It is a game with levels, each player can go to level 0 without any requirements, in order to get to each of the rest of levels (the order does not matter , there can be several possible options) one needs to have the appropriate key codes, and once they complete the level they get some bonus keys and some stars. The game ends when there are no more levels to be visited (all visited once) , or the keys the player has at the moment cannot grant him access to any levels.
Input:
file : 1st line has the number of levels (N)
the rest of the N+1 lines: have the numbers ki (keys required for the level) ri (bonus keys upon completion of the level) si (number of stars given) and after thet the codes of the ki+ri (bonus/required) keys.
Output:
The most stars a player can possibly gather by playing this game.
