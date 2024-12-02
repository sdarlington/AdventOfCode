package helper

import (
  "bufio"
  "log"
  "os"
)

func ParseInput[T any](filename string, parser func(string) T)(result []T)  {
  file, err := os.Open(filename)
  if err != nil {
  	log.Fatal(err)
  }
  defer file.Close()
  
  scanner := bufio.NewScanner(file)
 
  for scanner.Scan() {
    each_line := scanner.Text()
    
    result = append(result, parser(each_line))
  }

  return
}
