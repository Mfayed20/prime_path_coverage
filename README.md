# Prime Path Coverage

This code calculates the prime path coverage for any given graph.

## How to Run

1. Compile the `GraphTest.java` file using a Java compiler.

   ```sh
   javac GraphTest.java
   ```

2. Run the compiled class file.

   ```sh
   java GraphTest
   ```

3. Follow the on-screen prompts to input the number of vertices, the number of edges, and then each edge as a pair of integers representing the start and end vertices.

## Example Input/Output

Enter the number of vertices: 4

Enter the number of edges: 5

Enter 5 edges: 0 1 0 2 1 3 2 3 3 0

All paths and cycles:

[0]
[1]
[2]
[3]
[0, 1]
[0, 2]
[1, 3]
[2, 3]
[3, 0]
[0, 1, 3]
[0, 2, 3]
[1, 3, 0]
[2, 3, 0]
[3, 0, 1]
[3, 0, 2]
[0, 1, 3, 0]
[0, 2, 3, 0]
[1, 3, 0, 1]
[1, 3, 0, 2]
[2, 3, 0, 1]
[2, 3, 0, 2]
[3, 0, 1, 3]
[3, 0, 2, 3]

Total of paths and cycles: 23

All Prime paths:

[0, 1, 3, 0]
[0, 2, 3, 0]
[1, 3, 0, 1]
[1, 3, 0, 2]
[2, 3, 0, 1]
[2, 3, 0, 2]
[3, 0, 1, 3]
[3, 0, 2, 3]

Total of Prime paths: 8
