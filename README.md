# Data Processing System

## Overview
This project implements a Data Processing System that simulates multiple worker threads processing data in parallel. The system is developed in both **Java** and **Go** to demonstrate how to handle concurrency, manage shared resources, and implement error handling in different programming languages.

## How to Run

### Java Implementation

1. **Compile the Code:**
   - Open a terminal in the Java project directory (java-data-processing-system).
   - Navigate to the folder containing your Java files and compile them:
     ```bash
     cd src
     javac *.java
     ```

2. **Run the Application:**
   - Run the `Main` class:
     ```bash
     java Main
     ```

3. **Output:**
   - The console will display messages logging task processing activities.
   - Results are saved to `output/results.txt`.

### Go Implementation

1. **Run the Application:**
   - In your terminal, navigate to the Go project directory (go-data-processing-system) and run:
     ```bash
     go mod init go-data-processor
     go run .
     ```

2. **Output:**
   - Processed results are appended to `output/results.txt`.
   - Detailed logging information is available in `logs/system.log`.