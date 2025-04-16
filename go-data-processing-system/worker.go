package main

import (
	"fmt"
	"log"
	"math/rand"
	"time"
)

// worker function processes tasks from the 'tasks' channel and
// sends results/errors to the 'results' channel.
func worker(workerID int, tasks <-chan *Task, results chan<- string) {
	log.Printf("Worker %d started.\n", workerID)

	// Range over the tasks channel. This loop automatically exits
	// when the tasks channel is closed and drained.
	for task := range tasks {
		// Check for termination signal (nil task).
		if task == nil {
			log.Printf("Worker %d received termination signal.\n", workerID)
			break
		}

		log.Printf("Worker %d processing task %d: %s\n", workerID, task.TaskID, task.Data)

		// Simulate computational work with a delay
		processingTime := time.Duration(rand.Intn(500)+500) * time.Millisecond
		time.Sleep(processingTime)

		result := fmt.Sprintf("Worker %d completed task %d: %s", workerID, task.TaskID, task.Data)
		results <- result
		log.Printf("Worker %d finished task %d: %s\n", workerID, task.TaskID, task.Data)
	}
	log.Printf("Worker %d terminated.\n", workerID)
}
