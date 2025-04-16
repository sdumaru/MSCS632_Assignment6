package main

import (
	"fmt"
	"log"
	"os"
	"sync"
	"time"
)

func main() {
	// Create necessary directories for logs and output.
	if err := os.MkdirAll("logs", os.ModePerm); err != nil {
		fmt.Println("Error creating logs directory:", err)
		return
	}
	if err := os.MkdirAll("output", os.ModePerm); err != nil {
		fmt.Println("Error creating output directory:", err)
		return
	}

	// Set up logging to file.
	logFile, err := os.OpenFile("logs/system.log", os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0644)
	if err != nil {
		fmt.Println("Error opening log file:", err)
		return
	}
	defer logFile.Close()
	log.SetOutput(logFile)

	numWorkers := 5
	numTasks := 10

	// Create channels
	// tasks: Buffered channel to act as the task queue
	tasks := make(chan *Task, numTasks)
	// results: Buffered channel to collect results from workers
	// Buffer size equals numTasks to prevent workers from blocking if main is slow to read
	results := make(chan string, numTasks)

	// Use WaitGroup to wait for all workers to finish
	var wg sync.WaitGroup

	// Start worker goroutines.
	log.Printf("Starting %d workers...", numWorkers)
	for i := 1; i <= numWorkers; i++ {
		wg.Add(1) // Increment WaitGroup counter for each worker
		go worker(i, &wg, tasks, results)
	}

	// Add tasks to the channel.
	log.Printf("Adding %d tasks to the queue...", numTasks)
	for i := 0; i < numTasks; i++ {
		task := &Task{TaskID: i, Data: fmt.Sprintf("Information %d", i)}
		tasks <- task
		log.Printf("Added task %d: %s to queue\n", task.TaskID, task.Data)
	}

	// Send termination signals by pushing 'nil' tasks.
	for i := 0; i < numWorkers; i++ {
		tasks <- nil
	}
	close(tasks)
	log.Println("Tasks channel closed. No more tasks will be added.")

	// Wait for all worker goroutines to complete using the WaitGroup.
	// This ensures all tasks sent have been picked up and processed (or attempted).
	log.Println("Waiting for workers to finish...")
	wg.Wait()
	log.Println("All workers have finished.")

	// Open (or create) the results output file.
	outFile, err := os.OpenFile("output/results.txt", os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0644)
	if err != nil {
		log.Printf("Error opening output file: %v\n", err)
		return
	}
	defer outFile.Close()

	// Since each valid task produces one result, collect numTasks results.
	for i := 0; i < numTasks; i++ {
		result := <-results
		// Append the result to the output file.
		if _, err := outFile.WriteString(result + "\n"); err != nil {
			log.Printf("Error writing result: %v\n", err)
		}
	}

	// Optional: Allow some extra time for workers to log termination messages.
	time.Sleep(1 * time.Second)
	log.Println("All tasks processed. Program terminating.")
	fmt.Println("All tasks processed. Check the output in 'output/results.txt' and logs in 'logs/system.log'")
}
