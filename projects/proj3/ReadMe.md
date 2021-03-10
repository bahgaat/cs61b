# Bear Maps Project.
# CS61B - Project 3.

In this project, I have implemented the back end of a web server bear maps, which is similar to Google maps. 

Details:
- I have implemented the Rasterer class which renders map images given a user's requested area and level of zoom.
 
- I have implemented The GraphDB class which reads in the Open Street Map dataset and store it as a graph. Each node in the graph represents a single intersection, 
and each edge represents a road.

- I have implemented Router class which uses A* search algorithm to find the shortest path between two points in Berkeley,
 and uses shortest path to generate navigation directions.
 
- I have implemented an Autocomplete system using a Trie data structure, which allows matching a prefix to valid location.

for more information: https://sp18.datastructur.es/materials/proj/proj3/proj3
